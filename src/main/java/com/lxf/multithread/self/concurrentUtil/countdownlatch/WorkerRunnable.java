package com.lxf.multithread.self.concurrentUtil.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/27 18:17
 */
public class WorkerRunnable implements Runnable {
    private final CountDownLatch doneSignal;
    private final int i;

    WorkerRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            doWork(i);
            doneSignal.countDown();
        } catch (Exception ex) {
        }
    }

    void doWork(int i) {
        System.out.println("do work:"+i);
    }
}
