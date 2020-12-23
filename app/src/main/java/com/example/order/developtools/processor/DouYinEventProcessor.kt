package com.example.order.developtools.processor

import android.accessibilityservice.AccessibilityService
import android.view.View
import android.widget.ImageView

/**
 * Created by lh, 2020/12/23
 */
class DouYinEventProcessor(mService: AccessibilityService) : BaseGetVideoMoneyEventProcessor(mService) {
    override fun isFoundRedPacket(): Boolean {
        if (mService == null || mService.rootInActiveWindow == null) {
            return false
        }

        val rootInActiveWindow = mService.rootInActiveWindow!!
        return checkContentById(
                rootInActiveWindow,
                "com.ss.android.ugc.aweme.lite:id/cqn",
                View::class.java.name
        )
    }

    override fun isInVideoListPage(): Boolean {
        if (mService == null || mService.rootInActiveWindow == null) {
            return false
        }

        val rootInActiveWindow = mService.rootInActiveWindow!!
        return checkContentById(
                rootInActiveWindow,
                "com.ss.android.ugc.aweme.lite:id/c67",
                ImageView::class.java.name
        )
    }

    override fun isFoundCloseBtn(): Boolean {
        return false
    }

    override fun desiredPackageName(): String {
        return "com.ss.android.ugc.aweme.lite"
    }
}