package com.lxf.multithread.self.action;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/25 12:13
 */
public class ThreadLocalTest {
   final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
   final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    localVariable.set(new LocalVariable());
                    System.out.println("use local variable");
                    localVariable.remove();
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("over");
    }

    static class LocalVariable {
        private Long[] a =new Long[1024*1024];
    }
}
