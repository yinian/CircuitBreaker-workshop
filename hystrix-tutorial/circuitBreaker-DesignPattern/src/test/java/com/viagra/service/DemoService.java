package com.viagra.service;

import com.viagra.java.annotation.GuardByCircuitBreaker;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 12:51
 * @Description:
 */
public interface DemoService {

    @GuardByCircuitBreaker(noTripExceptions = {})
    public String getUuid(int idx);

    @GuardByCircuitBreaker(noTripExceptions = {IllegalArgumentException.class})
    public void illegalEx(int idx);
}
