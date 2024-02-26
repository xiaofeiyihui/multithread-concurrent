package com.lxf.multithread.self.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/13 17:45
 */
public class NotifyDemo {
    private static final int max_size=10;
    private static final int count = 10000;
    public static void main(String[] args) throws InterruptedException{
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(max_size);
        Thread produceThread = new Thread(() -> {
            synchronized (queue) {
                for (int i = 0; i < count; i++) {
                    System.out.println(Thread.currentThread().getName()+":produceThread---"+queue.size());
                    while (queue.size()==max_size) {
                        try {
                            System.out.println(Thread.currentThread().getName()+":produceThread---wait");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(1);
                    queue.notifyAll();
                }

            }

        });

        Thread consumerThread = new Thread(() -> {
            synchronized (queue) {
                for (int i = 0; i < count; i++) {
                    System.out.println(Thread.currentThread().getName()+":consumerThread---"+queue.size());
                    while (queue.size()==0) {
                        try {
                            System.out.println(Thread.currentThread().getName()+":consumerThread---wait");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":consumerThread---"+queue.size());
                    queue.notifyAll();
                }

            }

        });


        produceThread.start();
        consumerThread.start();
        produceThread.join();
        consumerThread.join();
    }
}
