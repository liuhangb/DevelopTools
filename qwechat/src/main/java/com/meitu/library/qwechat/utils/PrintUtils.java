package com.meitu.library.qwechat.utils;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by lh, 2020/12/11
 */
public class PrintUtils {
    public static String printNodeInfo(AccessibilityNodeInfo nodeInfo){
        if (nodeInfo == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nodeInfo.getClassName());
        stringBuilder.append("{");
        stringBuilder.append("text:" + nodeInfo.getText());
        stringBuilder.append("[");
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = nodeInfo.getChild(i);
            String s = printNodeInfo(child);
            stringBuilder.append(s);
        }
        stringBuilder.append("]");
        stringBuilder.append("},");

        return stringBuilder.toString();
    }
}
