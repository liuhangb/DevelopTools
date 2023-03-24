package com.meitu.library.qwechat.page;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.meitu.library.qwechat.utils.NodeInfoParseUtil;
import com.meitu.library.qwechat.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh, 2023/3/13
 * 发送好友申请页面
 */
public class SendRequestPage implements IPage {
    private AccessibilityService mService;
    private boolean isEditIconClicked;
    private boolean isContentFilled;
    private boolean isSendReqClicked;
    private boolean isSendReqException;

    @Override
    public void onVisible(AccessibilityService service, IPage lastPage) {
        mService = service;
        if (lastPage instanceof FeatureBiddenPage) {
            isSendReqException = true;
        }
    }

    @Override
    public void onInvisible() {
        mService = null;
        isEditIconClicked = false;
        isSendReqClicked = false;
        isContentFilled = false;
        isSendReqException = false;
    }

    @Override
    public void onHandleEvent(AccessibilityNodeInfo root) {
        if (isSendReqClicked) {
            return;
        } else if (isSendReqException) {
            clickBack(root);
            return;
        }

        if (isContentFilled) {
            sendFriendRequest(root);
        } else {
            updateRequestContent(root);
        }
    }

    @Override
    public boolean isCurrentPage(AccessibilityNodeInfo root) {
        return isSendFriendRequestPage(root);
    }

    /**
     * 是否是发送申请页
     * @return
     */
    private boolean isSendFriendRequestPage(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo e7oInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/e8w", "android.widget.TextView");
        return e7oInfo != null && "发送申请".equals(e7oInfo.getText().toString());
    }

    /**
     * 修改好友请求内容
     * @param root
     */
    private void updateRequestContent(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo b1aInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/b21", "android.widget.EditText");
        if (b1aInfo != null) {
            if (!isContentFilled) {
                isContentFilled = true;
                String content = (String) SharedPreferencesUtils.getInstance(mService.getApplication()).get(SharedPreferencesUtils.KEY_ADD_FRIEND_INFO, "");
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(mService.getApplication(), "添加好友说明为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                NodeInfoParseUtil.editText(b1aInfo, content);
            }
        } else {
            AccessibilityNodeInfo b1cInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/b23", "android.widget.ImageView");
            if (b1cInfo == null || isEditIconClicked) return;
            isEditIconClicked = true;
            NodeInfoParseUtil.performClick(b1cInfo, mService);
        }
    }

    private void sendFriendRequest(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo e7oInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/e8w", "android.widget.TextView");
        if (e7oInfo == null) return;
        isSendReqClicked = true;

        NodeInfoParseUtil.performClick(e7oInfo, mService);
    }

    private void clickBack(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo nodeInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/e8i", "android.widget.ImageView");
        NodeInfoParseUtil.performClick(nodeInfo, mService);
    }
}