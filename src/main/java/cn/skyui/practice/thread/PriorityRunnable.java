package cn.skyui.practice.thread;

/**
 * @author tianshaojie
 * @date 2017/12/19
 */
public abstract class PriorityRunnable implements Runnable{

    public int priority;

    public PriorityRunnable(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

