package com.example.order.developtools

/**
 * Created by lh, 2020/12/14
 */
class TaoBaoConfig {
    companion object {
        private val mCallbackList: MutableList<ConfigCallback> = ArrayList()
        var mTaskTime: String = "2020-12-14 16:05:00"

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