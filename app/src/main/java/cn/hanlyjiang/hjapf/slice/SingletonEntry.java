package cn.hanlyjiang.hjapf.slice;

/**
 * 测试入口类
 *
 * @author hanlyjiang 5/21/21 4:13 PM
 * @version 1.0
 */
public class SingletonEntry implements SingletonObjInterface {

    private static Singleton<SingletonObjInterface> mSingletonInstance = new Singleton<SingletonObjInterface>() {
        @Override
        protected SingletonObjInterface create() {
            return new SingletonEntry();
        }
    };

    public static SingletonObjInterface getService() {
        return mSingletonInstance.get();
    }

    public static String invokeShow() {
        return getService().show();
    }

    @Override
    public String show() {
        return "show me the code";
    }
}
