package com.lxf.multithread.self.concurrentUtil.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/27 22:42
 */
public class CountDownLatchDemo {
    private static int N = 5;
    public static void main(String[] args) throws InterruptedException{
        initAssembly(N);
        System.out.println("=============");
        System.out.println("=============");
        System.out.println("=============");
        raceSignal(N);
    }

    /**
     * 主应用初始化组件
     */
    private static void initAssembly(int n) throws InterruptedException{
        CountDownLatch application = new CountDownLatch(n);
        ExecutorService executorService = Executors.newFixedThreadPool(n + 1);
        System.out.println("initAssembly应用开始");
        for (int i = 0; i < n; i++) {
            Runnable assembly = new Runnable() {
                @Override
                public void run() {
                    System.out.println("assembly:"+Thread.currentThread().getName()+"初始化");
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("assembly:"+Thread.currentThread().getName()+"初始化完成");
                    application.countDown();
                }
            };
            executorService.execute(assembly);
        }
        application.await();
        System.out.println("initAssembly应用完成");


    }

    /**
     * 比赛信号枪，类似多个线程在某一时刻墙用资源
     */
    private static void raceSignal(int n) throws InterruptedException{
        CountDownLatch signal = new CountDownLatch(1);
        CountDownLatch threadRace = new CountDownLatch(n);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < n; i++) {
            Runnable thread = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "准备");
                        signal.await();
                        System.out.println(Thread.currentThread().getName() + "开始处理");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(Thread.currentThread().getName() + "处理完成");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        threadRace.countDown();
                    }
                }
            };
            executorService.execute(thread);
        }
        Thread.sleep((long) (Math.random() * 1000));
        signal.countDown();
        System.out.println("信号开始");
    }
}
