package com.lxf.multithread.book;

/**
 * @Description:  基于类的初始化锁懒加载单例
 * jvm在class被加载后，且被线程使用之前，会执行类的初始化。在执行类的初始化期间，jvm会去获取一个锁，这个锁可以同步
 * 多个线程对同一个类的初始化。
 * 所以第一次在InstanceHolder.instance处会加锁保证初始化，即使2,3步骤重排序也不会影响
 * 1.分配对象的内存空间
 * 2.初始化对象
 * 3.设置instance指向内存空间
 *
 * @Author: xiaofei.li
 * @Date: 2020/11/8 11:37
 */
public class SafeInstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }
    public static Instance getInstance() {
        return InstanceHolder.instance;
    }
}
