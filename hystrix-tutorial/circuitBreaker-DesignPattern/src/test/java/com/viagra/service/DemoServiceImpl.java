package com.viagra.service;

import java.util.UUID;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 12:51
 * @Description:
 */
public class DemoServiceImpl implements DemoService{
    public String getUuid(int idx) {
        if(idx % 2 == 0){
            throw new RuntimeException();
        }
        return UUID.randomUUID().toString() + idx;
    }

    public void illegalEx(int idx) {
        if(idx % 2 == 0){
            throw new IllegalArgumentException();
        }
    }
}
