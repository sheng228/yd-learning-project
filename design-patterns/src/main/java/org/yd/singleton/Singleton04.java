package org.yd.singleton;

/**
 * @Description 饿汉模式
 * @Author XUZS
 * @Date 21-4-7 16:15
 * @Version 1.0
 **/
public class Singleton04 {

    private static Singleton04 instance = new Singleton04();

    /**
     * 私有构造方法
     */
    private Singleton04(){}

    public static synchronized Singleton04 getInstance() {
        return instance;
    }
}
