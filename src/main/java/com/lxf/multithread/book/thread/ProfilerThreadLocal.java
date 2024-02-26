package com.lxf.multithread.book.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/8 21:27
 */
public class ProfilerThreadLocal {
    public static void main(String[] args)throws Exception {
        ProfilerThreadLocal.begin();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("cost:"+ProfilerThreadLocal.end()+"mills");
    }

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis()-TIME_THREADLOCAL.get();
    }
}
