package org.yd.proxy.dynamic;

import javassist.*;
import javassist.bytecode.AccessFlag;

/**
 * @Description Javassist动态java代码生成动态代理
 * @Author XUZS
 * @Date 21-4-13 10:18
 * @Version 1.0
 **/
public class JavassistDynamicCodeProxy {

    /**
     * 创建代理类
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object getProxy(Class clazz) throws Exception {
        ClassPool mPool = ClassPool.getDefault();
        CtClass c0 = mPool.get(clazz.getName());
        //定义代理类名称
        CtClass mCtc = mPool.makeClass(clazz.getName() + "$$BytecodeProxy");
        //添加父类继承
        mCtc.setSuperclass(c0);
        //添加类的字段信息
        CtField field = new CtField(c0, "real", mCtc);
        field.setModifiers(AccessFlag.PRIVATE);
        mCtc.addField(field);
        //添加构造函数
        CtConstructor constructor = new CtConstructor(new CtClass[]{c0},mCtc);
        constructor.setBody("{$0.real = $1;}"); // $0代表this, $1代表构造函数的第1个参数
        mCtc.addConstructor(constructor);
        //添加方法
        CtMethod ctMethod = mCtc.getSuperclass().getDeclaredMethod("send");
        CtMethod newMethod = new CtMethod(ctMethod.getReturnType(), ctMethod.getName(),ctMethod.getParameterTypes(), mCtc);
        newMethod.setBody("{" +
                "System.out.println(\"处理前\");" +
                "boolean result = $0.real.send();" +
                "System.out.println(\"处理后\");" +
                "return result;}");
        mCtc.addMethod(newMethod);

        //生成动态类
        return mCtc.toClass().getConstructor(clazz).newInstance(clazz.newInstance());
    }

}
