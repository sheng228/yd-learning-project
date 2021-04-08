# 设计模式
[TOC]

## 一、单例模式

单例模式是设计模式中使用最为普遍的一种模式。属于对象创建模式，它可以确保系统中一个类只产生一个实例。这样的行为能带来两大好处：

- 对于频繁使用的对象，可以省略创建对象所花费的时间，这对于那些重量级对象而言，是非常可观的一笔系统开销。
- 由于new操作的次数减少，因而对系统内存的使用频率也会降低，这将减轻GC压力，缩短GC停顿时间。

在实际应用中，很多时候有一些对象我们只需要一个，例如：线程池（threadpool）、缓存（cache）、注册表（registry）、日志对象等等，这个时候把它设计为单例模式是最好的选择。

### 1、单例模式6种实现方法

#### 1）懒汉模式(线程不安全)
 
```
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
```

**这种写法实现延迟加载，但线程不安全。禁止使用！**


#### 2）懒汉模式(线程安全)

```
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
```

**这种写法实现延迟加载，且增加synchronized来保证线程安全，但效率太低。不建议使用**


#### 3）懒汉模式(双重校验锁)

```
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
```

使用到了volatile机制。这个是第二种方式的升级版，俗称双重检查锁定。既保证了效率，又保证了安全。


#### 4）饿汉模式

```
public class Singleton03 {

    private static Singleton03 instance = new Singleton03();

    /**
     * 私有构造方法
     */
    private Singleton03(){}

    public static synchronized Singleton03 getInstance() {
        return instance;
     }
}
```

**这种基于类加载机制避免了多线程的同步问题，初始化的时候就给装载了。但却没了懒加载的效果。
这也是最简单的一种实现。**


#### 5）静态内部类

```
public class Singleton04 {

    // 静态内部类
    private static class SingletonHolder {
        private static final Singleton04 INSTANCE = new Singleton04();
    }

    /**
     * 私有构造方法
     */
    private Singleton04(){}

    public static Singleton04 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

**这种方式当`Singleton04`类被加载时，其内部类并不会被加载，所以单例类`INSTANCE`不会被初始化。
只有显式调用`getInstance`方法时，才会加载`SingletonHolder`，从而实例化`INSTANCE`。
由于实例的建立是在类加载时完成，所以天生线程安全。因此兼备了懒加载和线程安全的特性。**

#### 6）枚举（号称最好）

```
public enum EnumSingleton01 {

    INSTANCE;

    public void doSomething() {
        System.out.println("doSomething");
    }
}
```

**模拟数据库链接：**
```
public enum EnumSingleton02 {

    INSTANCE;

    private DBConnection dbConnection = null;

    private EnumSingleton02() {
        dbConnection = new DBConnection();
    }

    public DBConnection getConnection() {
        return dbConnection;
    }
}
```

**这种方式是`Effective Java`作者`Josh Bloch`提倡的方式，它不仅能避免多线程同步问题，
而且还能防止反序列化重新创建新的对象。**


### 2、为什么说枚举方法是最好的？

前5种方式实现单例都有如下3个特点：
- 构造方法私有化
- 实例化的变量引用私有化
- 获取实例的方法共有

**首先，私有化构造器并不保险。因为它抵御不了`反射攻击`,其次就是序列化重新创建新对象。下面来进行验证。**

#### 1） 反射验证
```
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
```

输出结果：
```
org.yd.singleton.Singleton03@61e4705b
org.yd.singleton.Singleton03@50134894
false
```

再看看枚举类的测试

```
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
```

输出结果：

```
java.lang.NoSuchMethodException: org.yd.singleton.EnumSingleton01.<init>()
	at java.lang.Class.getConstructor0(Class.java:3082)
	at java.lang.Class.getDeclaredConstructor(Class.java:2178)
	at org.yd.singleton.SingletonTest.reflectEnumTest(SingletonTest.java:61)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
```

**结论：通过反射，单例模式的私有构造方法也能构造出新对象。不安全。而枚举类直接抛异常，说明枚举类对反射是安全的。**

#### 2） 序列化验证

```
@Test
public void serializeTest(){
    Singleton03 s = Singleton03.getInstance();

    String serialize = JSON.toJSONString(s);
    Singleton03 deserialize =JSON.parseObject(serialize,Singleton03.class);

    System.out.println(s);
    System.out.println(deserialize);
    System.out.println(s == deserialize);

}
```

输出结果：

```
org.yd.singleton.Singleton03@387c703b
org.yd.singleton.Singleton03@75412c2f
false
```
**结论：序列化前后两个对象并不相等。所以序列化也是不安全的。**

同样看看枚举类的测试

```
@Test
public void serializeEnumTest(){
     EnumSingleton01 s = EnumSingleton01.INSTANCE;
 
     String serialize = JSON.toJSONString(s);
     EnumSingleton01 deserialize =JSON.parseObject(serialize,EnumSingleton01.class);
 
     System.out.println(s);
     System.out.println(deserialize);
     System.out.println(s == deserialize);
}
```

输出结果：

```
INSTANCE
INSTANCE
true
```

**结论：说明枚举类序列化安全。**

------------

综上，可以得出结论：枚举是实现单例模式的最佳实践。

- 反射安全
- 序列化/反序列化安全
- 写法简单


