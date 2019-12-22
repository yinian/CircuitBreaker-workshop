package com.viagra.java.exception;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 09:46
 * @Description:
 */
public class CircuitBreakerOpenException extends CircuitBreakerException{
    public CircuitBreakerOpenException(String message, Throwable cause) {
        super("The operation " + message + " has too many failures, tripping circuit breaker.",cause);
    }

    public CircuitBreakerOpenException(String message) {
        super("The operation " + message + " has too many failures, tripping circuit breaker.");
    }
}
