package com.viagra.java;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 10:14
 * @Description:
 */
public class CircuitBreakerRegister {

    private static ConcurrentHashMap<String, CircuitBreaker> breakers = new ConcurrentHashMap<String, CircuitBreaker>();

    public static CircuitBreaker get(String key){
        return breakers.get(key);
    }

    public static void putIfAbsent(String key,CircuitBreaker circuitBreaker){
        breakers.putIfAbsent(key,circuitBreaker);
    }
}
