package com.example.order.developtools.view.bean

import com.example.order.developtools.view.bean.ChildItem

/**
 * Created by lh, 2020-04-30
 */
class Group {
    var id: Long?
    /**
     * 标题
     */
    var title: String?
    /**
     * 内容
     */
    var childItems: List<ChildItem>? = null

    constructor(title: String?, id: Long?) {
        this.title = title
        this.id = id
    }
}