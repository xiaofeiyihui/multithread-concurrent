package com.lxf.multithread.book.container;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/9 19:57
 */
public class HashMapCycle {
    private final static HashMap<String, String> map = new HashMap<>(2);

    public static void main(String[] args) {
        int count = 10000;
        Thread thread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName());
                    map.put(UUID.randomUUID().toString(), "==");
                }, "xxx" + i).start();
            }
        }, "xxx");

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
