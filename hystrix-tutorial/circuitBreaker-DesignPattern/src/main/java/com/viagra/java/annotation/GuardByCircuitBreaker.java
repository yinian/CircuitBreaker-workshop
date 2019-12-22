package com.viagra.java.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 09:48
 * @Description:
 */

/**
 * -1 表示使用者没有overwrite该项配置
 * Created by patterncat on 2016/4/20.
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardByCircuitBreaker {
    int timeoutInMs() default -1;
    int failThreshold() default -1;
    int failCountWindowInMs() default -1;
    Class<? extends Throwable> [] noTripExceptions();
}
