package org.yd.proxy.dynamic;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Method;

/**
 * @Description Javassist 代理
 * @Author XUZS
 * @Date 21-4-9 16:16
 * @Version 1.0
 **/
public class JavassistProxyHandler implements MethodHandler {

    private ProxyFactory proxyFactory = new ProxyFactory();

    /**
     * 获取代理对象
     * @param clazz 被代理类
     * @return
     * @throws Exception
     */
    public Object getProxy(Class clazz) throws Exception {
        proxyFactory.setSuperclass(clazz);
        Class<?> factoryClass = proxyFactory.createClass();
        Object proxy = factoryClass.newInstance();
        ((ProxyObject)proxy).setHandler(this);
        return proxy;
    }

    public Object invoke(Object object, Method method, Method method1, Object[] args) throws Throwable {
        System.out.println("处理前");
        Object result = method1.invoke(object,args);
        System.out.println("处理后");
        return result;
    }
}
