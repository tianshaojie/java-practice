package cn.skyui.practice.thread;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by tiansj on 16/7/17.
 */
public class TestFuture {

    public static void main(String[] args) {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "over";
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask futureTask = new FutureTask(callable);
        System.out.println("futureTask线程现在开始启动，启动时间为：" + System.currentTimeMillis());
        executorService.submit(futureTask);
//        while (!futureTask.isDone()) {
//            try {
//                Thread.sleep(500);
//                System.out.println("私有账户计算未完成继续等待...");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        // 测试阻塞Future.get()方法
//        try {
//            String result = (String) futureTask.get();
//            System.out.println("futureTask线程计算完毕，此时时间为" + System.currentTimeMillis());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = (String) futureTask.get(5000, TimeUnit.MILLISECONDS);
                    System.out.println("测试阻塞Future.get()方法" + result + "，此时时间为" + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    System.out.println("测试阻塞Future.get()方法InterruptedException，此时时间为" + System.currentTimeMillis());
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    System.out.println("测试阻塞Future.get()方法ExecutionException，此时时间为" + System.currentTimeMillis());
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    System.out.println("测试阻塞Future.get()方法TimeoutException，此时时间为" + System.currentTimeMillis());
                    e.printStackTrace();
                }
            }
        });

        System.out.println("主线程执行，此时时间为" + System.currentTimeMillis());

        List<Runnable> list = executorService.shutdownNow();
        if(executorService.isShutdown()) {
            System.out.println("主线程执行，isShutdown=true" + System.currentTimeMillis());
            executorService = Executors.newSingleThreadExecutor();
        }

        FutureTask futureTask2 = new FutureTask(callable);
        executorService.submit(futureTask2);
        try {
            String rs2 = (String) futureTask2.get(5000, TimeUnit.MILLISECONDS);
            System.out.println("主线程执行，futureTask2此时时间为" + rs2 + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        executorService.shutdownNow();
    }

}
