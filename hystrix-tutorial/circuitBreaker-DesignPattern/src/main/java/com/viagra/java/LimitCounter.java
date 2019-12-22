package com.viagra.java;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 10:13
 * @Description: 带时间窗口的限流计数器
 */
public class LimitCounter {

    private long startTime;
    private long timeIntervalInMs;
    private int maxLimit;
    private AtomicInteger currentCount;

    public LimitCounter(long timeIntervalInMs, int maxLimit) {
        super();
        this.timeIntervalInMs = timeIntervalInMs;
        this.maxLimit = maxLimit;
        startTime = System.currentTimeMillis();
        currentCount = new AtomicInteger(0);
    }


    public int incrAndGet() {
        long currentTime = System.currentTimeMillis();
        if ((startTime + timeIntervalInMs) < currentTime) {
            synchronized (this) {
                if ((startTime + timeIntervalInMs) < currentTime) {
                    startTime = currentTime;
                    currentCount.set(0);
                }
            }
        }
        return currentCount.incrementAndGet();
    }

    public boolean thresholdReached(){
        return currentCount.get() > maxLimit;
    }

    public int get(){
        return currentCount.get();
    }

    public /*synchronized*/ void reset(){
        currentCount.set(0);
    }
}
