package com.viagra.java.handler;
import com.viagra.java.CircuitBreaker;
import com.viagra.java.CircuitBreakerConfig;
import com.viagra.java.CircuitBreakerRegister;
import com.viagra.java.annotation.GuardByCircuitBreaker;
import com.viagra.java.exception.CircuitBreakerOpenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * https://github.com/alenegro81/CircuitBreaker
 * http://www.doclo.be/lieven/articles/circuitbreakerwithspringaop.html
 * http://www.cnblogs.com/yangecnu/p/Introduce-Circuit-Breaker-Pattern.html
 * circuit breaker 的 jdk代理 实现
 */
public class CircuitBreakerInvocationHandler implements InvocationHandler{

    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerInvocationHandler.class);

    private Object target;

    public CircuitBreakerInvocationHandler(Object target) {
        this.target = target;
    }

    // 动态生成代理对象
    public Object proxy(){
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(),this);
    }






    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        GuardByCircuitBreaker breakerAnno = method.getAnnotation(GuardByCircuitBreaker.class);
        if (breakerAnno == null){
            return method.invoke(target,args);
        }

        Class<? extends Throwable>[] noTripExists = breakerAnno.noTripExceptions();
        int timeout = breakerAnno.timeoutInMs();
        int interval = breakerAnno.failCountWindowInMs();
        int failThreshold = breakerAnno.failThreshold();

        CircuitBreakerConfig config = CircuitBreakerConfig.newDefault();
        if (interval != -1){
            config.setFailCountWindowInMs(interval);
        }

        if (failThreshold != -1){
            config.setFailThreshold(failThreshold);
        }

        String key = target.getClass().getSimpleName() +"-"+ method.getName();
        CircuitBreaker breaker = CircuitBreakerRegister.get(key);

        if (breaker == null){
            breaker = new CircuitBreaker(key, config);
            CircuitBreakerRegister.putIfAbsent(key, breaker);
        }

        Object returnValue = null;

        logger.debug("breaker state:{}, method:{}", breaker.getState(), method.toGenericString());

        //breaker state
        if (breaker.isOpen()){
            // 判断是否该进入half_open状态
            if (breaker.isOpen2HalfOpenTimeout()){
                // 进入half_open状态
                breaker.openHalf();
                logger.debug("method:{} into half open", method.toGenericString());
                returnValue = processHalfOpen(breaker,method,args,noTripExists);
            }else{
                throw new CircuitBreakerOpenException(method.toGenericString());
            }
        }else if(breaker.isClosed()){

            try {
                returnValue = method.invoke(target,args);
                // 这里看情况是否重置标志
//            breaker.close();
            } catch (Throwable t){
                if (isNoTripException(t,noTripExists)){
                    throw t;
                }else{
                    // 增加计数
                    breaker.incrFailCount();
                    if (breaker.isCloseFailThresholdReached()){
                        // 触发阈值，打开
                        logger.debug("method:{} reached fail threshold, circuit breaker open", method.toGenericString());
                        breaker.open();
                        throw new CircuitBreakerOpenException(method.toGenericString());

                    }else{
                        throw t;
                    }
                }
            }


        }else if(breaker.isHalfOpen()){
            returnValue = processHalfOpen(breaker,method,args,noTripExists);

        }
        return returnValue;
    }


    private Object processHalfOpen(CircuitBreaker breaker,Method method,
                                   Object[] args,Class<? extends Throwable>[] noTripExs) throws Throwable {

        try {
            Object returnValue = method.invoke(target,args);
            breaker.getConsecutiveSuccCount().incrementAndGet();
            if (breaker.isConsecutiveSuccessThresholdReached()){
                // 调用成功则进入close状态
                breaker.close();
            }
            return returnValue;
        } catch (Throwable e) {
            if (isNoTripException(e, noTripExs)){
                breaker.getConsecutiveSuccCount().incrementAndGet();
                if (breaker.isConsecutiveSuccessThresholdReached()){
                    breaker.close();
                }
                throw e;
            }else{
                breaker.open();
                throw new CircuitBreakerOpenException(method.toGenericString());
            }
        }
    }

    private boolean isNoTripException(Throwable t,Class<? extends Throwable>[] noTripExceptions){

        if (noTripExceptions == null || noTripExceptions.length == 0){
            return false;
        }

        for (Class<? extends Throwable> ex : noTripExceptions){
            // 是否是抛出异常t的父类
            if(ex.isAssignableFrom(t.getCause().getClass())){
                return true;
            }
        }

        return false;
    }

}

