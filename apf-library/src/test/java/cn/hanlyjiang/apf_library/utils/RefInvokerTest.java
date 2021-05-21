package cn.hanlyjiang.apf_library.utils;

import org.junit.Assert;
import org.junit.Test;

import cn.hanlyjiang.apf_library.utils.ref.RefTestClazz;

/**
 * RefInvoker工具测试
 *
 * @author hanlyjiang 5/21/21 11:22 AM
 * @version 1.0
 */

public class RefInvokerTest {

    public static String mTestClassName = RefTestClazz.class.getName();
    public static Class mTestClaszz = RefTestClazz.class;


    @Test
    public void test_forName() throws ClassNotFoundException {
        Assert.assertNotNull("测试是否构建成功",
                RefInvoker.forName(mTestClassName));
    }


    @Test
    public void test_newInstance_with_className() throws ClassNotFoundException {
        Object newInstance = RefInvoker.newInstance(mTestClassName);
        Assert.assertNotNull(newInstance);
        Assert.assertTrue(newInstance instanceof RefTestClazz);
    }

    @Test
    public void test_newInstance_with_clazz() throws ClassNotFoundException {
        Object newInstance = RefInvoker.newInstance(mTestClaszz);
        Assert.assertNotNull(newInstance);
        Assert.assertTrue(newInstance instanceof RefTestClazz);
    }

    @Test
    public void test_newInstance_with_clazz_with_params() throws ClassNotFoundException {
        String value = "test_newInstance_with_clazz_with_params";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClaszz, value);
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(value, newInstance.getValue());
    }

    @Test
    public void test_newInstance_with_className_with_params() throws ClassNotFoundException {
        String value = "test_newInstance_with_clazz_with_params";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClassName, value);
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(value, newInstance.getValue());
    }

    @Test
    public void test_newInstance_with_clazz_with_params_2() throws ClassNotFoundException {
        String consType = "create from test case";
        String value = "test_newInstance_with_clazz_with_params_2";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClaszz,
                new Class[]{String.class, String.class}, new String[]{consType, value});
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(consType, newInstance.getConstructorType());
        Assert.assertEquals(value, newInstance.getValue());
    }

    @Test
    public void test_newInstance_with_className_with_params_2() throws ClassNotFoundException {
        String consType = "create from test case";
        String value = "test_newInstance_with_clazz_with_params_2";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClassName,
                new Class[]{String.class, String.class}, new String[]{consType, value});
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(consType, newInstance.getConstructorType());
        Assert.assertEquals(value, newInstance.getValue());
    }


    @Test
    public void test_invokeMethod_with_className_with_params_2() {
        String consType = "create from test case";
        String value = "test_newInstance_with_clazz_with_params_2";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClassName,
                new Class[]{String.class, String.class}, new String[]{consType, value});
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(consType, newInstance.getConstructorType());
        Assert.assertEquals(value, newInstance.getValue());
    }


    @Test
    public void test_invokeMethod_with_class_with_params_2() {
        String consType = "create from test case";
        String value = "test_newInstance_with_clazz_with_params_2";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClaszz,
                new Class[]{String.class, String.class}, new String[]{consType, value});
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(consType, newInstance.getConstructorType());
        Assert.assertEquals(value, newInstance.getValue());
    }

    @Test
    public void test_invokeStaticMethod_with_className_no_params() {
        String methodName = "doNoParamsCall";
        Object doNoParamsCall = RefInvoker.invokeStaticMethod(mTestClassName, methodName);
        Assert.assertEquals(methodName, doNoParamsCall);
    }

    @Test
    public void test_invokeStaticMethod_with_className_with_params() {
        String methodName = "setStaticValue";
        RefInvoker.invokeStaticMethod(mTestClassName, methodName, new Class[]{String.class}, new String[]{methodName,});
        Assert.assertEquals(methodName, RefTestClazz.getStaticValue());
    }


    @Test
    public void test_invokeStaticMethod_with_class_no_params() {
        String methodName = "doNoParamsCall";
        Object doNoParamsCall = RefInvoker.invokeStaticMethod(mTestClaszz, methodName);
        Assert.assertEquals(methodName, doNoParamsCall);
    }

    @Test
    public void test_invokeStaticMethod_with_class_with_params() {
        String methodName = "setStaticValue";
        RefInvoker.invokeStaticMethod(mTestClaszz, methodName, new Class[]{String.class}, new String[]{methodName,});
        Assert.assertEquals(methodName, RefTestClazz.getStaticValue());
    }

    @Test
    public void test_getStaticFiledValue_with_class() {
        Object sStaticValue = RefInvoker.getStaticField(mTestClaszz, "sStaticValue");
        Assert.assertEquals(sStaticValue, RefTestClazz.getStaticValue());
    }


    @Test
    public void test_getStaticField_with_className() {
        Object sStaticValue = RefInvoker.getStaticField(mTestClassName, "sStaticValue");
        Assert.assertEquals(RefTestClazz.sStaticValue_DEFAULT_VALUE, sStaticValue);
    }

    @Test
    public void test_setStaticFiledValue_with_className() {
        String newFiledValue = "newFieldValue";
        RefInvoker.setStaticField(mTestClassName, "sStaticValue", newFiledValue);
        Assert.assertEquals(RefTestClazz.getStaticValue(), newFiledValue);
    }

    @Test
    public void test_setStaticFiledValue_with_class() {
        String newFiledValue = "newFieldValue";
        RefInvoker.setStaticField(mTestClaszz, "sStaticValue", newFiledValue);
        Assert.assertEquals(RefTestClazz.getStaticValue(), newFiledValue);
    }

    @Test
    public void test_setFiledValue_with_class() {
        String newFiledValue = "newFieldValue";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClaszz);
        RefInvoker.setField(newInstance, mTestClaszz, "value", newFiledValue);
        Assert.assertEquals(newInstance.getValue(), newFiledValue);
    }

    @Test
    public void test_setFiledValue_with_className() {
        String newFiledValue = "newFieldValue";
        RefTestClazz newInstance = (RefTestClazz) RefInvoker.newInstance(mTestClassName);
        RefInvoker.setField(newInstance, mTestClassName, "value", newFiledValue);
        Assert.assertEquals(newInstance.getValue(), newFiledValue);
    }


}
