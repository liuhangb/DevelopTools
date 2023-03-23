package com.meitu.library.qwechat;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.meitu.library.qwechat.utils.GestureActionUtils;
import com.meitu.library.qwechat.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lh, 2020/12/9
 */
public abstract class BaseEventProcessor {
    protected static final String TAG = BaseEventProcessor.class.getSimpleName();
    protected Context mContext;
    protected AccessibilityService mService;
    private long mLastClickTime;

    public BaseEventProcessor(@NonNull AccessibilityService service) {
        mService = service;
        mContext = service.getBaseContext();
    }

    public abstract boolean isEnable();

    /**
     * 只接受指定包名的事件
     * @return
     */
    public abstract String desiredPackageName();

    /**
     * 是否需要监听所有app的页面变化事件
     * @return true时 {@link BaseEventProcessor#desiredPackageName}不起作用
     */
    public boolean isNeedAllEvent() {
        return false;
    }

    public abstract void onAccessibilityEvent(AccessibilityEvent event);


    protected void performClick(AccessibilityNodeInfo child) {
        performClick(child, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void performClick(AccessibilityNodeInfo child, boolean isCheckTime) {
        if (child == null) {
            LogUtil.d("performClick failed, node is null ");
            return;
        } else if (isCheckTime && System.currentTimeMillis() - mLastClickTime <= 3000) {
            LogUtil.w("performClick too frequently, so rejected");
            return;
        }

        mLastClickTime = System.currentTimeMillis();
        LogUtil.d("performClick: " + child);
        Toast.makeText(mService.getBaseContext(), "点击了: " + (child.getText() != null ? child.getText().toString() : ""), Toast.LENGTH_LONG).show();
        Rect rect = new Rect();
        child.getBoundsInScreen(rect);
        GestureActionUtils.performClick(mService, rect.centerX(), rect.centerY(), new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                LogUtil.d("performClick onCompleted");
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                LogUtil.d("performClick onCancelled");
            }
        });
    }
}
