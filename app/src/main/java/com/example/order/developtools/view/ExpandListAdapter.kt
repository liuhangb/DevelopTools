package com.example.order.developtools.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.order.developtools.R
import com.example.order.developtools.utils.LogUtil
import com.example.order.developtools.view.bean.Group



/**
 * Created by lh, 2020-04-30
 */
class ExpandListAdapter(var groupDatas: List<Group>?, var mContext: Context?): BaseExpandableListAdapter() {

    private var mItemClickCallback: ItemClickCallback? = null

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val groupViewHolder: GroupViewHolder
        var view : View? = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.setting_item_layout, parent, false)
            LogUtil.d("getGroupView: $view")

            groupViewHolder = GroupViewHolder()
            groupViewHolder.tvTitle = view?.findViewById(R.id.title) as TextView
            view?.tag = groupViewHolder
        } else {
            groupViewHolder = view.tag as GroupViewHolder
        }
        groupViewHolder.tvTitle!!.text = groupDatas?.get(groupPosition)?.title?: ""
        return view!!
    }

    override fun getGroupId(groupPosition: Int): Long {
       return ((groupDatas?.get(groupPosition)?.id)?:-1L)
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val childViewHolder: ChildViewHolder
        var view : View? = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.setting_item_layout, parent, false)
            childViewHolder = ChildViewHolder()
            childViewHolder.tvTitle = view?.findViewById(R.id.title) as TextView
            childViewHolder.tvTitle?.setOnClickListener{
                notifyItemClick(childViewHolder.id)
            }
            view?.tag = childViewHolder
        } else {
            childViewHolder = view?.tag as ChildViewHolder
        }
        childViewHolder.tvTitle!!.text = groupDatas?.get(groupPosition)?.childItems?.get(childPosition)?.title
        childViewHolder.id = groupDatas?.get(groupPosition)?.childItems?.get(childPosition)?.id?: 0
        return view !!
    }

    private fun notifyItemClick(childPosition: Long) {
        mItemClickCallback?.onItemClick(childPosition)
    }


    fun setClickListener(itemClickCallback: ItemClickCallback?) {
        mItemClickCallback = itemClickCallback
    }

    override fun getGroup(groupPosition: Int): Any {
        return groupDatas?.get(groupPosition)?: Any()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return groupDatas?.get(groupPosition)?.childItems?.get(childPosition)?.id?:-1
    }

    override fun getGroupCount(): Int {
        return groupDatas?.size ?: 0
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return (groupDatas?.get(groupPosition)?.childItems?.size)?:0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return (groupDatas?.get(groupPosition)?.childItems?.get(childPosition))?:Any()
    }

    companion object{
        class GroupViewHolder {
            var tvTitle: TextView? = null

        }

        class ChildViewHolder {
            var tvTitle: TextView? = null
            var id: Long = 0
        }

        interface ItemClickCallback {
            fun onItemClick(id: Long)
        }
    }
}