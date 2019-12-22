package com.viagra.command;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
/**
 * @Auther: viagra
 * @Date: 2019/12/10 17:08
 * @Description:
 */


// 降级处理
public class HelloCommand extends HystrixCommand<String> {

    private String name;

    public HelloCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("test"));
    }

    public HelloCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }





    @Override
    protected String run() throws Exception {
        //模拟请求外部接口需要的时间长度
//        Thread.sleep(50000);
        Thread.sleep(500);
        return "sucess";
    }

    @Override
    protected String getFallback() {
        //当外部请求超时后，会执行fallback里的业务逻辑
        System.out.println("执行了回退方法");
        return "error";
    }

}