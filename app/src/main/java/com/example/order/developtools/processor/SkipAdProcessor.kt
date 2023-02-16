package com.example.order.developtools.processor

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView
import com.example.order.developtools.utils.LogUtil

/**
 * Created by lh, 2021/2/26
 */
class SkipAdProcessor(service: AccessibilityService) : BaseEventProcessor(service) {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val rootInActiveWindow = mService.rootInActiveWindow ?: return

        LogUtil.d("rootInActiveWindow： $rootInActiveWindow")
        val findDestNodeByFuzzyMatch =
            findDestNodeByFuzzyMatch(rootInActiveWindow, "跳过", TextView::class.java.name)
        performClick(findDestNodeByFuzzyMatch)
    }

    override fun isEnable(): Boolean {
        return true
    }

    override fun desiredPackageName(): String {
        return ""
    }

    override fun isNeedAllEvent(): Boolean {
        return true
    }
}