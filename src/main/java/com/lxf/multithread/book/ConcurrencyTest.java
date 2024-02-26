package com.lxf.multithread.book;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/4 23:30
 */
public class ConcurrencyTest {

    //10亿
    private static final long COUNT =1000000000L;
    public static void main(String[] args)throws InterruptedException {
        concurrency(COUNT);
        serial(COUNT);

    }

    private static void concurrency(long count) throws InterruptedException{
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a =0;
            for (long i = 0; i < count; i++) {
                a+=5;
            }
        });
        thread.start();
        int b=0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency:"+time + "ms,b="+b);
    }

    private static void serial(long count) {
        long start = System.currentTimeMillis();
        int a =0;
        for (long i = 0; i < count; i++) {
            a+=5;
        }

        int b=0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial:"+time + "ms,b="+b);
    }
}
