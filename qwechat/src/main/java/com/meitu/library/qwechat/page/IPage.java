package com.meitu.library.qwechat.page;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by lh, 2023/3/13
 */
public interface IPage {
    /**
     * 页面可见
     */
    void onVisible(AccessibilityService service, IPage lastPage);

    /**
     * 页面不可见
     */
    void onInvisible();

    void onHandleEvent(AccessibilityNodeInfo root);

    boolean isCurrentPage(AccessibilityNodeInfo root);
}
