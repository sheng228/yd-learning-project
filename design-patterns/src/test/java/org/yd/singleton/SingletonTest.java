package org.yd.singleton;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * @Description TODO
 * @Author XUZS
 * @Date 21-4-8 16:52
 * @Version 1.0
 **/
public class SingletonTest {

    /**
     * 反射攻击验证
     * @throws Exception
     */
    @Test
    public void reflectTest() throws Exception {
        Singleton03 s = Singleton03.getInstance();

        // 拿到所有的构造函数，包括非public的
        Constructor<Singleton03> constructor = Singleton03.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // 构造实例
        Singleton03 reflection = constructor.newInstance();

        System.out.println(s);
        System.out.println(reflection);
        System.out.println(s == reflection);
    }

    /**
     * 序列化验证
     */
    @Test
    public void serializeTest(){
        Singleton03 s = Singleton03.getInstance();

        String serialize = JSON.toJSONString(s);
        Singleton03 deserialize =JSON.parseObject(serialize,Singleton03.class);

        System.out.println(s);
        System.out.println(deserialize);
        System.out.println(s == deserialize);

    }

    /**
     * 枚举类反射验证
     * @throws Exception
     */
    @Test
    public void reflectEnumTest() throws Exception {
        EnumSingleton01 s = EnumSingleton01.INSTANCE;

        // 拿到所有的构造函数，包括非public的
        Constructor<EnumSingleton01> constructor = EnumSingleton01.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // 构造实例
        EnumSingleton01 reflection = constructor.newInstance();

        System.out.println(s);
        System.out.println(reflection);
        System.out.println(s == reflection);
    }

    /**
     * 枚举类序列化验证
     */
    @Test
    public void serializeEnumTest(){
        EnumSingleton01 s = EnumSingleton01.INSTANCE;

        String serialize = JSON.toJSONString(s);
        EnumSingleton01 deserialize =JSON.parseObject(serialize,EnumSingleton01.class);

        System.out.println(s);
        System.out.println(deserialize);
        System.out.println(s == deserialize);
    }
}
