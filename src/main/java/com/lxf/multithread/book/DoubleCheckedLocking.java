package com.lxf.multithread.book;

/**
 * @Description:  双重检查 初始化单例
 * 但是会出现问题，原因 new Instance()会包含如下指令：
 * 1.分配对象的内存空间
 * 2.初始化对象
 * 3.设置instance指向内存空间
 *
 * JMM允许在不改变程序运行结果的前提下做出指令重排，2,3步重排后不会改变运行结果，所以会出现
 *  时间              线程A                         线程B
 *  t1             A1:分配对象空间
 *  t2            A3:设置instance指向内存空间
 *  t3                                              B1：判断instance是否为空
 *  t4                                              B2:由于instance不为null,线程B将访问instance应用对象
 *  t5              A2：初始化对象
 *  t6              A4：访问instance应用对象
 *
 *
 * @Author: xiaofei.li
 * @Date: 2020/11/8 11:20
 */
public class DoubleCheckedLocking {
    private  static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {    //第一次检查
            synchronized (DoubleCheckedLocking.class) {  //加锁
                if (instance == null) {   //第二次检查
                    instance=new Instance();  //问题的根源
                }
            }
        }
        return  instance;
    }
}
