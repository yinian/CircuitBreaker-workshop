package com.viagra.java.handler;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 13:05
 * @Description:
 */
public class ProxyFactory {
    public static <T> T proxyBean(Object target){
        return (T) new CircuitBreakerInvocationHandler(target).proxy();
    }
}