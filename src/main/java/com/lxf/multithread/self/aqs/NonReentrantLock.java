package com.lxf.multithread.self.aqs;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description:  自定义不可重入的独占锁
 * state为0表示目前锁没有被线程持有，state为1表示锁已经被某一个线程持有，由于是不可重入锁，
 * 所以不需要记录持有锁的线程获取锁的次数。另外，我们自定义的锁支持条件变量。
 * @Author: xiaofei.li
 * @Date: 2020/11/16 17:48
 */
public class NonReentrantLock implements Lock,java.io.Serializable{

    private Sync sync =new Sync();
    @Override
    public void lock() {
        sync.acquire(1);
    }
    /*中断响应*/
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            Objects.equals(arg,1);
            if (compareAndSetState(0,1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            Objects.equals(arg,1);
            if (Thread.currentThread() != getExclusiveOwnerThread() || getState() != 1) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }
    }
}
