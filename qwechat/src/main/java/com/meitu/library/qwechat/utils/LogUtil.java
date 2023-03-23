package com.meitu.library.qwechat.utils;

import android.os.SystemClock;
import android.util.Log;

import com.meitu.library.qwechat.BuildConfig;


/**
 * 日志工具
 * 日志目前只是根据DEBUG 状态做区分打印， 正式版本不打印v和d
 * 计时功能，用于调试，计算代码运行时间
 */
public class LogUtil {

    private static final String TAG = "WeworkTool";
    private static final String TAG_ADD = TAG + "_";

    private static boolean sDEBUG = BuildConfig.DEBUG;
    private static long sLastRecordTime = 0L;

    public static void setDebug(boolean isDebug){
        sDEBUG = isDebug;
    }


    public static void v(String msg) {
        if (sDEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (sDEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    public static void v(String tag, String msg) {
        if (sDEBUG) {
            Log.v(TAG_ADD + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (sDEBUG) {
            Log.d(TAG_ADD + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        Log.i(TAG_ADD + tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(TAG_ADD + tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(TAG_ADD + tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        Log.e(TAG_ADD + tag, msg, e);
    }

    /**
     * 打印当前操作距离上一次进入的时间
     *
     * @param mark 时间打印的标记
     */
    public static void recordTime(String mark) {
        if (sDEBUG) {
            long current = SystemClock.elapsedRealtime();
            Log.i(TAG, mark + " cost:" + (current - sLastRecordTime));
            sLastRecordTime = current;
        }
    }

    private LogUtil() {
        throw new UnsupportedOperationException("Prohibited!");
    }
}
