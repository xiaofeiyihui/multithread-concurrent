package com.lxf.multithread.self.concurrentUtil.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/27 18:13
 */
public class Driver2 {
    private static int N = 5;
    /**
     * 另一种典型用法是将一个问题分为N个部分，*用执行该部分的Runnable描述每个部分，在锁存器上递减计数，然后将所有Runnable排队给Executor。
     * 当所有子部分都完成后，协调线程*将能够通过等待。 （当线程必须以这种方式重复*递减计数时，请使用{@link CyclicBarrier}。）*/
    public static void main(String[] args) throws InterruptedException{
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = Executors.newFixedThreadPool(N);

        for (int i = 0; i < N; ++i) {
            // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));
        }
        doneSignal.await();           // wait for all to finish

        System.out.println("==");
    }
}

