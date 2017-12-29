package com.yuzhi.fine.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据重要性，自动分配不同的拒绝策略
 * 1. 重要的优先显示&拒绝后可以被恢复
 * 2. 非重要信息被忽略掉
 * 3. 可以根据性能、内存判断队列大小、
 *
 * @author tianshaojie
 * @date 2017/12/14
 */
public class CustomThreadPool {

    public static void main(String[] args) {

        CustomThreadPool exec = new CustomThreadPool();
        // 1.初始化
        exec.init();

        ExecutorService pool = exec.getCustomThreadPoolExecutor();
        for(int i=1; i<100; i++) {
            System.out.println("提交第" + i + "个任务!");
            final int j = i;

//            pool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(">>> ThreadName: " + Thread.currentThread().getName() + ", RunnableNum:" + j + " is running");
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

//            pool.execute(new PraiseRunnable(j+"") {
//
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(">>> ThreadName: " + Thread.currentThread().getName() + ", RunnableNum:" + j + " is running");
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });


            pool.execute(new PriorityRunnable(j) {
                @Override
                public void run() {
                    try {
                        System.out.println(">>> ThreadName: " + Thread.currentThread().getName() + ", RunnableNum:" + j + " is running");
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        // 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了
        // exec.destory();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private ThreadPoolExecutor pool = null;

    /**
     * 线程池初始化方法
     *
     * corePoolSize 核心线程池大小----10
     * maximumPoolSize 最大线程池大小----30
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
     *                          即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
     *                                任务会交给RejectedExecutionHandler来处理
     */
    private void init() {
        pool = new ThreadPoolExecutor(
                1,
                3,
                30,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(5, new PriorityCompare()),
//                new ArrayBlockingQueue<Runnable>(5),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());

                // 该策略是线程池的默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
//                new ThreadPoolExecutor.AbortPolicy());

                // 这个策略和AbortPolicy的slient版本，如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
//                new ThreadPoolExecutor.DiscardPolicy());

                //丢弃最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。因为队列是队尾进，队头出，所以队头元素是最老的，因此每次都是移除对头元素后再尝试入队
//                new ThreadPoolExecutor.DiscardOldestPolicy());

                // 使用此策略，如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。
//                new ThreadPoolExecutor.CallerRunsPolicy());

    }

    public void destroy() {
        if(pool != null) {
            pool.shutdownNow();
        }
    }


    public ExecutorService getCustomThreadPoolExecutor() {
        return this.pool;
    }

    private class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("CustomThreadPool" + count.addAndGet(1));
            return thread;
        }
    }

    /**
     * 点赞事件
     */
    abstract static class PraiseRunnable implements Runnable {
        String msg;
        PraiseRunnable(String msg) {
            this.msg = msg;
        }
    }

    //

    // 重要事件：自己点赞、自己送礼、中奖、
    // 非重要事件：外部点赞、别人送礼、

    /**
     * 根据重要性，自动分配不同的拒绝策略
     * 1. 重要的优先显示&拒绝后可以被恢复
     * 2. 非重要信息被忽略掉
     * 3. 可以根据性能、内存判断队列大小
     */
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            // 记录异常
            // 报警处理等
            System.out.println("error.............");
            if(runnable instanceof PraiseRunnable) {
                System.out.println(((PraiseRunnable) runnable).msg + " is rejected");
            }
        }
    }
}
