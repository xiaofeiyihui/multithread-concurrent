package com.lxf.multithread.self.action;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 *
 * 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，
 * 其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题
 *
 * @Author: xiaofei.li
 * @Date: 2020/11/25 07:30
 */
public class TimerTest {
    public static void main(String[] args) {
        //timerTest();

        scheduledThreadPoolExecutorTest();
    }

    /**
     * ScheduledThreadPoolExecutor的其他任务不受抛出异常的任务的影响，是因为在ScheduledThreadPoolExecutor中的ScheduledFutureTask任务中catch掉了异常，
     * 但是在线程池任务的run方法内使用catch捕获异常并打印日志是最佳实践。
     */
    private static void scheduledThreadPoolExecutorTest() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("---task one--");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }
        },500, TimeUnit.MILLISECONDS);

        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("---task two--");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1000,TimeUnit.MILLISECONDS);
    }


    /**
     * Timer的内部实现是一个多生产者-单消费者模型。当任务在执行过程中抛出InterruptedException之外的异常时，
     * 唯一的消费线程就会因为抛出异常而终止，那么队列里的其他待执行的任务就会被清除。所以在TimerTask的run方法内
     * 最好使用try-catch结构捕捉可能的异常，不要把异常抛到run方法之外
     */
    private static void timerTest() {
        Timer timer = new Timer();
        //延迟500ms执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("---task one--");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }
        }, 500L);

        //延迟1s
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("---task two--");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1000L);
    }
}
