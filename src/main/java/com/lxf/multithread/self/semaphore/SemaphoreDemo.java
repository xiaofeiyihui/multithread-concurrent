package com.lxf.multithread.self.semaphore;

import com.google.errorprone.annotations.Var;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description:  模拟  10个用户使用5个有限资源
 * @Author: xiaofei.li
 * @Date: 2020/11/4 23:01
 */
public class SemaphoreDemo {
    private static Semaphore semaphore = new Semaphore(5);
    private static Integer resourceNum =10;
    public static void main(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(resourceNum);
        for (Integer integer = 0; integer < resourceNum; integer++) {
            executorService.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+":准备获取资源");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+":使用资源");
                    TimeUnit.SECONDS.sleep(10);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+":释放资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        executorService.shutdown();
    }
}
