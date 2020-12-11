package com.example.order.developtools.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;

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
    public static void performSwipeDown(AccessibilityService service, AccessibilityService.GestureResultCallback callback) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N || service == null) {
            return;
        }
        Context baseContext = service.getBaseContext();
        DisplayMetrics displayMetrics = baseContext.getResources().getDisplayMetrics();
        final int height = displayMetrics.heightPixels;
        final int top = (int) (height * .25);
        final int bottom = (int) (height * .75);
        final int midX = displayMetrics.widthPixels / 2;
        GestureDescription.Builder gestureBuilder =  new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(midX, top);
        path.lineTo(midX, bottom);
        gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 1000));
        service.dispatchGesture(gestureBuilder.build(), callback, null);
    }

}
