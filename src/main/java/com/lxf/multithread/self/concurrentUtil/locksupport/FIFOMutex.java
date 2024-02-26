package com.lxf.multithread.self.concurrentUtil.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/2 22:49
 */
public class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(Boolean.FALSE);
    private final Queue<Thread> waiters=new ConcurrentLinkedDeque<>();

    public void lock() {
        boolean wasInterrupter =false;
        Thread currentThread = Thread.currentThread();
        waiters.add(currentThread);
        // 如果当前线程不在队首，或锁已被占用，测当前线程阻塞
        // note: 这个判断的意图其实是：锁必须由队首元素拿到
        while (waiters.peek() != currentThread ||
           !locked.compareAndSet(false, true)) {
            System.out.println("currentThread:"+currentThread);
            LockSupport.park(this);
            if (Thread.interrupted()) {
                wasInterrupter=true;
            }
        }
        waiters.remove();
        if (wasInterrupter) {
            currentThread.interrupt();
        }
    }

    public void unlock() {
        locked.set(Boolean.FALSE);
        LockSupport.unpark(waiters.peek());
    }
}
