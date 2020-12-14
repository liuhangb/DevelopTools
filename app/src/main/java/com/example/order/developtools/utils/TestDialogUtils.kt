package com.example.order.developtools.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.widget.EditText
import java.lang.StringBuilder

/**
 * Created by hzy, 2019/10/16
 */
class TestDialogUtils private constructor() {

    interface InputDialogCallback {

        /**
         * 输出文字
         * @param inputText
         */
        fun onClick(inputText: String)
    }

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        /**
         * 打开输入弹窗
         *
         * @param activity
         * @param callback
         */
        fun showInputDialog(activity: Activity, title: String, hint: String, callback: InputDialogCallback) {
            /*@setView 装入一个EditView
         */
            val editText = EditText(activity)
            editText.hint = hint
            val inputDialog = AlertDialog.Builder(activity)
            inputDialog.setTitle(title).setView(editText)
            inputDialog.setPositiveButton("确定"
            ) { dialog, which -> callback.onClick(editText.text.toString()) }.show()
        }

        /**
         * 打开输入弹窗
         *
         * @param activity
         * @param callback
         */
        fun showInputDialog(activity: Activity, title: String, hint: String, text: String, callback: InputDialogCallback) {
            /*@setView 装入一个EditView
         */
            val editText = EditText(activity)
            editText.hint = hint
            editText.text = SpannableStringBuilder(text)
            val inputDialog = AlertDialog.Builder(activity)
            inputDialog.setTitle(title).setView(editText)
            inputDialog.setPositiveButton("确定"
            ) { dialog, which -> callback.onClick(editText.text.toString()) }.show()
        }

        /**
         * 打开输入弹窗,输入为空不回调
         *
         * @param activity
         * @param callback
         */
        fun showInputDialogForbidenEmptyString(activity: Activity, title: String, hint: String, callback: InputDialogCallback) {
            /*@setView 装入一个EditView
         */
            val editText = EditText(activity)
            editText.hint = hint
            val inputDialog = AlertDialog.Builder(activity)
            inputDialog.setTitle(title).setView(editText)
            inputDialog.setPositiveButton("确定"
            ) { dialog, which ->
                if (!TextUtils.isEmpty(editText.text.toString())) {
                    callback.onClick(editText.text.toString())
                }
            }.show()
        }

        /**
         * 打开提示弹窗
         * @param activity
         * @param title
         * @param message
         * @param confirm
         */
        fun showNormalDialog(activity: Activity, title: String, message: String, confirm: String) {
            /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
            val normalDialog = AlertDialog.Builder(activity)
            normalDialog.setTitle(title)
            normalDialog.setMessage(message)
            normalDialog.setPositiveButton(confirm
            ) { dialog, which -> }
            // 显示
            normalDialog.show()
        }

        /**
         * 双Button处理
         * @param activity
         * @param title
         * @param message
         * @param confirm
         * @param cancel
         */
        fun showNormalDialog(activity: Activity, title: String, message: String,
                             confirm: String,
                             cancel: String,
                             confirmListener: DialogInterface.OnClickListener,
                             cancelListener: DialogInterface.OnClickListener) {
            /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
            val normalDialog = AlertDialog.Builder(activity)
            normalDialog.setTitle(title)
            normalDialog.setMessage(message)
            normalDialog.setPositiveButton(confirm, confirmListener)
            normalDialog.setNegativeButton(cancel, cancelListener)
            // 显示
            normalDialog.show()
        }
    }
}
