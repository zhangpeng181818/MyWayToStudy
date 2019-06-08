package com.nuc.zp.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * auther: ZP
 * time:   2019/5/15 9:18
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {

    }

    private static void mapNotSafe() {
        Map<String,String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap<>();//new HashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    private static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet<>();//new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    private static void listNotSafe() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }

        /**
         * 1、故障现象
         *            java.util.ConcurrentModificationException
         * 2、导致原因
         *              并发争抢修改导致
         * 3、解决方案
         *             3.1  new Vector<>()
         *             3.2  Collections.synchronizedList(new ArrayList<>())
         *             3.3 new CopyOnWriteArrayList<>()
         * 4、优化建议
         *            写时复制
         *            CopyOnWrite容器即写时复制的容器，往一个容器添加元素的时候，
         *            不直接往当前容器Object[]添加，而是先将当前容器Object[]进行
         *            Copy，复制出一个新的容器Object[] newElements，然后新的容器
         *            Object[] newElements里添加元素，添加完元素之后，再将原容器
         *            的引用指向新的容器 setArray(new Elements);这样做的好处是
         *            可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器
         *            不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，
         *            读和写不同的容器。
         */}
}
