package com.example.order.developtools

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.order.developtools.config.TaoBaoConfig
import com.example.order.developtools.utils.TestDialogUtils
import com.example.order.developtools.view.ExpandListAdapter
import com.example.order.developtools.view.FeatureConstants
import com.example.order.developtools.view.SettingConstants
import java.lang.Exception


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
            FeatureConstants.TAO_BAO_KEY_WORDS -> {
                handleTaobaoKeyWords()
            }
            SettingConstants.ACTION_ACCESSIBILITY_SETTINGS -> {
                handleAccessibilitySettings()
            }
        }
    }

    private fun handleAccessibilitySettings() {
        try {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            mActivity.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(mActivity, mActivity.getString(R.string.no_accessibility_setting), Toast.LENGTH_SHORT).show()
        }

    }

    private fun handleTaobaoKeyWords() {
        TestDialogUtils.showInputDialog(mActivity, mActivity.getString(R.string.taobao_key_words_setting),
                "", TaoBaoConfig.mKeyWords, object : TestDialogUtils.InputDialogCallback {
            override fun onClick(inputText: String) {
                TaoBaoConfig.setKeyWords(inputText)
                Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show()
            }

        })
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