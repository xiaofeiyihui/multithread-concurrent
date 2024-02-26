package com.lxf.multithread.self.concurrentUtil.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/27 17:10
 */
public class Worker implements Runnable  {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork();
        } catch (InterruptedException ex) {
        } finally {
            doneSignal.countDown();
        }
    }

    void doWork() {
        System.out.println("do work.");
    }
}
