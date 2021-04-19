package cn.skyui.practice.thread;

import java.util.concurrent.*;

public class ScheduledExecutor {


    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("666");
            }
        }, 1, 1, TimeUnit.SECONDS);


        scheduledExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("111");
            }
        });

//        scheduledExecutor.shutdown();
//        if(!scheduledExecutor.isShutdown()) {
//
//        }
//
        scheduledExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("222");
            }
        });



    }
}
