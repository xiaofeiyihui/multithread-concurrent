package com.lxf.multithread.self.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/5 22:23
 */
public class ExchangerDemo {
    private static Exchanger<String> exchanger = new Exchanger<String>();
    public static void main(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(2);



        Thread fillThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println(Thread.currentThread().getName() + ":装水");
                TimeUnit.SECONDS.sleep(3);
                String empty = exchanger.exchange("交换满桶了");
                System.out.println(Thread.currentThread().getName() + ":"+empty);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread emptyThread = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ":倒水");
                TimeUnit.SECONDS.sleep(3);
                String fill = exchanger.exchange("交换空桶了");
                System.out.println(Thread.currentThread().getName() + ":" + fill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        fillThread.start();
        emptyThread.start();

        executorService.shutdown();

    }
}
