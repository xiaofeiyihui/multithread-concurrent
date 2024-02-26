package com.lxf.multithread.self.thread;

/**
 * @Description:
 * ThreadLocal
 * 在每个线程内部都有一个名为threadLocals的成员变量，该变量的类型为HashMap，其中key为我们定义的ThreadLocal变量的this引用，
 * value则为我们使用set方法设置的值。每个线程的本地变量存放在线程自己的内存变量threadLocals中，如果当前线程一直不消亡，
 * 那么这些本地变量会一直存在，所以可能会造成内存溢出，因此使用完毕后要记得调用ThreadLocal的remove方法删除对应线程的
 * threadLocals中的本地变量
 *
 * InheritableThreadLocal类通过重写代码getMap()和setMap()让本地变量保存到了具体线程的inheritableThreadLocals变量里面，
 * 那么线程在通过InheritableThreadLocal类实例的set或者get方法设置变量时，就会创建当前线程的inheritableThreadLocals变量。
 * 当父线程创建子线程时，构造函数会把父线程中inheritableThreadLocals变量里面的本地变量复制一份保存到子线程的inheritableThreadLocals变量里面。
 * @Author: xiaofei.li
 * @Date: 2020/11/13 22:41
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> threadLocal =new ThreadLocal<String>();
    private static ThreadLocal<String> inheritableThreadLocal =new InheritableThreadLocal<>();
    public static void main(String[] args) {
        threadLocalTest();
        System.out.println("=========");
        inheritableThreadLocalTest();

    }

    private static void inheritableThreadLocalTest() {
        //主线程变量
        inheritableThreadLocal.set("inheritableThreadLocal:main");

        new Thread(()->{
            //
            System.out.println("inheritableThreadLocal:childThread:"+inheritableThreadLocal.get());
        }).start();

        System.out.println("inheritableThreadLocal:main thread:"+inheritableThreadLocal.get());
    }

    private static void threadLocalTest() {
        //主线程变量
        threadLocal.set("main");

        new Thread(()->{
            // childThread:null 说明 子线程中是获取不到父线程的变量
            System.out.println("childThread:"+threadLocal.get());
        }).start();

        System.out.println("main thread:"+threadLocal.get());
    }
}
