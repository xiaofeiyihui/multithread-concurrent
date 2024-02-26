package com.lxf.multithread.book.thread;

/**
 * @Description:  睡眠的线程 中断标识被清除，一直工作的线程中断标识没有被清除
 * @Author: xiaofei.li
 * @Date: 2020/11/8 21:05
 */
public class InterruptedThread {
    public static void main(String[] args) {
        Thread sleepThread = new Thread(() -> {
            while (true) {
                SleepUtils.second(100);
            }
        }, "sleepThread");

        Thread busyThread = new Thread(() -> {
            while (true) {

            }
        }, "busyThread");

        sleepThread.setDaemon(true);
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        //主线程休眠5s,充分运行sleepThread  busyThread
        SleepUtils.second(5);
        //主线程打断两个线程
        sleepThread.interrupt();
        busyThread.interrupt();
        //输出interrupt标识

        System.out.println("sleepThread interrupted is :"+sleepThread.isInterrupted());
        System.out.println("busyThread interrupted is :"+busyThread.isInterrupted());

    }
}
