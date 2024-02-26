package com.lxf.multithread.book.thread;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/8 20:56
 */
public class DaemonThread {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("finally");
            }
        }, "DaemonThread");
        daemonThread.setDaemon(Boolean.TRUE);
        daemonThread.start();
    }
}
