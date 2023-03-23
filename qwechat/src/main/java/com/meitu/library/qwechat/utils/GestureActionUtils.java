package com.meitu.library.qwechat.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.util.DisplayMetrics;

/**
 * Created by lh, 2020/12/11
 * 封装手势操作
 */
public class GestureActionUtils {

    /**
     * 执行下拉操作
     * @param service
     * @param callback
     */
    public static void performSwipeDown(AccessibilityService service, int start, int end, AccessibilityService.GestureResultCallback callback) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N || service == null) {
            return;
        }
        Context baseContext = service.getBaseContext();
        DisplayMetrics displayMetrics = baseContext.getResources().getDisplayMetrics();
        final int midX = displayMetrics.widthPixels / 2;
        GestureDescription.Builder gestureBuilder =  new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(midX, start);
        path.lineTo(midX, end);
        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 500));
        service.dispatchGesture(gestureBuilder.build(), callback, null);
    }

    /**
     * 执行左右滑动操作
     * @param service
     * @param callback
     */
    public static void performSwipeX(AccessibilityService service, int start, int end, AccessibilityService.GestureResultCallback callback) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N || service == null) {
            return;
        }
        Context baseContext = service.getBaseContext();
        DisplayMetrics displayMetrics = baseContext.getResources().getDisplayMetrics();
        final int midY = displayMetrics.heightPixels / 2;
        GestureDescription.Builder gestureBuilder =  new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(start, midY);
        path.lineTo(end, midY);
        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 1000));
        service.dispatchGesture(gestureBuilder.build(), callback, null);
    }

    /**
     * 执行点击操作
     * @param service
     * @param x
     * @param y
     * @param callback
     */
    public static void performClick(AccessibilityService service, int x, int y, AccessibilityService.GestureResultCallback callback) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N || service == null) {
            return;
        }
        GestureDescription.Builder gestureBuilder =  new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, y);
        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 1000));
        service.dispatchGesture(gestureBuilder.build(), callback, null);
    }

}
