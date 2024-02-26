package com.lxf.multithread.self.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/17 15:01
 */
public class ReentrantLockList {
    private final List<String> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    public void add(String e) {

        writeLock.lock();
        try {
            list.add(e);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(String e) {
        writeLock.lock();
        try {
            list.remove(e);
        } finally {
            writeLock.unlock();
        }
    }

    public String getIndex(int index) {
        readLock.lock();
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }
}
