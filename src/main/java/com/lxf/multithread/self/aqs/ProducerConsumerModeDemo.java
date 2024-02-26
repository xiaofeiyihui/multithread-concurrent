package com.lxf.multithread.self.aqs;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/16 18:19
 */
public class ProducerConsumerModeDemo {
    //final  static NonReentrantLock lock = new NonReentrantLock();
    final  static ReentrantLock lock = new ReentrantLock();
    final  static Condition notFull = lock.newCondition();
    final  static  Condition notEmpty = lock.newCondition();
    final  static int queueSize =10;
    final  static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    static int count =20;
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Thread producer = new Thread(() -> {
            int producerRandom = random.nextInt(queueSize,count);
            System.out.println("producerRandom:"+producerRandom);
            for (int i = 0; i < producerRandom; i++) {
                lock.lock();
                try {
                    while (queue.size()==queueSize) {
                        //阻塞生产队列
                        notEmpty.await();

                    }
                    System.out.println("producer:ele"+i);
                    queue.add("ele"+i);
                    //唤醒消费队列
                    notFull.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            int consumerRandom = random.nextInt(queueSize,count);
            System.out.println("consumerRandom:"+consumerRandom);
            for (int i = 0; i <  consumerRandom; i++) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        notFull.await();
                    }
                    String poll = queue.poll();
                    System.out.println("poll:"+poll);
                    notEmpty.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
