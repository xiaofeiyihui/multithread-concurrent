package com.lxf.multithread.self.concurrentUtil;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description:
 *
 * 在没有原子类的情况下，实现计数器需要使用一定的同步措施，比如使用synchronized关键字等，但是这些都是阻塞算法，对性能有一定损耗，
 * 而本章介绍的这些原子操作类都使用CAS非阻塞算法，性能更好。但是在高并发情况下AtomicLong还会存在性能问题。
 * JDK 8提供了一个在高并发下性能更好的LongAdder类，
 *
 * JDK 8中新增的LongAdder原子性操作类，该类通过内部cells数组分担了高并发下多线程同时对一个原子变量进行更新时的竞争量，
 * 让多个线程可以同时对cells数组里面的元素进行并行的更新操作。
 *
 * LongAdder类是LongAccumulator的一个特例，LongAccumulator比LongAdder的功能更强大。
 *
 * @Author: xiaofei.li
 * @Date: 2020/11/15 18:51
 */
public class AtomicDemo {
    private static AtomicLong atomicLong=new AtomicLong();
    private static LongAdder longAdder = new LongAdder();
    private static LongAccumulator longAccumulator = new LongAccumulator((long left, long right) -> {
        return left + right;
    }, 1);

    private static Integer[] array1=new Integer[]{0,1,2,3,0,5,6,0,5,6,0};
   private static Integer[] array2=new Integer[]{10,1,2,3,0,5,6,0,5,6,0};
    public static void main(String[] args) {

        atomicLongTest();


        longAdderTest();


        longAccumulatorTest();


    }

    private static void longAccumulatorTest() {
        Thread thread1 = new Thread(() -> {
            for (Integer integer : array1) {
                if (integer == 0) {
                    longAccumulator.accumulate(1);
                }

            }
        });

        Thread thread2 = new Thread(() -> {
            for (Integer integer : array2) {
                if (integer == 0) {
                    longAccumulator.accumulate(1);
                }
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

        System.out.println("longAccumulator count:"+longAccumulator.get());
    }

    private static void longAdderTest() {
        Thread thread1 = new Thread(() -> {
            for (Integer integer : array1) {
                if (integer == 0) {
                    longAdder.increment();
                }

            }
        });

        Thread thread2 = new Thread(() -> {
            for (Integer integer : array2) {
                if (integer == 0) {
                    longAdder.increment();
                }
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

        System.out.println("longAdder count:"+longAdder.sum());
    }

    private static void atomicLongTest() {
        Thread thread1 = new Thread(() -> {
            for (Integer integer : array1) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                }

            }
        });


        Thread thread2 = new Thread(() -> {
            for (Integer integer : array2) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                }
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

        System.out.println("atomicLong count:"+atomicLong.get());
    }
}
