package com.lxf.multithread.self.concurrentUtil.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/30 07:39
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"准备park");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+"唤醒");
        });

        thread.start();
        System.out.println(Thread.currentThread().getName()+"准备sleep");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName()+"调用unpark");
        LockSupport.unpark(thread);
    }
}
