package com.lxf.multithread.book.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/8 20:41
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
