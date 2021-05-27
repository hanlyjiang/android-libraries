package com.github.hanlyjiang.lib.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;


/**
 * 任务调度的工具类，可以在主线程和后台线程里安排任务
 * <br>
 *
 * @author hanlyjiang 2016/12/5 10:27
 */
public class RunnableUtils {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);
    private static final Handler sUIHandler = new Handler(Looper.getMainLooper());

    /**
     * 在UI线程中执行一个Runnable
     *
     * @param task 要执行的任务
     */
    public static void postUI(Runnable task) {
//        AndroidSchedulers.mainThread().scheduleDirect(task);
        postUIWork(task);  // 不使用RXJava 的实现
    }

    /**
     * 在UI线程中执行一个Runnable
     *
     * @param task  要执行的任务
     * @param delay 延迟（MILLISECONDS） 1000ms =  1s
     */
    public static void postUI(Runnable task, int delay) {
//        AndroidSchedulers.mainThread().scheduleDirect(task, delay, TimeUnit.MILLISECONDS);
        postUIWork(task, delay);    //不使用RXJava 的实现
    }

    /**
     * 在后台 线程中执行一个 Callable
     *
     * @param callableTask 要执行的任务
     * @return Future 对象
     */
    public static Future<?> postBG(Callable<?> callableTask) {
        return EXECUTOR.submit(callableTask);
    }

    /**
     * 在IO 线程中执行一个Runnable
     *
     * @param task 要执行的任务
     */
    public static void postBG(Runnable task) {
//        Schedulers.from(EXECUTOR).scheduleDirect(task);
        EXECUTOR.execute(task);
    }


    private static void postUIWork(Runnable runnable, int delay) {
        Message msg = sUIHandler.obtainMessage();
        msg.obj = runnable;
        sUIHandler.sendMessageDelayed(msg, delay);
    }

    private static void postUIWork(Runnable runnable) {
        sUIHandler.post(runnable);
    }

}
