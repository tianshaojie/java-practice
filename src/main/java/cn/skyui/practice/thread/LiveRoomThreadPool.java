package cn.skyui.practice.thread;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据重要性，自动分配不同的拒绝策略
 * 1. 重要的优先执行&拒绝后可以被恢复
 * 2. 非重要信息被忽略掉
 * 3. 可以根据性能、内存判断队列大小、
 *
 * @author tianshaojie
 * @date 2017/12/14
 */
public class LiveRoomThreadPool {


    private ThreadPoolExecutor threadPoolExecutor;
    private BoundedPriorityBlockingQueue queue;


    public static LiveRoomThreadPool getInstance() {
        LiveRoomThreadPool threadPool = new LiveRoomThreadPool();
        threadPool.init();
        return threadPool;
    }

    /**
     * 线程池初始化方法
     *
     * corePoolSize 核心线程池大小---2
     * maximumPoolSize 最大线程池大小---5
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间---60+单位TimeUnit
     * TimeUnit keepAliveTime时间单位---TimeUnit.SECONDS
     * workQueue 带优先级阻塞队列---new PriorityBlockingQueue<>(50)---50容量的阻塞队列
     * threadFactory 新建线程工厂---new CustomThreadFactory()---定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maximumPoolSize+workQueue之和时，即当提交第56个任务时会交给RejectedExecutionHandler来处理
     */
    private void init() {
        queue = new BoundedPriorityBlockingQueue<>(300, new PriorityCompare<>());
        threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                60,
                TimeUnit.SECONDS,
                queue,
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
//                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 在未来某个时间执行给定的命令
     * <p>该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。</p>
     *
     * @param command 命令
     */
    public void execute(final PriorityRunnable command) {
        threadPoolExecutor.execute(command);
    }

    /**
     * 在未来某个时间执行给定的命令链表
     * <p>该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。</p>
     *
     * @param commands 命令链表
     */
    public void execute(final List<PriorityRunnable> commands) {
        for (PriorityRunnable command : commands) {
            threadPoolExecutor.execute(command);
        }
    }

    /**
     * 提交一个Runnable任务用于执行
     *
     * @param task   任务
     * @param result 返回的结果
     * @param <T>    泛型
     * @return 表示任务等待完成的Future, 该Future的{@code get}方法在成功完成时将会返回该任务的结果。
     */
    public <T> Future<T> submit(final PriorityRunnable task, final T result) {
        return threadPoolExecutor.submit(task, result);
    }


    public void shutdown() {
        try {
            threadPoolExecutor.shutdown();
            if (!threadPoolExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }
    }

    public void clearQueue() {
        if(queue != null) {
            queue.clear();
        }
    }


    private class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("LiveRoomThreadPool" + count.addAndGet(1));
            return thread;
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
    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

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

    /**
     * 点赞事件
     */
    abstract static class PraiseRunnable extends PriorityRunnable {

        String msg;

        PraiseRunnable(String msg) {
            super(PriorityConstants.PRIORITY_LOW);
            this.msg = msg;
        }
    }

    public static void main(String[] args) {

        LiveRoomThreadPool exec = new LiveRoomThreadPool();
        // 初始化
        exec.init();



        // 1. 模拟送星星
        for (int i = 1; i < 10; i++) {
            final int j = i;
            System.out.println("提交第" + i + "个送星星任务!");
            exec.execute(new PriorityRunnable(PriorityConstants.PRIORITY_NORMAL) {
                @Override
                public void run() {
                    try {
                        System.out.println(">>> ThreadName: " + Thread.currentThread().getName() + ", StarRunnable:" + j + " is running");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // 2. 模拟点赞-正常优先级
        for (int i = 1; i < 100; i++) {
            final int j = i;
            System.out.println("提交第" + i + "个点赞任务!");
            exec.execute(new PraiseRunnable("PraiseRunnable-" + j) {
                @Override
                public void run() {
                    try {
                        System.out.println(">>> ThreadName: " + Thread.currentThread().getName() + ", PraiseRunnable:" + j + " is running");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // 3. 模拟送礼
        for (int i = 1; i < 10; i++) {
            final int j = i;
            System.out.println("提交第" + i + "个送礼任务!");
            exec.execute(new PriorityRunnable(PriorityConstants.PRIORITY_HIGH) {
                @Override
                public void run() {
                    try {
                        System.out.println(">>> ThreadName: " + Thread.currentThread().getName() + ", GiftRunnable:" + j + " is running");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        exec.execute(new PriorityRunnable(PriorityConstants.PRIORITY_HIGH) {
            @Override
            public void run() {
                exec.clearQueue();
            }
        });


        // 停止
//        exec.shutdown();

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
