package com.lxf.multithread.book.thread;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/8 21:14
 */
public class ShutsownThread {
    public static void main(String[] args) throws Exception{
        Runner one = new Runner();
        Thread countThread = new Thread(one, "countThread-1");
        countThread.start();
        //主线程睡眠1S,然后对countThread 中断
        SleepUtils.second(1);
        countThread.interrupt();

        Runner two = new Runner();
        countThread = new Thread(two, "countThread-2");
        countThread.start();
        SleepUtils.second(1);
        two.cancel();
    }

    static class Runner implements Runnable {
        private long i;
        private volatile boolean on =true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("count i = "+i);
        }

        public void cancel(){
            on = false;
        }

    }
}
