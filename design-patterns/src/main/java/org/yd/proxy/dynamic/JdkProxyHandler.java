package org.yd.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description JDK库动态代理
 * @Author XUZS
 * @Date 21-4-9 14:55
 * @Version 1.0
 **/
public class JdkProxyHandler implements InvocationHandler {

    private Object target;

    public JdkProxyHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("处理前");
        Object result = method.invoke(target,args);
        System.out.println("处理后");
        return result;
    }
}
