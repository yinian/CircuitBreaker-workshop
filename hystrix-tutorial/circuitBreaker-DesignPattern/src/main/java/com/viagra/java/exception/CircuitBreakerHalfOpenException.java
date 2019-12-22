package com.viagra.java.exception;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 09:45
 * @Description:
 */
public class CircuitBreakerHalfOpenException  extends CircuitBreakerException{
    public CircuitBreakerHalfOpenException(String message) {
        super(message);
    }

    public CircuitBreakerHalfOpenException(String message, Throwable cause) {
        super(message, cause);
    }
}
