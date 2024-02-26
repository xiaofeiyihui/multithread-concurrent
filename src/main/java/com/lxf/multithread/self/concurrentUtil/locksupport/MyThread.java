package com.lxf.multithread.self.concurrentUtil.locksupport;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/2 23:00
 */
public class MyThread extends Thread {
    private String name;
    private FIFOMutex mutex;
    public static int count;

    private static int num=100;

    public MyThread(String name, FIFOMutex mutex) {
        this.name = name;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        mutex.lock();
        for (int i = 0; i < num; i++) {
            count++;
            System.out.println("name:"+name+"; count:"+count);
        }
        mutex.unlock();
    }
}
