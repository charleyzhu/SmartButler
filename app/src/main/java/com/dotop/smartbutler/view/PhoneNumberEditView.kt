package com.dotop.smartbutler.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         PhoneNumberEditView
 * 创建者:         Bugs
 * 创建时间:        2017/7/27下午3:29
 * 描述            TODO
 */


class PhoneNumberEditView(mContext: Context,mAttrs: AttributeSet) : EditText(mContext,mAttrs) {


    fun AddNumber(number: String) {
        val rawText = text.toString()
        val newText = "$rawText$number"
        setText(newText.toCharArray(), 0, newText.length)
        setSelection(newText.length)
    }

    fun DelectLast(){
        val rawText = text.toString()
        //数组下目标从0开始所有这里要减去2
        val newText = rawText.substring(0..rawText.length-2)
        setText(newText.toCharArray(), 0, newText.length)
        setSelection(newText.length)
    }

    fun Clean(){
        setText("".toCharArray(), 0, "".length)
    }

}