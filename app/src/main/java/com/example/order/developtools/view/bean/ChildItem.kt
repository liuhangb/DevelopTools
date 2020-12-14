package com.example.order.developtools.view.bean

/**
 * Created by lh, 2020-04-30
 */
class ChildItem {
    var id: Long?
    /**
     * 标题
     */
    var title: String? = ""

    constructor(title: String?, id: Long?) {
        this.title = title
        this.id = id
    }
}