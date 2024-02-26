package com.lxf.multithread.self.thread;

/**
 * @Description:  阻塞当前线程，让join线程执行完，如果当前线程被interrupter,
 * 会在调用join处抛InterruptedException
 * @Author: xiaofei.li
 * @Date: 2020/11/13 18:16
 */
public class JoinDemo {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        joinTest(mainThread);
        System.out.println("============");
        joinInterrupterTest(mainThread);
    }


    /**
     * 可以用countdownLatch
     * @param mainThread
     */
    private static void joinTest(Thread mainThread) {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1 over.");
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 over.");
        });

        thread1.start();
        thread2.start();
        System.out.println("wait child");
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wait over.");
    }


    private static void joinInterrupterTest(Thread mainThread) {
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 run");
            for (;;) {

            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 mainThread.interrupt.");
            mainThread.interrupt();
        });

        thread1.start();
        thread2.start();
        System.out.println("wait child");
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wait over.");
    }
}
