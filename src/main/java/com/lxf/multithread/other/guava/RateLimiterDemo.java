package com.lxf.multithread.other.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/3 13:51
 */
public class RateLimiterDemo {
    public static void main(String[] args) {
        //rateLimiterDemo();

        rateLimiterDemo2();



    }

    private static void rateLimiterDemo2() {
        final RateLimiter rateLimiter = RateLimiter.create(2);

        for (int i = 0; i < 10; i++) {
            long timeOut = (long) 0.5;
            boolean isValid = rateLimiter.tryAcquire(timeOut, TimeUnit.SECONDS);
            System.out.println("任务" + i + "执行是否有效:" + isValid);
            if (!isValid) {
                continue;
            }
            System.out.println("任务" + i + "在执行");
        }
        System.out.println("结束");
    }

    private static void rateLimiterDemo() {
        //1秒产生1个令牌
        final RateLimiter rateLimiter = RateLimiter.create(2);
        for (int i = 0; i < 10; i++) {
            //该方法会阻塞线程，直到令牌桶中能取到令牌为止才继续向下执行。
            double waitTime= rateLimiter.acquire();
            System.out.println("任务执行" + i + "等待时间" + waitTime);
        }
        System.out.println("执行结束");
    }


}
