package org.yd.proxy.dynamic;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description CGLIB动态代理
 * @Author XUZS
 * @Date 21-4-9 15:14
 * @Version 1.0
 **/
public class CglibProxyInterceptor implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    /**
     * 获取代理类
     * @param clazz
     * @return
     */
    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("处理前");
        Object result = methodProxy.invokeSuper(object,args);
        System.out.println("处理后");
        return result;
    }
}
