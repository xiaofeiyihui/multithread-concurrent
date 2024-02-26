package com.lxf.multithread.self.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/16 17:11
 */
public class AqsAwaitDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("begin wait");
                condition.await();
                System.out.println("end wait");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println("begin signal");
                condition.signal();
                System.out.println("end signal");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---");
    }
}
