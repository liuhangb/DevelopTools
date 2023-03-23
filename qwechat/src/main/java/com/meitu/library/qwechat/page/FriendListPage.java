package com.meitu.library.qwechat.page;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meitu.library.qwechat.R;
import com.meitu.library.qwechat.utils.GestureActionUtils;
import com.meitu.library.qwechat.utils.LogUtil;
import com.meitu.library.qwechat.utils.NodeInfoParseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh, 2023/3/13
 */
public class FriendListPage extends AccessibilityService.GestureResultCallback implements IPage{
    private List<String> mOldUserList= new ArrayList<>();
    private AccessibilityService mService;
    private long mLastClickTime = 0;
    private boolean isFindTargetUser;
    private boolean isClicking;

    @Override
    public void onVisible(AccessibilityService service, IPage lastPage) {
        mService = service;
    }

    @Override
    public void onInvisible() {
        mService = null;
        isFindTargetUser = false;
    }

    @Override
    public void onHandleEvent(AccessibilityNodeInfo root) {
        if (!isEnableProcess()) return;
        boolean isFind = findTargetUserAndClick(root);
//        boolean isFind =false;
        if (!isFind) {
            slideUp();
        }
    }

    @Override
    public boolean isCurrentPage(AccessibilityNodeInfo root) {
        return isContactList(root);
    }

    @Override
    public void onCancelled(GestureDescription gestureDescription) {
        super.onCancelled(gestureDescription);
        isClicking = false;
        LogUtil.d("click onCancelled");
    }

    @Override
    public void onCompleted(GestureDescription gestureDescription) {
        super.onCompleted(gestureDescription);
        isClicking = false;
        LogUtil.d("click onCompleted");
    }

    private boolean isEnableProcess() {
        return !isClicking;
    }

    /**
     * 是否是群成员列表
     * @param root
     * @return
     */
    private boolean isContactList(AccessibilityNodeInfo root) {
//        AccessibilityNodeInfo qcyNodeInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByText(root, "群成员", "android.widget.TextView");
//        AccessibilityNodeInfo addNodeInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByText(root, "添加", "android.widget.TextView");
        AccessibilityNodeInfo l2cInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/l5x", "android.widget.TextView");
        return l2cInfo != null && l2cInfo.getText().toString().contains("群成员");
//        return qcyNodeInfo != null && addNodeInfo != null;
    }


    private boolean findTargetUserAndClick(@NonNull AccessibilityNodeInfo rootInActiveWindow) {
        AccessibilityNodeInfo listViewInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(rootInActiveWindow, "com.tencent.wework:id/gpr", "android.widget.ListView");
        if (listViewInfo == null) return false;
        LogUtil.d("找到联系人列表");
        int childCount = listViewInfo.getChildCount();
        LogUtil.d("childCount:"+childCount);

        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = listViewInfo.getChild(i);
            if (isTargetUser(child) && !isOldUser(child)) {
                AccessibilityNodeInfo lnxInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(child, "com.tencent.wework:id/ls0", "android.view.ViewGroup");
                AccessibilityNodeInfo targetInfo = lnxInfo.getChild(0);
//                AccessibilityNodeInfo targetInfo = child;
                if (!isValidScreenRect(targetInfo, rootInActiveWindow) || isHiddenByHeadHint(rootInActiveWindow, targetInfo)) {
                    LogUtil.w("isValidScreenRect: false");
                    continue;
                }
                updateOldUserList(child);
                isFindTargetUser = true;
                mLastClickTime = System.currentTimeMillis();
                isClicking = true;
                NodeInfoParseUtil.performClick(targetInfo, mService, this);
                return true;
            }
        }

        return false;
    }

    /**
     * 视图是否被"共80人, 含79位联系人"提示遮挡
     * @return
     */
    private boolean isHiddenByHeadHint(AccessibilityNodeInfo parentInfo, AccessibilityNodeInfo root) {
        AccessibilityNodeInfo go0Info = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(parentInfo, "com.tencent.wework:id/gq_", "android.widget.TextView");
        if (go0Info == null || root == null) return false;
        Rect hintRect = new Rect();
        go0Info.getBoundsInScreen(hintRect);
        Rect rootRect = new Rect();
        root.getBoundsInScreen(rootRect);
        LogUtil.d("isHiddenByHeadHint:"+ (rootRect.top < hintRect.bottom));
        return rootRect.top < hintRect.bottom && rootRect.bottom - rootRect.top <= 20;
    }

    /**
     * 有些AccessibilityNodeInfo对应的布局可能是超过屏幕尺寸不可见, 这时候就无法点击, 需要过滤
     * 有如下特征: boundsInScreen: Rect(205, 2658 - 311, 2640)
     */
    private boolean isValidScreenRect(AccessibilityNodeInfo root, AccessibilityNodeInfo parentInfo) {
        if (root == null) return false;
        Rect rect = new Rect();
        root.getBoundsInScreen(rect);
        LogUtil.d("isValidScreenRect:"+ rect +",centerX:" + rect.centerX() + ",centerY:" + rect.centerY());
        Rect rect1 = new Rect();
        parentInfo.getBoundsInScreen(rect1);
        LogUtil.d("parentInfo:"+ rect1);
        return rect.bottom > rect.top && rect.right > rect.left && rect.bottom - rect.top > 20;
    }

    private void updateOldUserList(AccessibilityNodeInfo root) {
        String userName = getUserName(root);
        if (TextUtils.isEmpty(userName)) return;
        mOldUserList.add(userName);
    }

    private String getUserName(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo lnxInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/ls0", "android.view.ViewGroup");
        if (lnxInfo == null || lnxInfo.getChildCount() <= 0) return null;
        return lnxInfo.getChild(0).getText().toString();
    }

    /**
     * 是否是添加过的旧用户
     * @param root
     * @return
     */
    private boolean isOldUser(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo lnxInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(root, "com.tencent.wework:id/ls0", "android.view.ViewGroup");
        if (lnxInfo == null || lnxInfo.getChildCount() <= 0) return true;
        String text = lnxInfo.getChild(0).getText().toString();
        return mOldUserList.contains(text);
    }

    /**
     * 是否是目标用户
     * @param child
     * @return
     */
    private boolean isTargetUser(AccessibilityNodeInfo child) {
        if (child == null) return false;
        AccessibilityNodeInfo lnxInfo = NodeInfoParseUtil.findAccessibilityNodeInfosByViewId(child, "com.tencent.wework:id/ls0", "android.view.ViewGroup");
        if (lnxInfo == null || lnxInfo.getChildCount() <= 1 || lnxInfo.getChild(1) == null || lnxInfo.getChild(1).getText() == null) return false;
        return "＠微信".equals(lnxInfo.getChild(1).getText().toString());
    }

    private void slideUp() {
        isClicking = true;
        DisplayMetrics displayMetrics = mService.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int top = (int) (height * 0.1);
        int bottom = (int) (height * 0.9);
        LogUtil.e("friendListPage slideUp");
        GestureActionUtils.performSwipeDown(mService, bottom, top, this);
    }
}
