package com.meitu.library.qwechat.page;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import com.meitu.library.qwechat.utils.NodeInfoParseUtil;

/**
 * Created by lh, 2023/3/23
 * 限制加好友弹窗
 */
public class FeatureBiddenPage extends AccessibilityService.GestureResultCallback implements IPage{
    private AccessibilityService mService;
    @Override
    public void onVisible(AccessibilityService service, IPage lastPage) {
        mService = service;
    }

    @Override
    public void onInvisible() {
        mService = null;
    }

    @Override
    public void onHandleEvent(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo nodeInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/cjw", "android.widget.TextView");
        NodeInfoParseUtil.performClick(nodeInfo, mService);
    }

    @Override
    public boolean isCurrentPage(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo nodeInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByText(root, "当前功能暂时被限制，请稍后再试", "android.widget.TextView");
        return nodeInfo != null;
    }
}
