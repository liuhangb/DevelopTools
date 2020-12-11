package com.example.order.developtools;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by lh, 2020/12/9
 */
public abstract class BaseEventProcessor {
    protected static final String TAG = BaseEventProcessor.class.getSimpleName();
    protected static Context mContext;
    protected AccessibilityService mService;

    public BaseEventProcessor(@NonNull AccessibilityService service) {
        mService = service;
        mContext = service.getBaseContext();
    }

    public abstract boolean isEnable();

    public abstract void onAccessibilityEvent(AccessibilityEvent event);

    /**
     * 模拟点击
     *
     * @param event      事件
     * @param text       按钮文字
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */
    protected void clickByText(AccessibilityEvent event, String text, String widgetType) {
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
                            Toast.makeText(mContext, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    protected static void clickById(AccessibilityNodeInfo root, String id, String widgetType) {
        // 事件页面节点信息不为空
        if (root != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = root.findAccessibilityNodeInfosByViewId(id);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            Toast.makeText(mContext, "辅助功能已经检测到: "+ id, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    /**
     * 可以自定义action
     * @param root
     * @param id
     * @param widgetType
     * @param action
     */
    protected static void clickById(AccessibilityNodeInfo root, String id, String widgetType, int action) {
        // 事件页面节点信息不为空
        if (root != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = root.findAccessibilityNodeInfosByViewId(id);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            node.performAction(action);
                            Toast.makeText(mContext, "辅助功能已经检测到: "+ id, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }


    /**
     * 检查对应的内容是否存在
     * @param rootInActiveWindow
     * @param text
     * @param widgetType
     * @return
     */
    protected boolean checkContentByText(AccessibilityNodeInfo rootInActiveWindow, String text, String widgetType) {
        // 事件页面节点信息不为空
        if (rootInActiveWindow != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            Toast.makeText(mContext, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查对应的内容是否存在
     * @param rootNodeInfo
     * @param id
     * @param widgetType
     * @return
     */
    protected boolean checkContentById(AccessibilityNodeInfo rootNodeInfo, String id, String widgetType) {
        // 事件页面节点信息不为空
        if (rootNodeInfo != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = rootNodeInfo.findAccessibilityNodeInfosByViewId(id);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected void clickByCustomText(AccessibilityNodeInfo root, String text, String widgetType) {
        // 事件页面节点信息不为空
        if (root != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            AccessibilityNodeInfo source = root;
            List<AccessibilityNodeInfo> stop_nodes = source.findAccessibilityNodeInfosByText(text);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            Toast.makeText(mContext, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            root.recycle();
        }

    }
}
