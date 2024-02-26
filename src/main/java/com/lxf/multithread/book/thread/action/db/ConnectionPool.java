package com.lxf.multithread.book.thread.action.db;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/8 21:36
 */
public class ConnectionPool {
    private  LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                //把释放的线程加入线程池中，然后通知
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaing = mills;
                while (pool.isEmpty() && remaing > 0) {
                    pool.wait(remaing);
                    remaing = future - System.currentTimeMillis();
                }

                Connection result =null;
                if (!pool.isEmpty()) {
                    pool.removeFirst();
                }
                return result;

            }
        }
    }
}
