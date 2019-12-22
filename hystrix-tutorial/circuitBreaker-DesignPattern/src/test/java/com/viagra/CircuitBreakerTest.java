package com.viagra;

import com.viagra.java.exception.CircuitBreakerOpenException;
import com.viagra.java.handler.ProxyFactory;
import com.viagra.service.DemoService;
import com.viagra.service.DemoServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: viagra
 * @Date: 2019/12/19 12:51
 * @Description:
 */
public class CircuitBreakerTest {

    DemoService demoService;

    @Before
    public void prepare(){
        demoService = ProxyFactory.proxyBean(new DemoServiceImpl());
    }

    @Test
    public void testSucc(){
        for(int i = 0;i<100;i++){
            System.out.println(demoService.getUuid(1));
        }
    }

    @Test(expected = CircuitBreakerOpenException.class)
    public void testOpen(){
        for(int i=0;i<2 ;i++){
            try{
                demoService.getUuid(2);
            }catch (Exception e){

            }
        }
        demoService.getUuid(1);
    }

    @Test
    public void testFailWindow() throws InterruptedException {
        for(int i=0;i<6;i++){
            Thread.sleep(2 * 1000);
            try{
                demoService.getUuid(2);
            }catch (Exception e){

            }
        }
        try{
            demoService.getUuid(2);
        }catch (Exception e){

        }
    }

    @Test
    public void testHalfOpen2Open() throws InterruptedException {
        for(int i=0;i<6;i++){
            try{
                demoService.getUuid(2);
            }catch (Exception e){

            }
        }
        //into half open
        TimeUnit.SECONDS.sleep(10);
        try{
            demoService.getUuid(2);
        }catch (Exception e){

        }
        //into open
    }

    @Test
    public void testHalfOpen2Close() throws InterruptedException {
        for(int i=0;i<6;i++){
            try{
                demoService.getUuid(2);
            }catch (Exception e){

            }
        }
        //into half open
        TimeUnit.SECONDS.sleep(4);
        for(int i=0;i<6;i++){
            demoService.getUuid(1);
        }
        //into close
    }

    @Test
    public void testNoTripException(){
        for(int i=0;i<6;i++){
            try{
                demoService.illegalEx(2);
            }catch (Exception e){

            }
        }
        // still close
        try{
            demoService.illegalEx(2);
        }catch (Exception e){

        }
    }
}
