package cn.hanlyjiang.apf_library.proxy;


import cn.hanlyjiang.apf_library.utils.RefInvoker;

/**
 * Created by cailiming on 16/10/30.
 */

public class HackSingleton {
    private static final String ClassName = "android.util.Singleton";

    private static final String Field_mInstance = "mInstance";

    private Object instance;

    public HackSingleton(Object instance) {
        this.instance = instance;
    }

    public void setInstance(Object object) {
        RefInvoker.setField(instance, ClassName, Field_mInstance, object);
    }
}
