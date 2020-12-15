package com.example.order.developtools

import com.example.order.developtools.utils.DateUtils
import com.example.order.developtools.utils.LogUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by lh, 2020/12/14
 */
class TaoBaoConfig {
    companion object {
        private val mCallbackList: MutableList<ConfigCallback> = ArrayList()
        var mTaskTime: String = "2020-12-14 16:05:00"

        init {
            // 预设抢购时间每天晚上八点
            val date = Date()
            date.hours = 20
            date.minutes = 0
            date.seconds = 0
            mTaskTime = DateUtils.stampToDate(date)
            LogUtil.d("mTaskTime init: $mTaskTime")
        }
        fun register(callback: ConfigCallback) {
            if (callback != null) {
                mCallbackList.add(callback)
            }
        }

        fun removeAll() {
            mCallbackList.clear()
        }

        fun setTaskTime(time: String) {
            if (time != mTaskTime) {
                mTaskTime = time
                for (configCallback in mCallbackList) {
                    configCallback.onTaskTimeChanged(time)
                }
            }
        }
    }

    interface ConfigCallback {
        fun onTaskTimeChanged(date: String)
    }


}