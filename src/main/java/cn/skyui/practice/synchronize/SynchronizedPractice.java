package cn.skyui.practice.synchronize;

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


    static class SyncObject {
        public synchronized void a() {
            System.out.println("aaa============");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public synchronized  void b() {
            System.out.println("bbb============");
        }
    }

    public static void main(String[] args) {
        SyncObject syncObject = new SyncObject();

        new Thread(() -> {
            syncObject.a();
            System.out.println("Tread1 end=============");
        }).start();
        new Thread(() -> {
            syncObject.b();
            System.out.println("Tread2 end=============");
        }).start();
        System.out.println("Main Tread=============");
        syncObject.b();
        System.out.println("Main Tread end=============");
    }

}
