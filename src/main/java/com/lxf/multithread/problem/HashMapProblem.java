package com.lxf.multithread.problem;

/**
 * @Description:
 * @Author: xiaofei.li
 * @Date: 2020/12/24 13:46
 */
public class HashMapProblem {

    public static void main(String[] args) {
        Long key1 = 782550353L;
        Long key2 = 782550333L;
        int h;
        int hash1 = (h = key1.hashCode()) ^ (h >>> 16);
        int hash2 = (h = key2.hashCode()) ^ (h >>> 16);
        System.out.println("hash1:"+hash1+"--------------hash2:"+hash2);

        int i1 = (16 - 1) & hash1;
        int i2 = (16 - 1) & hash2;
        System.out.println("i1:"+i1+"--------------i2:"+i2);
    }
}
