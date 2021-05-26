package com.github.hanlyjiang.lib.common.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;


/**
 * Toast工具
 *
 * @author hanlyjiang 5/26/21 9:31 P
 * @version 1.0
 */
public class ToastUtils {

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param msg     消息
     */
    public static void showToast(Context context, String msg) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param resId   消息字符串资源id
     */
    public static void showToast(Context context, @StringRes int resId) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }
}
