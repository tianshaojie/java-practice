package com.yuzhi.fine.synchronize;

public class SynchronizedPractice {

    Object object = new Object();

    public synchronized void a() {
        System.out.println("a");
    }

    public static synchronized void b() {
        System.out.println("b");
    }

    public void c() {
        synchronized (object) {
            System.out.println("c");
        }
    }

}
