package com.lxf.multithread.self.thread;

/**
 * @Description: 虚假唤醒  共享对象没有调用notify   interrupter  超时等
 *
 * 定义：
 * ​ 在多线程的情况下，当多个线程执行了wait()方法后，需要其它线程执行notify()或者notifyAll()方法去唤醒，假如被阻塞的多个线程都被唤醒，
 * 但实际情况是被唤醒的线程中有一部分线程是不应该被唤醒的，那么对于这些不应该被唤醒的线程而言就是虚假唤醒。
 *
 * 分析：
 *      当data为1的时候，线程A和B先后获取锁去生产数据的时候会被阻塞住，然后消费者C或者D消费掉数据后去notifyAll()唤醒了线程A和B，
 * 被唤醒的A和B没有再次去判断data状态，就去执行后续增加数据的逻辑了，导致两个生产者都执行了increment()，最终data出现了2这种情况。
 * 也就是说线程A和B有一个是不应该被唤醒的却被唤醒了，
 * 解决方案：
 *      出现这个问题的关键点在于程序中使用到了if判断，只判断了一次data的状态，应该使用while循环去判断
 * @Author: xiaofei.li
 * @Date: 2020/11/13 16:21
 */
public class FakeNotify {
    private static final int count =5;
    public static void main(String[] args) {
        Data data = new Data();
        /*生产A*/
        new Thread(()->{
            for (int i = 0; i < count; i++) {
                try {
                    data.incrementIf();
                    data.incrementWhile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        /*生产B*/
        new Thread(()->{
            for (int i = 0; i < count; i++) {
                try {
                    data.incrementIf();
                    data.incrementWhile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        /*消费C*/
        new Thread(()->{
            for (int i = 0; i < count; i++) {
                try {
                    data.decrementIf();
                    data.decrementWhile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        /**
         * 消费D
         */
        new Thread(()->{
            for (int i = 0; i < count; i++) {
                try {
                    data.decrementIf();
                    data.decrementWhile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }

    static class Data {
        private int numIf =0;
        private int numWhile =0;

        public synchronized void  incrementIf()throws InterruptedException {
            if (numIf !=0) {
                this.wait();
            }
            numIf++;
            System.out.println(Thread.currentThread().getName()+"生产了IF数据"+ numIf);
            this.notifyAll();
        }

        public synchronized void decrementIf() throws InterruptedException{
            if (numIf ==0) {
                this.wait();
            }
            numIf--;
            System.out.println(Thread.currentThread().getName()+"消费了IF数据"+ numIf);
            this.notifyAll();
        }

        public synchronized void  incrementWhile()throws InterruptedException {
            while (numWhile !=0) {
                this.wait();
            }
            numWhile++;
            System.out.println(Thread.currentThread().getName()+"生产了while数据"+ numWhile);
            this.notifyAll();
        }

        public synchronized void decrementWhile() throws InterruptedException{
            while (numWhile ==0) {
                this.wait();
            }
            numWhile--;
            System.out.println(Thread.currentThread().getName()+"消费了while数据"+ numWhile);
            this.notifyAll();
        }
    }
}
