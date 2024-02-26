package com.lxf.multithread.self.concurrentUtil.locksupport;

/**
 * @Description:  模拟先进先出 FIFO
 * @Author: xiaofei.li
 * @Date: 2020/11/2 22:47
 */
public class LockSupportFIFODemo {
    public static void main(String[] args) throws InterruptedException{
        FIFOMutex fifoMutex = new FIFOMutex();

        MyThread a1 = new MyThread("a1", fifoMutex);
        MyThread a2 = new MyThread("a2", fifoMutex);
        MyThread a3 = new MyThread("a3", fifoMutex);

        a1.start();
        a2.start();
        a3.start();
        a1.join();
        a2.join();
        a3.join();
        System.out.println(MyThread.count);

        System.out.println("over");
    }
}
