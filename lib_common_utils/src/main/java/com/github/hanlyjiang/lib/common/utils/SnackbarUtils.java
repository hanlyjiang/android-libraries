package com.github.hanlyjiang.lib.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

/**
 * Snackbar 工具
 *
 * @author hanlyjiang 5/26/21 10:27 PM
 * @version 1.0
 */
public class SnackbarUtils {

    /**
     * 以 ${@link Snackbar#LENGTH_SHORT} 的时长显示snackbar，内容为 msgResId 指定的字符串
     *
     * @param activity Activity
     * @param msgResId String id
     */
    public static void showSnackBar(Activity activity, int msgResId) {
        if (activity == null) return;
        View viewById = activity.findViewById(android.R.id.content);
        Snackbar.make(viewById, activity.getResources().getString(msgResId), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 以 ${@link Snackbar#LENGTH_SHORT} 的时长显示snackbar，内容为 msg
     *
     * @param activityContext Context
     * @param msg             消息内容
     */
    public static void showSnackBar(Context activityContext, String msg) {
        if (activityContext == null) return;
        if (!(activityContext instanceof Activity)) return;
        View viewById = ((Activity) activityContext).findViewById(android.R.id.content);
        Snackbar.make(viewById, msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 以 ${@link Snackbar#LENGTH_SHORT} 的时长显示snackbar，内容为 msgResId 指定的字符串
     *
     * @param activityContext Context
     * @param msgResId        String id
     */
    public static void showSnackBar(Context activityContext, int msgResId) {
        if (activityContext == null) return;
        if (!(activityContext instanceof Activity)) return;
        View viewById = ((Activity) activityContext).findViewById(android.R.id.content);
        Snackbar.make(viewById, activityContext.getResources().getString(msgResId), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 以 ${@link Snackbar#LENGTH_SHORT} 的时长显示snackbar，内容为 msg
     *
     * @param activity Activity
     * @param msg      String id
     */
    public static void showSnackBar(Activity activity, String msg) {
        if (activity == null) return;
        View viewById = activity.findViewById(android.R.id.content);
        Snackbar.make(viewById, msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 以 ${@link Snackbar#LENGTH_SHORT} 的时长显示snackbar，内容为 msg
     *
     * @param fragment Fragment
     * @param msg      String id
     */
    public static void showSnackBar(Fragment fragment, String msg) {
        if (fragment == null || fragment.getView() == null) return;
        Snackbar.make(fragment.getView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 以 ${@link Snackbar#LENGTH_SHORT}  的时长显示snackbar，内容为 msg
     *
     * @param fragment  fragment  Fragment
     * @param msgStrRes String id
     */
    public static void showSnackBar(Fragment fragment, int msgStrRes) {
        if (fragment == null || fragment.getContext() == null || fragment.getView() == null) return;
        Snackbar.make(fragment.getView(), fragment.getContext().getText(msgStrRes), Snackbar.LENGTH_SHORT).show();
    }
}
