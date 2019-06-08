package com.nuc.zp.juc;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现一个自旋锁
 * 自旋锁的好处：循环比较获取直到成功为止，没有类似wait的阻塞
 *
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，
 * B随后进来发现当前有线程持有锁，不是null，所以只能通过自旋等待，
 * 直到A释放锁后B随后抢到
 */
public class SpinLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            spinLockDemo.myUnLock();
        },"t1").start();

        Thread.sleep(1000);

        new Thread(()->{
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        },"t2").start();
    }

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t invoked myLock");
        while (!atomicReference.compareAndSet(null,thread)){
        }
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock");
    }
}
