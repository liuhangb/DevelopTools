package com.meitu.library.qwechat.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh, 2023/3/13
 */
public class NodeInfoParseUtil {
    /**
     * 模拟点击
     *
     * @param event      事件
     * @param text       按钮文字
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */
    protected void clickByText(AccessibilityEvent event, String text, String widgetType, Context context) {
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
//                            Toast.makeText(context, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    protected boolean clickById(AccessibilityNodeInfo root, String id, String widgetType, Context context) {
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
//                            Toast.makeText(context, "辅助功能已经检测到: "+ id, Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * 可以自定义action
     * @param root
     * @param id
     * @param widgetType
     * @param action
     */
    protected void clickById(AccessibilityNodeInfo root, String id, String widgetType, int action, Context context) {
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
//                            Toast.makeText(context, "辅助功能已经检测到: "+ id, Toast.LENGTH_SHORT).show();
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
    protected boolean checkContentByText(AccessibilityNodeInfo rootInActiveWindow, String text, String widgetType, Context context) {
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
//                            Toast.makeText(context, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 模糊匹配检查对应的内容是否存在
     * @param rootInActiveWindow
     * @param text
     * @param widgetType
     * @return
     */
    protected boolean checkContentByFuzzyMatch(AccessibilityNodeInfo rootInActiveWindow, String text, String widgetType, Context context) {
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
//                            Toast.makeText(context, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
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

    public static boolean clickByCustomText(AccessibilityNodeInfo root, String text, String widgetType, Context context) {
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
                        if (node.isEnabled() && node.isClickable()) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            Toast.makeText(context, "辅助功能已经检测到: "+ text, Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
            root.recycle();
        }

        return false;
    }

    /**
     * 通过同一层级node辅助，找到目标node
     * @param rootInActiveWindow
     * @param flatNodeText
     * @param className
     * @return
     */
    public static AccessibilityNodeInfo findDestNodeByFlatNode(AccessibilityNodeInfo rootInActiveWindow,
                                                           String flatNodeText, String className) {
        if (rootInActiveWindow == null || TextUtils.isEmpty(flatNodeText) || TextUtils.isEmpty(className)) {
            return null;
        }

        boolean isExit = false;
        for (int i = 0; i < rootInActiveWindow.getChildCount(); i++) {
            AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
            if (child == null) {
                continue;
            }

            if (child.getText() != null && flatNodeText.equals(child.getText().toString()) ||  child.getContentDescription() != null && flatNodeText.equals(child.getContentDescription().toString())) {
                isExit = true;
                break;
            } else {
                AccessibilityNodeInfo destNodeByFlatNode = findDestNodeByFlatNode(child, flatNodeText, className);
                if (destNodeByFlatNode != null) {
                    return destNodeByFlatNode;
                }
            }
        }

        if (!isExit) {
            return null;
        }

        for (int i = 0; i < rootInActiveWindow.getChildCount(); i++) {
            AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
            if (child == null) {
                continue;
            }
            if (className.equals(child.getClassName())) {
                return child;
            }
        }

        return null;

    }

    /**
     * 模糊匹配，找到目标node
     * @param rootInActiveWindow
     * @param flatNodeText
     * @param className
     * @return
     */
    public static AccessibilityNodeInfo findDestNodeByFuzzyMatch(AccessibilityNodeInfo rootInActiveWindow,
                                                             String flatNodeText, String className) {
        if (rootInActiveWindow == null || TextUtils.isEmpty(flatNodeText) || TextUtils.isEmpty(className)) {
            return null;
        }

//        if ((rootInActiveWindow.getText() != null && rootInActiveWindow.getText().toString().contains(flatNodeText)) ||  (rootInActiveWindow.getContentDescription() != null && rootInActiveWindow.getContentDescription().toString().contains(flatNodeText))) {
//            LogUtil.d("find child: " + rootInActiveWindow);
//            return rootInActiveWindow;
//        }

        for (int i = 0; i < rootInActiveWindow.getChildCount(); i++) {
            AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
            if (child == null) {
                continue;
            }

            LogUtil.d("child: " + child);

            if (child.getText() != null) {
                LogUtil.d("child.getText(): " + child.getText() != null ? child.getText().toString() : "");
            }

            if (child.getContentDescription() != null) {
                LogUtil.d("child.getContentDescription(): " + child.getContentDescription() != null ? child.getContentDescription().toString() : "");
            }

            if (child.getText() != null && child.getText().toString().equals("跳过广告")) {
                LogUtil.d("");
            }
            if (((child.getText() != null && child.getText().toString().contains(flatNodeText))
                    ||  (child.getContentDescription() != null &&
                    child.getContentDescription().toString().contains(flatNodeText))) && child.getClassName().equals(className)) {
                LogUtil.d("find child: " + child);
//                Toast.makeText(mService.getBaseContext(), "找到了跳过按钮", Toast.LENGTH_LONG).show();
                return child;
            } else {
                AccessibilityNodeInfo destNodeByFlatNode = findDestNodeByFuzzyMatch(child, flatNodeText, className);
                if (destNodeByFlatNode != null) {
                    return destNodeByFlatNode;
                }
            }
        }

        return null;

    }

    public static List<AccessibilityNodeInfo> findDestNodesByFlatNode(AccessibilityNodeInfo rootInActiveWindow,
                                                                  String flatNodeText, String className) {
        List<AccessibilityNodeInfo> accessibilityNodeInfos = new ArrayList<>();
        if (rootInActiveWindow == null || TextUtils.isEmpty(flatNodeText) || TextUtils.isEmpty(className)) {
            return accessibilityNodeInfos;
        }

        boolean isExit = false;
        for (int i = 0; i < rootInActiveWindow.getChildCount(); i++) {
            AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
            if (child == null) {
                continue;
            }

            LogUtil.d("findDestNodesByFlatNode", "text:" + child.getText());
            if ((child.getText() != null && flatNodeText.equals(child.getText().toString())) || (child.getContentDescription() != null && flatNodeText.equals(child.getContentDescription().toString()))) {
                isExit = true;
                break;
            } else {
                List<AccessibilityNodeInfo> destNodeByFlatNode = findDestNodesByFlatNode(child, flatNodeText, className);
                if (destNodeByFlatNode != null && destNodeByFlatNode.size() > 0) {
                    return destNodeByFlatNode;
                }
            }
        }

        if (!isExit) {
            return accessibilityNodeInfos;
        }

        for (int i = 0; i < rootInActiveWindow.getChildCount(); i++) {
            AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
            if (child == null) {
                continue;
            }
            if (className.equals(child.getClassName())) {
                accessibilityNodeInfos.add(child);
            }
        }

        return accessibilityNodeInfos;

    }

    public static AccessibilityNodeInfo findAccessibilityNodeInfosByViewId(AccessibilityNodeInfo rootNodeInfo, String id, String widgetType) {
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
                        return node;
                    }
                }
            }
        }

        return null;
    }

    public static AccessibilityNodeInfo findAccessibilityNodeInfosByText(AccessibilityNodeInfo rootNodeInfo, String text, String widgetType) {
        // 事件页面节点信息不为空
        if (rootNodeInfo != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = rootNodeInfo.findAccessibilityNodeInfosByText(text);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        return node;
                    }
                }
            }
        }

        return null;
    }


    /**
     * 直接使用nodeInfo.setText修改内容会导致崩溃
     *  java.lang.IllegalStateException: Cannot perform this action on a sealed instance.
     * @param editInfo
     * @param content
     */
    public static void editText(AccessibilityNodeInfo editInfo, String content) {
        if (editInfo == null) return;
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, content);
        editInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    public static void performClick(AccessibilityNodeInfo child, AccessibilityService service) {
        if (child == null) {
            LogUtil.d("performClick failed, node is null ");
            return;
        }

        LogUtil.d("performClick: " + child);
//        Toast.makeText(service.getBaseContext(), "点击了: " + (child.getText() != null ? child.getText().toString() : ""), Toast.LENGTH_LONG).show();
        Rect rect = new Rect();
        child.getBoundsInScreen(rect);
        GestureActionUtils.performClick(service, rect.centerX(), rect.centerY(), new AccessibilityService.GestureResultCallback() {
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

    public static void performClick(AccessibilityNodeInfo child, AccessibilityService service, AccessibilityService.GestureResultCallback callback) {
        if (child == null) {
            LogUtil.d("performClick failed, node is null ");
            return;
        }

        LogUtil.d("performClick: " + child);
//        Toast.makeText(service.getBaseContext(), "点击了: " + (child.getText() != null ? child.getText().toString() : ""), Toast.LENGTH_LONG).show();
        Rect rect = new Rect();
        child.getBoundsInScreen(rect);
        GestureActionUtils.performClick(service, rect.centerX(), rect.centerY(), callback);
    }
}
