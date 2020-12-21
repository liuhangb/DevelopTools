package com.example.order.developtools

import android.accessibilityservice.AccessibilityService
import android.text.TextUtils
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.order.developtools.utils.GestureActionUtils
import com.example.order.developtools.utils.LogUtil
import com.example.order.developtools.utils.PrintUtils
import java.util.*

/**
 * Created by lh, 2020/12/21
 */
class KuShouEventProcessor(mService: AccessibilityService): BaseEventProcessor(mService) {
    init {
        initTimerTask()
    }
    private var mLastSlideUpTime: Long = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (!isInFoundPage()) {
            clickFoundPage()
        } else if (isFoundCloseBtn()) {
            clickById(mService.rootInActiveWindow, "com.kuaishou.nebula:id/close",
                Button::class.java.name)
        }
        else if (!isFoundRedPacket()){
            val printNodeInfo = PrintUtils.printNodeInfoById(mService.rootInActiveWindow)
            LogUtil.d("isFoundRedPacket: " + printNodeInfo)
            slideUp()
        }

    }

    private fun initTimerTask() {
        val timer = Timer()
        timer.schedule(SlideJob(), 0, 12 * 1000)
    }

    private fun isFoundRedPacket(): Boolean {
        if (mService == null || mService.rootInActiveWindow == null) {
            return false
        }

        val rootInActiveWindow = mService.rootInActiveWindow!!
        return checkContentById(
            rootInActiveWindow,
            "com.kuaishou.nebula:id/circular_progress_bar",
            View::class.java.name
        )
//                || checkContentById(
//            rootInActiveWindow,
//            " com.kuaishou.nebula:id/gold_egg_packet",
//            ImageView::class.java.name
//        )
    }

    private fun isInFoundPage(): Boolean {
        if (mService == null || mService.rootInActiveWindow == null) {
            return false
        }

        val rootInActiveWindow = mService.rootInActiveWindow!!
        return checkContentById(
            rootInActiveWindow,
            "com.kuaishou.nebula:id/forward_button",
            LinearLayout::class.java.name
        )
    }

    private fun clickFoundPage() {
        val displayMetrics = mService.resources.displayMetrics
        val width = displayMetrics.widthPixels
        val end = (width * .25).toInt()
        val start = (width * .75).toInt()
        GestureActionUtils.performSwipeX(mService, start, end, null)
    }

    inner class SlideJob : TimerTask() {
        override fun run() {
            if ( mService== null || mService.rootInActiveWindow == null) {
                return
            }
            val rootInActiveWindow = mService.rootInActiveWindow!!
            val packageName = rootInActiveWindow.packageName
            if (TextUtils.isEmpty(packageName) || packageName != desiredPackageName()) {
                return
            }

            if (isInFoundPage()) {
                slideUp()
            }
        }

    }

    private fun slideUp() {
        if (mService == null) {
            return
        }
        if (System.currentTimeMillis() - mLastSlideUpTime <= 1000) {
          return
        }
        val displayMetrics = mService.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val top = (height * .1).toInt()
        val bottom = (height * .9).toInt()
        GestureActionUtils.performSwipeDown(mService, bottom, top, null)
        mLastSlideUpTime = System.currentTimeMillis()
    }

    private fun isFoundCloseBtn(): Boolean {
        if (mService == null || mService.rootInActiveWindow == null) {
            return false
        }

        val rootInActiveWindow = mService.rootInActiveWindow!!
        return checkContentById(
            rootInActiveWindow,
            "com.kuaishou.nebula:id/close",
            Button::class.java.name
        )
    }

    override fun isEnable(): Boolean {
        return true
    }

    override fun desiredPackageName(): String {
        return "com.kuaishou.nebula"
    }
}