package com.example.order.developtools.processor

import android.accessibilityservice.AccessibilityService
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import com.example.order.developtools.BaseEventProcessor
import com.example.order.developtools.utils.GestureActionUtils
import java.util.*

/**
 * Created by lh, 2020/12/23
 */
abstract class BaseGetVideoMoneyEventProcessor(myService: AccessibilityService): BaseEventProcessor(myService) {
    init {
        initTimerTask()
    }
    private var mLastSlideUpTime: Long = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (!isInVideoListPage()) {
            tryToSwipeToVideoListPage()
        } else if (isFoundCloseBtn()) {
            clickById(mService.rootInActiveWindow, "com.kuaishou.nebula:id/close",
                    Button::class.java.name)
        }
        else if (!isFoundRedPacket()){
            slideUp()
        }

    }

    private fun initTimerTask() {
        val timer = Timer()
        timer.schedule(SlideJob(), 0, 5 * 1000)
    }

    protected abstract fun isFoundRedPacket(): Boolean

    protected abstract fun isInVideoListPage(): Boolean

    private fun tryToSwipeToVideoListPage() {
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

            if (isInVideoListPage()) {
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

    protected abstract fun isFoundCloseBtn(): Boolean

    override fun isEnable(): Boolean {
        return true
    }

    abstract override fun desiredPackageName(): String
}