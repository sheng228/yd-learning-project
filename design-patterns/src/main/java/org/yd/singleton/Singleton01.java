package org.yd.singleton;

/**
 * @Description 懒汉模式(线程不安全)
 * @Author XUZS
 * @Date 21-4-7 16:15
 * @Version 1.0
 **/
public class Singleton01 {

    private static Singleton01 instance;

    /**
     * 私有构造方法
     */
    private Singleton01(){}

    public static Singleton01 getInstance() {
        if(instance == null) {
            instance = new Singleton01();
        }

        return instance;
     }
}
