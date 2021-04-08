package org.yd.singleton;

/**
 * @Description 静态内部类
 * @Author XUZS
 * @Date 21-4-7 16:15
 * @Version 1.0
 **/
public class Singleton05 {

    // 静态内部类
    private static class SingletonHolder {
        private static final Singleton05 INSTANCE = new Singleton05();
    }

    /**
     * 私有构造方法
     */
    private Singleton05(){}

    public static Singleton05 getInstance() {
        return Singleton05.SingletonHolder.INSTANCE;
    }
}
