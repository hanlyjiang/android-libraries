package cn.hanlyjiang.apf_library.loader;

import com.github.hanlyjiang.lib.common.utils.RefInvoker;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * 插件 Dex 加载工具类
 *
 * @author hanlyjiang 5/28/21 3:20 PM
 * @version 1.0
 */
public class BaseDexCLassLoaderHookHelper {


    /**
     * 获取一个 pathClassLoader
     *
     * @param classLoader 类加载器
     * @param apkFile     apk 文件
     * @param optDexFile  可选的dex文件
     */
    public static void pathClassLoader(ClassLoader classLoader, File apkFile, File optDexFile) {
        Object pathListObj = RefInvoker.getField(classLoader, DexClassLoader.class.getSuperclass(), "pathList");
    }
}
