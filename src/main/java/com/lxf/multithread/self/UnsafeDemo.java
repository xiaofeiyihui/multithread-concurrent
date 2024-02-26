package com.lxf.multithread.self;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/11/14 16:32
 */
public class UnsafeDemo {

    // 不使用反射
    /*Unsafe类是rt.jar包提供的，rt.jar包里面的类是使用Bootstrap类加载器加载的，而我们的启动main函数所在的类是使用AppClassLoader加载的，
    所以在main函数里面加载Unsafe类时，根据委托机制，会委托给Bootstrap去加载Unsafe类。*/
    private static final Unsafe theUnsafe =Unsafe.getUnsafe();
    static final long stateOffset;
    private volatile long state = 0;
    static {
        try {
            stateOffset=theUnsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    static final Unsafe unsafe1;
    static final long stateOffset2;
    private volatile long state2 = 0;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("unsafe1");
            field.setAccessible(true);
            unsafe1 = (Unsafe)field.get(null);
            stateOffset2 = unsafe1.objectFieldOffset(UnsafeDemo.class.getDeclaredField("state2"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        unsafeReflectTest();
        try {
            unsafeTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void unsafeReflectTest() {
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        //如果unsafeDemo对象中内存偏移量为stateOffset的state变量的值为0，则更新该值为1。
        boolean b = theUnsafe.compareAndSwapInt(unsafeDemo, stateOffset2, 0, 1);
        System.out.println(b);
    }

    private static void unsafeTest() {
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        //如果unsafeDemo对象中内存偏移量为stateOffset的state变量的值为0，则更新该值为1。
        boolean b = theUnsafe.compareAndSwapInt(unsafeDemo, stateOffset, 0, 1);
        System.out.println(b);
    }


}
