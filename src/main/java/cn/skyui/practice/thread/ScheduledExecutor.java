package cn.skyui.practice.thread;

import java.util.concurrent.*;

public class ScheduledExecutor {

    private static ScheduledExecutorService executorService;

    private ScheduledExecutor() {
        executorService = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("ScheduledExecutor"));
    }

    private static class Holder {
        private final static ScheduledExecutor INSTANCE = new ScheduledExecutor();
    }

    public static ScheduledExecutor getInstance() {
        return Holder.INSTANCE;
    }

    public ScheduledExecutorService getThreadPool(){
        return executorService;
    }

    public static void main(String[] args) {
        ScheduledExecutor scheduledExecutor = ScheduledExecutor.getInstance();
        ScheduledFuture<?> future = scheduledExecutor.getThreadPool().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("111111");
            }
        }, 0, 1, TimeUnit.SECONDS);


        try {
            Thread.sleep(3000L);
            future.cancel(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(1000L);

            future = scheduledExecutor.getThreadPool().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("222222");
                }
            }, 0, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000L);
            future.cancel(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduledExecutor.getThreadPool().shutdown();
    }
}
