package org.yd.proxy;

import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.junit.Test;
import org.yd.proxy.dynamic.CglibProxyInterceptor;
import org.yd.proxy.dynamic.JavassistDynamicCodeProxy;
import org.yd.proxy.dynamic.JavassistProxyHandler;
import org.yd.proxy.dynamic.JdkProxyHandler;

import java.lang.reflect.Proxy;

/**
 * @Description 代理模式测试
 * @Author XUZS
 * @Date 21-4-9 14:15
 * @Version 1.0
 **/
public class ProxyTest {

    @Test
    public void testStaticProxy(){
        long begin = System.currentTimeMillis();
        ISender sender = new ProxySender(new SmsSender());
        System.out.println("创建耗时：" + (System.currentTimeMillis() - begin));
        boolean result = sender.send();
        System.out.println("输出结果：" + result);
    }

    @Test
    public void testJdkProxy(){
        ISender sender = (ISender) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{ISender.class},
                new JdkProxyHandler(new SmsSender()));
        boolean result = sender.send();
        System.out.println("代理对象：" + sender.getClass().getName());
        System.out.println("输出结果：" + result);
    }

    @Test
    public void testJdkProxy2(){
        BdSender sender = (BdSender) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                BdSender.class.getInterfaces(),
                new JdkProxyHandler(new BdSender()));
        boolean result = sender.send();
        System.out.println("代理对象：" + sender.getClass().getName());
        System.out.println("输出结果：" + result);
    }

    @Test
    public void testCglibProxy(){
        BdSender sender = (BdSender) new CglibProxyInterceptor().getProxy(BdSender.class);
        boolean result = sender.send();
        System.out.println("代理对象：" + sender.getClass().getName());
        System.out.println("输出结果：" + result);
    }

    @Test
    public void testJavassistProxy() throws Exception {
        BdSender sender = (BdSender) new JavassistProxyHandler().getProxy(BdSender.class);
        boolean result = sender.send();
        System.out.println("代理对象：" + sender.getClass().getName());
        System.out.println("输出结果：" + result);
    }

    @Test
    public void testJavassisBytecodetProxy() throws Exception {
        BdSender sender = (BdSender) JavassistDynamicCodeProxy.getProxy(BdSender.class);
        boolean result = sender.send();
        System.out.println("代理对象：" + sender.getClass().getName());
        System.out.println("输出结果：" + result);
    }
}
