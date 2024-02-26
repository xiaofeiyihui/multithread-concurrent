package com.lxf.multithread.self.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Description:  SimpleDateFormat线程不安全
 * 不安全的原因：SimpleDateFormat之所以是线程不安全的，就是因为Calendar是线程不安全的。
 * 我们在使用SimpleDateFormat.parse()时，内部会使用到CalendarBuilder.establish()重构Calendar属性，
 * 重构步骤包括：1.清除Calendar对象的属性值，2.设置新属性，3.返回新值。这三个步骤不是原子操作。
 * 所以在多线程环境下共用一个SimpleDateFormat实例会出现并发问题
 *
 *  方案1：
 *  每次使用时new一个SimpleDateFormat的实例，这样可以保证每个实例使用自己的Calendar实例，但是每次使用都需要new一个对象，
 *  并且使用后由于没有其他引用，又需要回收，开销会很大。
 *  方案2：
 *  使用synchronized进行同步 SimpleDateFormat.parse()操作.
 *  进行同步意味着多个线程要竞争锁，在高并发场景下这会导致系统响应性能下降。
 *  方案3：
 *  使用ThreadLocal，这样每个线程只需要使用一个SimpleDateFormat实例，这相比第一种方式大大节省了对象的创建销毁开销，
 *  并且不需要使多个线程同步。
 *
 *  @Author: xiaofei.li
 * @Date: 2020/11/25 06:54
 */
public class SimpleDateFormatTest {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static ThreadLocal<DateFormat> sdfThreadLocal = new ThreadLocal<DateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    static int count =20;
    public static void main(String[] args) {
        //sdfErrorTest();
        parseBYSynchronizedTest();
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println("threadLocal:"+sdfThreadLocal.get().parse("2020-11-25 08:25:20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }finally {
                    //避免内存泄漏
                    sdfThreadLocal.remove();
                }
            });
            thread.start();
        }

    }

    private static void parseBYSynchronizedTest() {
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                try {
                    synchronized (sdf) {
                        System.out.println("synchronized:"+sdf.parse("2020-11-25 08:25:20"));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    private static void sdfErrorTest() {
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(sdf.parse("2020-11-25 08:25:20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }
}
