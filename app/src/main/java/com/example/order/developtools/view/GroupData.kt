package com.example.order.developtools.view

import com.example.order.developtools.DemoApplication
import com.example.order.developtools.R
import com.example.order.developtools.view.bean.ChildItem
import com.example.order.developtools.view.bean.Group

/**
 * Created by lh, 2020-04-30
 */
class GroupData {

    companion object{
        fun getGroups(): List<Group> {
            var groups = ArrayList<Group>()
            groups.add(getFeaturesGroup())
            return groups
        }

       private fun getFeaturesGroup(): Group {
            var group = Group("Feature项", 2)
            var childItems = ArrayList<ChildItem>()
            childItems.add(ChildItem(getString(R.string.taobao_purchase_task_time_setting), FeatureConstants.TAO_BAO_PURCHASE_TASK_TIME))
            childItems.add(ChildItem(getString(R.string.taobao_key_words_setting), FeatureConstants.TAO_BAO_KEY_WORDS))

           group.childItems = childItems
            return group
        }

        private fun getString(resId: Int) :String {
            val application = DemoApplication.getApplication()
            return application.getString(resId)
        }
        
    }


}