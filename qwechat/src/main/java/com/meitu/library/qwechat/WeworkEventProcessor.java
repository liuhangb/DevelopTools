package com.meitu.library.qwechat;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.meitu.library.qwechat.page.AddContactPage;
import com.meitu.library.qwechat.page.ClientInfoPage;
import com.meitu.library.qwechat.page.FeatureBiddenPage;
import com.meitu.library.qwechat.page.FriendListPage;
import com.meitu.library.qwechat.page.IPage;
import com.meitu.library.qwechat.page.SendRequestPage;
import com.meitu.library.qwechat.utils.LogUtil;
import com.meitu.library.qwechat.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lh, 2023/2/16
 */
public class WeworkEventProcessor extends BaseEventProcessor{
    private final String TAG = "WeworkEventProcessor";
    private IPage mCurrentPage = null;
    private List<IPage> mPageList = new ArrayList<>();
    public static boolean isUnableAddFriend;
    private boolean mIsHandleDelayEvent;

    public WeworkEventProcessor(@NonNull AccessibilityService service) {
        super(service);
        SendRequestPage sendRequestPage = new SendRequestPage();
        AddContactPage addContactPage = new AddContactPage();
        mPageList.add(sendRequestPage);
        mPageList.add(new FriendListPage());
        mPageList.add(addContactPage);
        mPageList.add(new ClientInfoPage());
        mPageList.add(new FeatureBiddenPage());
    }

    @Override
    public boolean isEnable() {
        return !isUnableAddFriend;
    }

    @Override
    public String desiredPackageName() {
        return "com.tencent.wework";
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (mIsHandleDelayEvent) {
            LogUtil.d("ignore event:" + event.toString());
            return;
        }
        mIsHandleDelayEvent = true;
        ThreadUtils.runOnMainUI(new Runnable() {
            @Override
            public void run() {
                handleEventAfterDelay();
                mIsHandleDelayEvent = false;
            }
        }, getDelayTime());
    }

    private int getDelayTime() {
        int value = (new Random().nextInt(4) + 2) * 1000;
        LogUtil.d("getDelayTime:" + value);
        return value;
    }

    private void handleEventAfterDelay() {
        AccessibilityNodeInfo rootInActiveWindow = mService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            LogUtil.e(TAG, "rootInActiveWindow == null");
            return;
        }
        IPage currentPage = findCurrentPage(rootInActiveWindow);
        if (currentPage == null) return;

        if (mCurrentPage != null && mCurrentPage != currentPage) {
            mCurrentPage.onInvisible();
        }
//        else if (mCurrentPage == currentPage && System.currentTimeMillis() - mLastTime <= 3000){
//            return;
//        }
        currentPage.onVisible(mService, mCurrentPage);
        mCurrentPage = currentPage;
        mCurrentPage.onHandleEvent(rootInActiveWindow);
    }

    private IPage findCurrentPage(AccessibilityNodeInfo root) {
        for (int i = 0; i < mPageList.size(); i++) {
            IPage iPage = mPageList.get(i);
            if (iPage.isCurrentPage(root)) {
                return iPage;
            }
        }
        return null;
    }
}
