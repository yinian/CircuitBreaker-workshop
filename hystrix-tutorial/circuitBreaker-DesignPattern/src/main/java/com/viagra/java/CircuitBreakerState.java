package com.viagra.java;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 10:00
 * @Description:
 */
public enum CircuitBreakerState {

    CLOSED,    // working normally, calls are transparently passing through
    OPEN,      // method calls are being intercepted and CircuitBreakerExceptions are being thrown instead
    HALF_OPEN  // method calls are passing through; if another blacklisted exception is thrown, reverts back to OPEN
}
