package org.yd.singleton;

/**
 * @Description 懒汉模式(双重校验锁)
 * @Author XUZS
 * @Date 21-4-7 16:15
 * @Version 1.0
 **/
public class Singleton03 {

    private volatile static Singleton03 instance;

    /**
     * 私有构造方法
     */
    private Singleton03(){}

    public static Singleton03 getInstance() {
        if(instance == null) {
            synchronized (Singleton03.class) {
                if (instance == null) {
                    instance = new Singleton03();
                }
            }
        }

        return instance;
     }
}
