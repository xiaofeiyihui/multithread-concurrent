package com.lxf.multithread.self.aqs;

import java.util.concurrent.locks.StampedLock;

/**
 * @Description:  二维坐标点  StampedLock保证线程安全
 * @Author: xiaofei.li
 * @Date: 2020/11/17 19:37
 */
public class Point {
    private double x,y;
    private final StampedLock sl = new StampedLock();

    /**
     * 移动坐标,写锁
     * 写场景
     * @param deltaX
     * @param deltaY
     */
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x+=deltaX;
            y+=deltaY;
        }finally {
            sl.unlockWrite(stamp);
        }

    }

    /**
     * 距离原点距离，乐观读锁（tryOptimisticRead）
     * 读多写少场景
     * @return
     */
    double distanceFromOrigin() {
        // 获取乐观锁，由于tryOptimisticRead并没有使用CAS设置锁状态，所以不需要显式地释放该锁
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        // 检查锁是否被其他写线程抢占
        if (!sl.validate(stamp)) {
            // 获取悲观读锁
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        //计算距离
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    /**
     * 从原点移动到指定坐标，读锁（乐观或者悲观）转换成写锁
     * 读加写多场景
     * @param newX
     * @param newY
     */
    void moveIfAtOrigin(double newX, double newY) {
        // 获取读锁
        long stamp = sl.readLock();
        try {
            //是原点
            while (x==0.0 && y==0.0) {
                //尝试转换成写锁
                long stampWrite = sl.tryConvertToWriteLock(stamp);
                //转换成功，设置坐标点
                if (stampWrite != 0L) {
                    stamp = stampWrite;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    //转换失败，释放读锁，换成写锁
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            //释放锁
            sl.unlock(stamp);
        }
    }
}
