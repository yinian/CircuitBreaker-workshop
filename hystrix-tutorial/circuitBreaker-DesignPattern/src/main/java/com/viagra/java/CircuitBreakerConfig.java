package com.viagra.java;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 09:53
 * @Description:
 */
public class CircuitBreakerConfig {

    //closed状态的失败次数阈值
    private int failThreshold = 5;

    //closed状态的失败计数的时间窗口
    private int failCountWindowInMs = 60*1000;

    //处于open状态下进入half-open的超时时间
    private int open2HalfOpenTimeoutInMs = 5*1000;

    //half-open状态下成功次数阈值
    private int consecutiveSuccThreshold = 5;

    private CircuitBreakerConfig(){

    }

    public static CircuitBreakerConfig newDefault(){
        CircuitBreakerConfig config = new CircuitBreakerConfig();
        return config;
    }

    public int getFailThreshold() {
        return failThreshold;
    }

    public void setFailThreshold(int failThreshold) {
        this.failThreshold = failThreshold;
    }

    public int getFailCountWindowInMs() {
        return failCountWindowInMs;
    }

    public void setFailCountWindowInMs(int failCountWindowInMs) {
        this.failCountWindowInMs = failCountWindowInMs;
    }

    public int getOpen2HalfOpenTimeoutInMs() {
        return open2HalfOpenTimeoutInMs;
    }

    public void setOpen2HalfOpenTimeoutInMs(int open2HalfOpenTimeoutInMs) {
        this.open2HalfOpenTimeoutInMs = open2HalfOpenTimeoutInMs;
    }

    public int getConsecutiveSuccThreshold() {
        return consecutiveSuccThreshold;
    }

    public void setConsecutiveSuccThreshold(int consecutiveSuccThreshold) {
        this.consecutiveSuccThreshold = consecutiveSuccThreshold;
    }
}
