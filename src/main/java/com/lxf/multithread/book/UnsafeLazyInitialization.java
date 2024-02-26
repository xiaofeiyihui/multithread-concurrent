package com.lxf.multithread.book;

/**
 * @Description: 多线程下会有竞争，不安全
 * @Author: xiaofei.li
 * @Date: 2020/11/29 10:58
 */
public class UnsafeLazyInitialization {
    private static Instance instance;

    public static Instance getInstance() {
        //1:线程A
        if (instance==null) {
            //2:线程B
            instance=new Instance();
        }
        return instance;
    }
}
