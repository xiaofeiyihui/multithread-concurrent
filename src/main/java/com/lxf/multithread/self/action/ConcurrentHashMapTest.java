package com.lxf.multithread.self.action;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 通过分析Tomcat中NioEndPoint的实现源码介绍了并发组件ConcurrentLinkedQueue的使用。NioEndPoint的思想也是使用队列将同步转为异步，
 * 并且由于ConcurrentLinkedQueue是无界队列，所以需要让用户提供一个设置队列大小的接口以防止队列元素过多导致OOM。
 *
 *
 * @Description:
 *
 * ConcurrentHashMap使用注意事项：
 *  put（K key, V value）方法判断如果key已经存在，则使用value覆盖原来的值并返回原来的值，如果不存在则把value放入并返回null。
 *  而putIfAbsent（Kkey, V value）方法则是如果key已经存在则直接返回原来对应的值并不使用value覆盖，如果key不存在则放入value并返回null，
 *  另外要注意，判断key是否存在和放入是原子性操作。
 *
 * @Author: xiaofei.li
 * @Date: 2020/11/24 18:53
 */
public class ConcurrentHashMapTest {
    /**
     * key:topic,value:设备列表
     */
    static ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<String, List<String>>();
    public static void main(String[] args) {
       // putTest();


        putIfAbsentTest();
    }

    private static void putIfAbsentTest() {
        Thread thread1 = new Thread(() -> {
            List<String> list = Lists.newArrayList();
            list.add("device1");
            list.add("device2");
            List<String> oldList = map.putIfAbsent("topic1", list);
            if (null!=oldList) {
                oldList.addAll(list);
            }
            System.out.println(JSON.toJSONString(map));
        });
        Thread thread2 = new Thread(() -> {
            List<String> list = Lists.newArrayList();
            list.add("device11");
            list.add("device22");
            List<String> oldList = map.putIfAbsent("topic1",list);
            if (null!=oldList) {
                oldList.addAll(list);
            }
            System.out.println(JSON.toJSONString(map));
        });

        Thread thread3 = new Thread(() -> {
            List<String> list = Lists.newArrayList();
            list.add("device111");
            list.add("device221");
            List<String> oldList = map.putIfAbsent("topic2",list);
            if (null!=oldList) {
                oldList.addAll(list);
            }
            System.out.println(JSON.toJSONString(map));
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    private static void putTest() {
        Thread thread1 = new Thread(() -> {
            List<String> list = Lists.newArrayList();
            list.add("device1");
            list.add("device2");
            map.put("topic1",list);
            System.out.println(JSON.toJSONString(map));
        });
        Thread thread2 = new Thread(() -> {
            List<String> list = Lists.newArrayList();
            list.add("device11");
            list.add("device22");
            map.put("topic1",list);
            System.out.println(JSON.toJSONString(map));
        });

        Thread thread3 = new Thread(() -> {
            List<String> list = Lists.newArrayList();
            list.add("device111");
            list.add("device221");
            map.put("topic2",list);
            System.out.println(JSON.toJSONString(map));
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
