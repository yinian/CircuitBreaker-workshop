package com.viagra.java.exception;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 09:44
 * @Description:
 */
public class CircuitBreakerException extends RuntimeException{
    public CircuitBreakerException(String message) {
        super(message);
    }

    public CircuitBreakerException(String message, Throwable cause) {
        super(message, cause);
    }
}
