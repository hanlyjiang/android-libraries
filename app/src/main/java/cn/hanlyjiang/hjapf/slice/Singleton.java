package cn.hanlyjiang.hjapf.slice;

/**
 * 测试Singleton的反射
 *
 * @author hanlyjiang 5/21/21 4:09 PM
 * @version 1.0
 * <p>
 * Singleton helper class for lazily initialization.
 * <p>
 * Modeled after frameworks/base/include/utils/Singleton.h
 * @hide
 */
public abstract class Singleton<T> {

    public Singleton() {
    }

    private T mInstance;

    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}
