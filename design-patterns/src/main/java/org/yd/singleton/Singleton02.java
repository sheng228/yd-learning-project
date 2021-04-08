package org.yd.singleton;

/**
 * @Description 懒汉模式(线程安全)
 * @Author XUZS
 * @Date 21-4-7 16:15
 * @Version 1.0
 **/
public class Singleton02 {

    private static Singleton02 instance;

    /**
     * 私有构造方法
     */
    private Singleton02(){}

    public static synchronized Singleton02 getInstance() {
        if(instance == null) {
            instance = new Singleton02();
        }

        return instance;
     }
}
