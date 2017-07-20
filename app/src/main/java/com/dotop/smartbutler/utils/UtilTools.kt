package com.dotop.smartbutler.utils

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.utils
 * 文件名:         UtilTools
 * 创建者:         Bugs
 * 创建时间:        2017/7/12上午2:29
 * 描述            工具类
 */


object UtilTools {
    fun setFont(mContext: Context, tv: TextView) {
        val typeFace = Typeface.createFromAsset(mContext.assets,"Fonts/FONT.TTF")
        tv.setTypeface(typeFace)
    }
}