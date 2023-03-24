package com.meitu.library.qwechat.page;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import com.meitu.library.qwechat.utils.NodeInfoParseUtil;

/**
 * Created by lh, 2023/3/13
 * 好友信息页面
 */
public class ClientInfoPage implements IPage{
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
        // 点击返回
        AccessibilityNodeInfo l2bInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/l5w", "android.widget.TextView");
        NodeInfoParseUtil.performClick(l2bInfo, mService);
    }

    @Override
    public boolean isCurrentPage(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo l2cInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/l5x", "android.widget.TextView");
        return l2cInfo != null && "客户信息".equals(l2cInfo.getText().toString());
    }
}
