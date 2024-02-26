package com.lxf.multithread.self.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 *
 * isInterrupted(): 不会清楚中断标志
 * interrupted()：清除中断标志
 *
 * @Author: xiaofei.li
 * @Date: 2020/11/13 19:13
 */
public class InterrupterDemo {
    public static void main(String[] args) {
        interrupterTest();

        System.out.println("=========");

        interrupterDiffTest();




    }

    private static void interrupterDiffTest() {
        Thread thread = new Thread(() -> {
            for (;;) {

            }
        });

        thread.start();

        thread.interrupt();
        System.out.println("thread.isInterrupted:"+thread.isInterrupted());
        System.out.println("Thread.interrupted:"+Thread.interrupted());
        System.out.println("thread.isInterrupted:"+thread.isInterrupted());

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main over.");
    }

    private static void interrupterTest() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName()+"run");
            }
        });

        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main interrupter thread");

        thread.interrupt();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main over");
    }
}
