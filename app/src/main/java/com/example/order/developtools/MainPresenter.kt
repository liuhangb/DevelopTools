package com.example.order.developtools

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.order.developtools.utils.TestDialogUtils
import com.example.order.developtools.view.ExpandListAdapter
import com.example.order.developtools.view.FeatureConstants

/**
 * Created by lh, 2020/12/14
 */
class MainPresenter : ExpandListAdapter.Companion.ItemClickCallback{

    private var mActivity: AppCompatActivity

    constructor(activity: AppCompatActivity) {
        mActivity = activity;
    }

    override fun onItemClick(id: Long) {
        when (id) {
            FeatureConstants.TAO_BAO_PURCHASE_TASK_TIME -> {
                handleTaobaoPurchaseTaskTime()
            }
        }
    }

    private fun handleTaobaoPurchaseTaskTime() {

        val mTaskTime = TaoBaoConfig.mTaskTime
        TestDialogUtils.showInputDialog(mActivity, mActivity.getString(R.string.taobao_purchase_task_time_setting),
                "格式: 2020-12-14 16:38:00", mTaskTime, object : TestDialogUtils.InputDialogCallback {
            override fun onClick(inputText: String) {
                TaoBaoConfig.setTaskTime(inputText)
                Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show()
            }

        })
    }

}