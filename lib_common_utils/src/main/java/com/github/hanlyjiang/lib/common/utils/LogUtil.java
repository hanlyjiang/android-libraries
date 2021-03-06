package com.github.hanlyjiang.lib.common.utils;

import android.util.Log;

/**
 * 日志工具类，COPY FROM Android-Plugin-Framework
 */
public class LogUtil {

    private static String TAG = "HJ_AFP";
    private static boolean isEnable = false;

    private static int stackLevel = 4;

    public static void v(Object... msg) {
        printLog(Log.VERBOSE, msg);
    }

    public static void i(Object... msg) {
        printLog(Log.INFO, msg);
    }

    public static void d(Object... msg) {
        printLog(Log.DEBUG, msg);
    }

    public static void w(Object... msg) {
        printLog(Log.WARN, msg);
    }

    public static void e(Object... msg) {
        printLog(Log.ERROR, msg);
    }

    private static void printLog(int level, Object... msg) {
        if (isEnable) {
            StringBuilder str = new StringBuilder();
            if (msg != null) {
                for (Object obj : msg) {
                    str.append("||").append(obj);
                }
                if (str.length() > 1) {
                    str.delete(0, 2);//删除开头的分隔符
                }
            } else {
                str.append("null");
            }
            try {
                StackTraceElement[] sts = Thread.currentThread().getStackTrace();
                StackTraceElement st = null;
                String tag = null;
                if (sts != null && sts.length > stackLevel) {
                    st = sts[stackLevel];
                    if (st != null) {
                        String fileName = st.getFileName();
                        tag = (fileName == null) ? "Unkown" : fileName.replace(".java", "");
                        str.insert(0, "[" + tag + "." + st.getMethodName() + "() line " + st.getLineNumber() + "]\n>>>[")
                                .append("]");
                    }
                }
                while (str.length() > 0) {
                    DEFAULT_LOGHANDLER.publish(TAG, level, str.substring(0, Math.min(2000, str.length())).toString());
                    str.delete(0, 2000);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void printStackTrace() {
        if (isEnable) {
            try {
                StackTraceElement[] sts = Thread.currentThread().getStackTrace();
                for (StackTraceElement stackTraceElement : sts) {
                    DEFAULT_LOGHANDLER.publish("Log_StackTrace", Log.ERROR, stackTraceElement.toString());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void printException(String msg, Throwable e) {
        if (isEnable) {
            DEFAULT_LOGHANDLER.publish("Log_StackTrace", Log.ERROR, msg + '\n' + Log.getStackTraceString(e));
        }
    }

    public interface LogHandler {

        void publish(String tag, int level, String message);

    }

    public static LogHandler DEFAULT_LOGHANDLER = new LogHandler() {
        @Override
        public void publish(String tag, int level, String message) {
            Log.println(level, tag, message);
        }
    };

    public static void setEnable(boolean isLogEnable) {
        isEnable = isLogEnable;
    }

    public static void setLogHandler(LogHandler logHandler) {
        DEFAULT_LOGHANDLER = logHandler;
    }

    /**
     * 设置标签
     *
     * @param tag 标签
     */
    public static void setTag(String tag) {
        TAG = tag;
    }

    /**
     * 设置日志级别，默认为 {@link Log#INFO }
     *
     * @param level 参考{@link Log#INFO } 等
     */
    public static void setStackLevel(int level) {
        stackLevel = level;
    }

}
