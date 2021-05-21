package cn.hanlyjiang.hjapf.slice;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

import cn.hanlyjiang.apf_library.utils.RefInvoker;

/**
 * android Singleton 类的反射修改测试
 *
 * @author hanlyjiang 5/21/21 4:11 PM
 * @version 1.0
 */
public class SingletonRefInvokeTest {

    /**
     * 测试对Singleton的反射及修改instance，涉及类
     */
    @Test
    public void test_refSingleton() {
        // 通过静态方法获取接口对应的实际对象
        final Object instance = RefInvoker.invokeStaticMethod(SingletonEntry.class, "getService");
        // 获取Singleton对象
        Object singletonObj = RefInvoker.getStaticField(SingletonEntry.class, "mSingletonInstance");
        // 修改代理实际对象
        Object proxyInstance = Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                // 这里需要所有所有接口
                new Class[]{SingletonObjInterface.class}, (proxy, method, args) -> {
                    Object invoke = method.invoke(instance, args);
                    if (invoke instanceof String) {
                        return String.format("ADD ME: %s", invoke);
                    }
                    return invoke;
                });
        RefInvoker.setField(singletonObj, Singleton.class, "mInstance", proxyInstance);
        String invokeShow = SingletonEntry.invokeShow();
        Assert.assertEquals("ADD ME: " + RefInvoker.invokeMethod(instance, SingletonEntry.class, "show"),
                invokeShow);
        System.out.println("Call:" + invokeShow);
    }
}
