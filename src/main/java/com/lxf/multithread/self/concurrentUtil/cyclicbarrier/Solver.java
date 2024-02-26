package com.lxf.multithread.self.concurrentUtil.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/10/29 22:11
 */
public class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;



    public Solver(float[][] matrix)throws InterruptedException {
        data = matrix;
        N = matrix.length;
        Runnable barrierAction =
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName()+":mergeRows");
                    }
                };
        barrier = new CyclicBarrier(N, barrierAction);

        List<Thread> threads = new ArrayList<Thread>(N);
        for (int i = 0; i < N; i++) {
            Thread thread = new Thread(new Worker(i));
            threads.add(thread);
            thread.start();
        }
        // wait until done
        for (Thread thread : threads) {
            thread.join();
        }
    }

    class Worker implements Runnable {
        int myRow;

        Worker(int row) {
            myRow = row;
        }

        @Override
        public void run() {
            while (!done()) {
                System.out.println(Thread.currentThread().getName()+":processRow");
                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        boolean done() {
            boolean b = myRow < 0;
            --myRow;
            return b;
        }


    }
}
