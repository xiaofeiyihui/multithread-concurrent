package com.lxf.multithread.self.concurrentUtil.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/29 22:59
 */
public class CyclicBarrierDemo {
    private static int N = 5;

    public static void main(String[] args) {
        //cyclicBarrier(N);
        cyclicBarrierWithRunnableCommand(N);
    }

    public static void cyclicBarrier(int n) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N);
        task(cyclicBarrier,n);
    }

    public static void cyclicBarrierWithRunnableCommand(int n) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"command 执行");
            }
        });
        task(cyclicBarrier,n);
    }

    private static void task(CyclicBarrier cyclicBarrier,int n) {
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":执行-1-");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":执行-2-");
                }
            };
            executorService.execute(runnable);
        }
    }
}
