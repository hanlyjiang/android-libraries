package com.github.hanlyjiang.lib.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

/**
 * APP 信息获取工具类
 *
 * @author hanlyjiang  5/27/21
 * @version 1.0
 */
public class AppInfoUtils {

    /**
     * 获取应用程序名称
     *
     * @param context 上下文
     * @return APP名称或 null
     */
    @Nullable
    public static String getAppName(Context context) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageInfo(context).applicationInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null) {
            return null;
        }
        int labelRes = applicationInfo.labelRes;
        if (labelRes == 0) {
            return null;
        }
        return context.getResources().getString(labelRes);
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context 上下文对象
     * @return 当前应用的版本名称 or null
     */
    @Nullable
    public static String getVersionName(Context context) {
        try {
            return getPackageInfo(context).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取应用程序版号
     *
     * @param context 上下文对象
     * @return 当前应用的版本号 or 0
     */
    public static int getVersionCode(Context context) {
        try {
            return getPackageInfo(context).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取应用程序包名
     *
     * @param context 上下文
     * @return 当前应用的包名 or null
     */
    public static String getPackageName(Context context) {
//            return getPackageInfo(context).packageName;
        return context.getPackageName();
    }


    /**
     * 获取当前应用的图标
     *
     * @param context 上下文
     * @return 应用图标 or null
     */
    @Nullable
    public static Drawable getAppIcon(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return context.getPackageManager().getApplicationIcon(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


    private static PackageInfo getPackageInfo(Context context)
            throws PackageManager.NameNotFoundException {
        return context.getPackageManager()
                .getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
    }


}