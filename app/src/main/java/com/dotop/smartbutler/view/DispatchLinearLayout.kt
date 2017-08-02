package com.dotop.smartbutler.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.LinearLayout


/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.view
 * 文件名:         DispatchLinearLayout
 * 创建者:         Bugs
 * 创建时间:        2017/8/1上午9:44
 * 描述            重写LinearLayout，监听事件
 */


class DispatchLinearLayout : LinearLayout {

    var dispatchKeyEventListener: DispatchKeyEventListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    interface DispatchKeyEventListener {
        fun dispatchKeyEvent(event: KeyEvent): Boolean
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        //如果dispatchKeyEventListener 不为空，说明调用了，去获取事件
        if (dispatchKeyEventListener != null) {
            return dispatchKeyEventListener!!.dispatchKeyEvent(event)
        }
        return super.dispatchKeyEvent(event)
    }


}