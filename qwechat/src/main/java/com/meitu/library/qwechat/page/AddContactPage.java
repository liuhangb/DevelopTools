package com.meitu.library.qwechat.page;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import com.meitu.library.qwechat.utils.NodeInfoParseUtil;

/**
 * Created by lh, 2023/3/13
 */
public class AddContactPage implements IPage {
    private AccessibilityService mService;
    private boolean isAddContactBtnClicked;
    private boolean isFinished;
    private boolean isSendPageFinished;

    @Override
    public void onVisible(AccessibilityService service, IPage lastPage) {
        mService = service;
        if (lastPage instanceof SendRequestPage) {
            isSendPageFinished = true;
        } else if (lastPage instanceof FriendListPage) {
            isAddContactBtnClicked = false;
            isSendPageFinished = false;
        }
        isFinished = false;
    }

    @Override
    public void onInvisible() {
        mService = null;
    }

    @Override
    public void onHandleEvent(AccessibilityNodeInfo root) {
        if (isFinished) return;
        if (isAddContactBtnClicked && isSendPageFinished) {
            clickBack(root);
        } else {
            addContact(root);
        }
    }

    @Override
    public boolean isCurrentPage(AccessibilityNodeInfo root) {
        return isUserInfo(root);
    }

    /**
     * 是否是个人信息页
     * @return
     */
    private boolean isUserInfo(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo l2cInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/l5x", "android.widget.TextView");
        AccessibilityNodeInfo b2bInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/b32", "android.widget.TextView");
        return l2cInfo != null && "个人信息".equals(l2cInfo.getText().toString()) && b2bInfo != null && "添加为联系人".equals(b2bInfo.getText().toString());
    }

    /**
     * 点击添加联系人
     */
    private void addContact(AccessibilityNodeInfo root) {
        if (!isUserInfo(root) || !isWxUserInfo(root) || isAddContactBtnClicked) return;
        AccessibilityNodeInfo b2bInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/b32", "android.widget.TextView");
        if (b2bInfo == null) return;
        isAddContactBtnClicked = true;
//        NodeInfoParseUtil.performClick(b2bInfo, mService);
    }

    /**
     * 个人信息页判断是否是外部用户（微信）
     * @param root
     * @return
     */
    private boolean isWxUserInfo(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo kdxInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/kh7", "android.widget.TextView");
        return kdxInfo != null && "微信".equals(kdxInfo.getText().toString());
    }

    private void clickBack(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo l2bInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/l5w", "android.widget.TextView");
        if (l2bInfo == null) return;
        isFinished = true;
        NodeInfoParseUtil.performClick(l2bInfo, mService);
    }
}
