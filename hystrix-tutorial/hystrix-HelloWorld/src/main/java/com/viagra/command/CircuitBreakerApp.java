package com.viagra.command;

/**
 * @Auther: viagra
 * @Date: 2019/12/11 10:09
 * @Description:
 */
public class CircuitBreakerApp {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            HelloCircuitBreakerCommand command = new HelloCircuitBreakerCommand();
            String result = command.execute();
            System.out.println("circuit Breaker is open : " + command.isCircuitBreakerOpen());
            if(command.isCircuitBreakerOpen()){
                Thread.currentThread().sleep(500);
            }
        }
    }
}
