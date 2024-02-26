package com.lxf.multithread.book.forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/12/6 11:28
 */
public class CountTask extends RecursiveTask<Integer> {
    /**
     * 分割的任务大小
     */
    private static final int THRESHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = end - start <= THRESHOLD;
        //执行计算
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //大于阈值，继续分割
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，得到结果
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            //合并任务结果
            sum = leftResult + rightResult;
        }
        return sum;
    }
}
