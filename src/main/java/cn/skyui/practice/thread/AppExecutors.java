package cn.skyui.practice.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private final Executor mSingleThreadIO;

    private final Executor mNetworkIO;

    private AppExecutors(Executor diskIO, Executor networkIO) {
        this.mSingleThreadIO = diskIO;
        this.mNetworkIO = networkIO;
    }

    private AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3));
    }

    public static AppExecutors getInstance() {
        return SingletonClassInstance.instance;
    }

    public Executor singleThreadIO() {
        return mSingleThreadIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    private static class SingletonClassInstance {
        private static final AppExecutors instance = new AppExecutors();
    }


    public static void main(String[] args) {

        AppExecutors.getInstance().singleThreadIO().execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                for(int i = 0; i < 3; i++) {
                  doSomething();
                }
                System.out.println("111currentTimeMillis=" + System.currentTimeMillis() + "，耗时：" + (System.currentTimeMillis() - startTime));
            }
        });

        AppExecutors.getInstance().mNetworkIO.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                for(int i = 0; i < 3; i++) {
                    doSomething();
                }
                System.out.println("222currentTimeMillis=" + System.currentTimeMillis() + "，耗时：" + (System.currentTimeMillis() - startTime));
            }
        });


        AppExecutors.getInstance().mNetworkIO.execute(new Runnable() {
            @Override
            public void run() {
                doSomething();
                System.out.println("currentTimeMillis=" + System.currentTimeMillis());
            }
        });

        AppExecutors.getInstance().mNetworkIO.execute(new Runnable() {
            @Override
            public void run() {
                doSomething();
                System.out.println("currentTimeMillis=" + System.currentTimeMillis());
            }
        });

        AppExecutors.getInstance().mNetworkIO.execute(new Runnable() {
            @Override
            public void run() {
                doSomething();
                System.out.println("currentTimeMillis=" + System.currentTimeMillis());
            }
        });

    }

    public static void doSomething() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
