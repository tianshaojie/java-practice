package cn.skyui.practice.thread;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by tiansj on 17/6/17.
 */
public class TestFutureTimeout {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService timeoutExecutorService = Executors.newSingleThreadExecutor();

        sendMessage(executorService, timeoutExecutorService, "110");
        sendMessage(executorService, timeoutExecutorService, "119");
        sendMessage(executorService, timeoutExecutorService, "120");

        executorService.shutdown();
        timeoutExecutorService.shutdown();
    }

    public static void sendMessage(ExecutorService executorService, ExecutorService timeoutExecutorService, String str) {
        HashMap hashMap = new HashMap();

        // 执行一个超时任务
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                System.out.println("=====success====="+str);
                return "success";
            }
        });

        // 通过Future timeout 取消任务
        timeoutExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = future.get(500, TimeUnit.MILLISECONDS);
                    System.out.println("=====future.get()=====" + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    System.out.println("=====timeout=====" + str);

                    // 重要，timeout时取消任务，否则任务依然会被执行
                    future.cancel(true);
                }
            }
        });

    }

}
