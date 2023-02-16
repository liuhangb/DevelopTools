package com.example.order.developtools;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.order.developtools.config.TaoBaoConfig;
import com.example.order.developtools.processor.BaseEventProcessor;
import com.example.order.developtools.processor.DouYinEventProcessor;
import com.example.order.developtools.processor.KuShouEventProcessor;
import com.example.order.developtools.processor.MeituanEvaluationProcessor;
import com.example.order.developtools.processor.SkipAdProcessor;
import com.example.order.developtools.processor.TaoBaoEventProcessor;

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
        Log.d(TAG, "eventType:" + event.getEventType() +",package name: " + event.getPackageName());
        for (int i = 0; i < mEventProcessor.size(); i++) {
            BaseEventProcessor baseEventProcessor = mEventProcessor.get(i);
            CharSequence packageName = event.getPackageName();
            if (baseEventProcessor.isEnable() && ((!TextUtils.isEmpty(packageName) && packageName.equals(baseEventProcessor.desiredPackageName()))
                    || baseEventProcessor.isNeedAllEvent())) {
                baseEventProcessor.onAccessibilityEvent(event);
            }
        }

//        if (rootInActiveWindow != null) {
//            rootInActiveWindow.recycle();
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onInterrupt() {

        Log.d(TAG, "onInterrupt");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        TaoBaoEventProcessor taoBaoEventProcessor = new TaoBaoEventProcessor(this);
        TaoBaoConfig.Companion.register(taoBaoEventProcessor);
        addEventProcessor(taoBaoEventProcessor);
        MeituanEvaluationProcessor meituanEvaluationProcessor = new MeituanEvaluationProcessor(this);
        addEventProcessor(meituanEvaluationProcessor);
        addEventProcessor(new KuShouEventProcessor(this));
        addEventProcessor(new DouYinEventProcessor(this));
        addEventProcessor(new SkipAdProcessor(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TaoBaoConfig.Companion.removeAll();
    }
}
