package com.dotop.smartbutler.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.dotop.smartbutler.R

/**
 * Created by charley on 2017/7/22.
 */
class CustomDialog(context: Context, style: Int) : Dialog(context, style) {

    //定义模板
    constructor(context: Context, layout: Int, style: Int) : this(context, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, layout, style, Gravity.CENTER)
    //实例
    constructor(context: Context, width: Int, height: Int, layout: Int, style: Int, gravity: Int) : this(context, width, height, layout, style, gravity, R.style.pop_anim_style)
    //定义属性
    constructor(context: Context, width: Int, height: Int, layout: Int, style: Int, gravity: Int, anim: Int) : this(context, style) {
        setContentView(layout)
        val layoutParams = window.attributes

        layoutParams.width = if (width == 0){
            WindowManager.LayoutParams.MATCH_PARENT
        }else {
            width
        }

        layoutParams.height = if (height == 0){
            WindowManager.LayoutParams.WRAP_CONTENT
        }else {
            height
        }

        layoutParams.gravity = gravity
        window.attributes = layoutParams
        window.setWindowAnimations(anim)
    }


}