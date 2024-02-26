package com.lxf.multithread.book;

/**
 * @Description:  安全的懒加载初始化
 * 缺点：如果getInstance()方法被多个线程频繁调用，将会导致程序执行性能下降
 * @Author: xiaofei.li
 * @Date: 2020/11/8 11:16
 */
public class SafeLazyInitialization {
    private static Instance instance;
    public synchronized static Instance getInstance() {
        //1:线程A
        if (instance==null) {
            //2:线程B
            instance=new Instance();
        }
        return instance;
    }
}
