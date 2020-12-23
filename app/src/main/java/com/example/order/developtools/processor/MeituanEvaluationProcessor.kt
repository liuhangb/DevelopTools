package com.example.order.developtools.processor

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.os.Bundle
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.order.developtools.utils.GestureActionUtils
import com.example.order.developtools.utils.LogUtil

/**
 * Created by lh, 2020/12/15
 */
class MeituanEvaluationProcessor(service: AccessibilityService) : BaseEventProcessor(service) {
    private var isClickNiMingBtn: Boolean = false

    override fun isEnable(): Boolean {
        return true
    }

    override fun desiredPackageName(): String {
        return "com.sankuai.meituan.takeoutnew"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        if (event?.eventType != AccessibilityEvent.TYPE_WINDOWS_CHANGED) {
//            return
//        }
        val rootInActiveWindow = mService.rootInActiveWindow ?: return
        LogUtil.d("onAccessibilityEvent event type: ${event?.eventType}, text:${event?.text}, desc: ${event?.contentDescription}")
        when {
            isOrderListPage(rootInActiveWindow) -> {
                processOrderList(rootInActiveWindow)
            }
            isEvaluationPage(rootInActiveWindow) -> {
                processEvaluation(rootInActiveWindow)
            }
            isAlbumPage(rootInActiveWindow) -> {
                processAlbum(rootInActiveWindow)
            }
            isEvaluationFinishPage(rootInActiveWindow) -> {
                processEvaluationFinish(rootInActiveWindow)
            }
        }
    }

    private fun isEvaluationPage(rootInActiveWindow: AccessibilityNodeInfo) : Boolean{
        return checkContentByText(rootInActiveWindow, "您对骑手满意吗？", TextView::class.java.name)
    }

    private fun isOrderListPage(rootInActiveWindow: AccessibilityNodeInfo) : Boolean {
        return checkContentByText(rootInActiveWindow, "待评价", TextView::class.java.name)
    }

    private fun isAlbumPage(rootInActiveWindow: AccessibilityNodeInfo) : Boolean{
        return checkContentById(rootInActiveWindow, "com.sankuai.meituan.takeoutnew:id/rv_photos", "android.support.v7.widget.RecyclerView")
    }

    private fun isEvaluationFinishPage(rootInActiveWindow: AccessibilityNodeInfo) : Boolean{
        return checkContentByText(rootInActiveWindow, "评价完成，获得", TextView::class.java.name)
    }

    private fun processOrderList(rootInActiveWindow: AccessibilityNodeInfo) {
        LogUtil.d("processOrderList")
        val takeOutTxtNodeInfo = findAccessibilityNodeInfosByViewId(rootInActiveWindow,
                "com.sankuai.meituan.takeoutnew:id/takeout_txt_tab", TextView::class.java.name) ?: return
        if (takeOutTxtNodeInfo?.isSelected != true) {
            val findAccessibilityNodeInfosByViewId = findAccessibilityNodeInfosByViewId(rootInActiveWindow,
                    "com.sankuai.meituan.takeoutnew:id/takeout_txt_tab", TextView::class.java.name) ?: return
            performClick(findAccessibilityNodeInfosByViewId)
            return
        }

        val btnOrderNodeInfo = findAccessibilityNodeInfosByText(rootInActiveWindow, "评价得33金豆", TextView::class.java.name)

        btnOrderNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    private fun processEvaluation(rootInActiveWindow: AccessibilityNodeInfo)  {
        LogUtil.d("processEvaluation")
        val satifyNodeInfo = findDestNodesByFlatNode(rootInActiveWindow, "您对骑手满意吗？", ViewGroup::class.java.name)
        val hasPinJiaBtn = checkContentByText(rootInActiveWindow, "写评价", TextView::class.java.name)
        if (!hasPinJiaBtn) {
            val size = satifyNodeInfo.size
            if (size > 0) {
                LogUtil.d("satifyNodeInfo enable: " + satifyNodeInfo[size - 1].isEnabled)
                satifyNodeInfo[size - 1]?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
            return
        }

        val isExitSatifiedText: Boolean = checkContentByText(rootInActiveWindow, "非常满意", TextView::class.java.name)

        if (!isExitSatifiedText) {
            val niMingBtnNodeInfo = findDestNodeByFlatNode(rootInActiveWindow, "匿名提交", ViewGroup::class.java.name) ?: return
            if (!isClickNiMingBtn) {
                niMingBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                isClickNiMingBtn = true
            }

            val totalPinJia: List<AccessibilityNodeInfo> = findDestNodesByFlatNode(rootInActiveWindow, "总体", ViewGroup::class.java.name) ?: return
            val accessibilityNodeInfo = totalPinJia[totalPinJia.size - 1]
            val child = accessibilityNodeInfo.getChild(accessibilityNodeInfo.childCount - 1)
            performClick(child)
            return
        }


        val commentNodeInfo = findAccessibilityNodeInfosByViewId(rootInActiveWindow,
                "com.sankuai.meituan.takeoutnew:id/edit_comment", EditText::class.java.name) ?: return
        if ("口味赞，包装好，推荐给大家" == commentNodeInfo.text) {
            val arguments = Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    "不错不错不错不错不错不错不错不错不错不错不错不错不错不错不错不错");
            commentNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        }

        val isExitUploadPicHint = checkContentByText(rootInActiveWindow, "传图得 20 金豆", TextView::class.java.name)
        if (isExitUploadPicHint) {
            val findDestNodesByFlatNode = findDestNodesByFlatNode(rootInActiveWindow, "传图得 20 金豆", ViewGroup::class.java.name) ?: return
            if (findDestNodesByFlatNode.size > 0) {
                findDestNodesByFlatNode[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }

        val isExitPinJiaFoodsHint = checkContentByText(rootInActiveWindow, "评价菜品可再得 2 金豆", TextView::class.java.name)
        if (isExitPinJiaFoodsHint) {
            val findDestNodesByFlatNode: MutableList<AccessibilityNodeInfo> = findDestNodesByFlatNode(rootInActiveWindow, "包装", ViewGroup::class.java.name)
            val size = findDestNodesByFlatNode.size
            if (size > 0) {
                val accessibilityNodeInfo = findDestNodesByFlatNode[size -1].getChild(0)
                performClick(accessibilityNodeInfo)
            }
        }

        val checkContentByText: Boolean = checkContentByText(rootInActiveWindow, "金豆 33", TextView::class.java.name)
        if (checkContentByText) {
            val findAccessibilityNodeInfosByText = findAccessibilityNodeInfosByText(rootInActiveWindow, "提交", TextView::class.java.name) ?: return
            performClick(findAccessibilityNodeInfosByText)
        }
    }

    private fun processAlbum(rootInActiveWindow: AccessibilityNodeInfo)  {
        LogUtil.d("processAlbum")
        if (clickById(rootInActiveWindow, "com.sankuai.meituan.takeoutnew:id/done", TextView::class.java.name)) {
            return
        }

        val findAccessibilityNodeInfosByViewId: AccessibilityNodeInfo = findAccessibilityNodeInfosByViewId(rootInActiveWindow, "com.sankuai.meituan.takeoutnew:id/rv_photos",
                "android.support.v7.widget.RecyclerView") ?: return
        if (findAccessibilityNodeInfosByViewId.childCount >= 2) {
            val child: AccessibilityNodeInfo = findAccessibilityNodeInfosByViewId.getChild(1)
            clickById(child, "com.sankuai.meituan.takeoutnew:id/v_selected", ImageView::class.java.name)
        }
    }

    private fun processEvaluationFinish(rootInActiveWindow: AccessibilityNodeInfo) {
        val findAccessibilityNodeInfosByText = findAccessibilityNodeInfosByText(rootInActiveWindow, "评价得33金豆", TextView::class.java.name) ?: return
        performClick(findAccessibilityNodeInfosByText)
        LogUtil.d("processEvaluationFinish 评价得33金豆")
    }

    private fun performClick(child: AccessibilityNodeInfo) {
        val rect = Rect()
        child.getBoundsInScreen(rect)
        GestureActionUtils.performClick(mService, rect.centerX(), rect.centerY(), null)
    }

}