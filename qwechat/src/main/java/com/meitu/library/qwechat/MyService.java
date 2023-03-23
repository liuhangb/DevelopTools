package com.meitu.library.qwechat;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.meitu.library.qwechat.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh, 2020/12/8
 */
public class MyService extends AccessibilityService {
    private final String TAG = "MyService";
    private final List<BaseEventProcessor> mEventProcessor = new ArrayList<>();

    private void addEventProcessor(BaseEventProcessor eventProcessor) {
        if (eventProcessor != null && !mEventProcessor.contains(eventProcessor)) {
            mEventProcessor.add(eventProcessor);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 事件页面节点信息不为空
//        LogUtil.d(TAG, "eventType:" + event.getEventType() +",package name: " + event.getPackageName());
        for (int i = 0; i < mEventProcessor.size(); i++) {
            BaseEventProcessor baseEventProcessor = mEventProcessor.get(i);
            CharSequence packageName = event.getPackageName();
            if (baseEventProcessor.isEnable() && ((!TextUtils.isEmpty(packageName) && packageName.equals(baseEventProcessor.desiredPackageName()))
                    || baseEventProcessor.isNeedAllEvent())) {
                baseEventProcessor.onAccessibilityEvent(event);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onInterrupt() {

        LogUtil.d(TAG, "onInterrupt");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
        WeworkEventProcessor weworkEventProcessor = new WeworkEventProcessor(this);
        addEventProcessor(weworkEventProcessor);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }
}
