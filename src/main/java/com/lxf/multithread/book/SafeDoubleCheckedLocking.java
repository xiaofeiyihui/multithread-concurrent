package com.lxf.multithread.book;

/**
 * @Description:  安全的懒加载单例
 * 基于volatile，不允许 指令重排序
 * new Instance()包含如下指令：
 *  1.分配对象的内存空间
 * 2.初始化对象
 * 3.设置instance指向内存空间
 * @Author: xiaofei.li
 * @Date: 2020/11/8 11:08
 */
public class SafeDoubleCheckedLocking {
    private volatile static Instance instance;
    private static Instance instance2;
    public static Instance getInstance(){
        //线程B
        if (instance == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance == null) {
                    //线程A
                    instance = new Instance();
                }
            }
        }
        return instance;
    }


    /**
     * 不安全的双重锁校验
     * 线程A获取了锁，new new Instance()只执行了 1.分配对象的内存空间，3.设置instance指向内存空间
     * 线程B抢到cpu，判断instance2 == null，此时instance2 是Instance的默认值，不为null,将Instance的默认值返回，就出现了线程不安全的情况
     * @return
     */
    public static Instance getInstanceNotSafe(){
        //线程B
        if (instance2 == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance2 == null) {
                    //线程A
                    instance2 = new Instance();
                }
            }
        }
        return instance2;
    }


}
