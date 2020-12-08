package com.example.order.developtools;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lh, 2020/12/8
 */
public class MyService extends AccessibilityService {
    private final String TAG = "MyService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 事件页面节点信息不为空
        Log.d(TAG, "the source:" + event.getSource());
        if (event.getSource() != null) {
            // 判断事件页面所在的包名，这里是自己
            if (event.getPackageName().equals(getApplicationContext().getPackageName())) {
                click(event, "Hello World", TextView.class.getName());
            }
        } else {
            Log.d(TAG, "the source = null");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 模拟点击
     * @param event 事件
     * @param text 按钮文字
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */
    private void click(AccessibilityEvent event, String text, String widgetType) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = event.getSource().findAccessibilityNodeInfosByText(text);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
//                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            Toast.makeText(getBaseContext(), "辅助功能已经检测到hello world", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

        Log.d(TAG, "onInterrupt");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
}
