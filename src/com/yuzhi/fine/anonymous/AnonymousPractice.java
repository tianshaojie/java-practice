package com.yuzhi.fine.anonymous;

/**
 * Created by tiansj on 2017/12/9.
 */
public class AnonymousPractice {

    public static void main(String[] args) {
        printThread("666");
    }

    static void printThread(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(str);
            }
        }).start();
    }

}
