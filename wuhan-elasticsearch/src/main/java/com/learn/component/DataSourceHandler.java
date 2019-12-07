package com.learn.component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author dshuyou
 * @date 2019/12/4 15:41
 */
public class DataSourceHandler implements InvocationHandler {
    private Object proxyObj;

    public DataSourceHandler(Object proxy){
        this.proxyObj = proxy;
    }

    public static Object factory(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new DataSourceHandler(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(proxyObj,args);
    }
}