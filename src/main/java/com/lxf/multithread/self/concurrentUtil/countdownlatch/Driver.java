package com.lxf.multithread.self.concurrentUtil.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/27 16:59
 */
public class Driver {

    private static int N = 5;
    /** {{code CountDownLatch}是一种多功能的同步工具*，可以用于多种用途。
    由一个计数初始化为1的* {@code CountDownLatch}用作*简单的开/关锁存器或逻辑门：调用{@link #await await}的所有线程*在逻辑门中等待，
    直到被线程调用{ @link * #countDown}。初始化为<em> N </ em> *的{@code CountDownLatch}可用于使一个线程等待，
    直到<em> N </ em>线程完成某些动作或某些动作已完成N次。*/
    public static   void main(String[] args)throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);
        // create and start threads
        for (int i = 0; i < N; ++i) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        // don't let run yet
        System.out.println("startSignal.countDown() before");
        startSignal.countDown();      // let all threads proceed
        System.out.println("startSignal.countDown() after");
        doneSignal.await();           // wait for all to finish
        System.out.println("==");
    }


}


