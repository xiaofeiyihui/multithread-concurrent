package com.lxf.multithread.book.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/12/6 11:44
 */
public class CountTaskDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 1000);
        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
