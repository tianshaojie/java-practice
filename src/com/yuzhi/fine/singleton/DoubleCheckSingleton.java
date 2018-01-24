package com.yuzhi.fine.singleton;

/**
 * @author tianshaojie
 * @date 2018/1/10
 */
public class DoubleCheckSingleton {

    /**
     * volatile 防止指令重排
     */
    private volatile static DoubleCheckSingleton instance;

    /**
     * 防止反射机制破坏单例
     */
    private DoubleCheckSingleton() {
        if(instance != null) {
            throw new RuntimeException("This is not allowed.");
        }
    }

    /**
     * double check 可以有效降低 synchronized 的性能损耗
     * @return
     */
    public static DoubleCheckSingleton getInstance() {
        if(instance == null) {
            synchronized(DoubleCheckSingleton.class) {
                if(instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

    /**
     * 防止反序列化破坏单例
     * @return
     */
    private Object readResolve(){
        return instance;
    }


    public void test() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        DoubleCheckSingleton.getInstance().test();
    }
}
