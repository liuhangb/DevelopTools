package com.example.order.developtools;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lh, 2020/12/9
 */
public class TaoBaoEventProcessor extends BaseEventProcessor{

    private String[] SAVE_EVENT_ID = {"com.taobao.taobao:id/btn_back", "com.taobao.taobao:id/button_cart_charge"};
    private Map<String, AccessibilityNodeInfo> mEventMap =  new HashMap<>(10);
    public TaoBaoEventProcessor(@NonNull Context context) {
        super(context);
    public TaoBaoEventProcessor(@NonNull AccessibilityService service) {
        super(service);
    }

    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, AccessibilityNodeInfo rootNodeInfo) {

        if (event.getSource() != null) {
            // 判断事件页面所在的包名，这里是自己
            if (event.getPackageName() != null && event.getPackageName().equals("com.taobao.taobao")) {

                clickById(rootNodeInfo, "com.taobao.taobao:id/button_cart_charge", TextView.class.getName());
                boolean isContainInvalidDesc = checkContentByText(rootNodeInfo, "失效宝贝", TextView.class.getName());
                if (isContainInvalidDesc) {
                    clickById(rootNodeInfo, "com.taobao.taobao:id/btn_back", TextView.class.getName());
                } else {
                    clickByCustomText(rootNodeInfo, "提交订单", TextView.class.getName());
                }

//                boolean isContainPayNow = checkContentByText(event, "立即付款", TextView.class.getName());
//                Log.d(TAG, "立即付款 :" + isContainPayNow);
            }
        } else {
            Log.d(TAG, "the source = null");
        }

//        saveEvent(rootNodeInfo);
    }

    private void saveEvent(AccessibilityNodeInfo root) {
        if (!mEventMap.containsValue(root)) {
            for (int i = 0; i < SAVE_EVENT_ID.length; i++) {
                boolean isExit = checkContentById(root, SAVE_EVENT_ID[i], TextView.class.getName());
                if (isExit) {
                    mEventMap.put(SAVE_EVENT_ID[i], root);
                }
            }
        }
    }


}
