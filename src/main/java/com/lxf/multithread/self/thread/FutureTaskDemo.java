package com.lxf.multithread.self.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/13 13:51
 */
public class FutureTaskDemo {

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "11";
        }
    }

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
