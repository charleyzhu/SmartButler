package com.dotop.smartbutler.utils

import android.util.Log

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.utils
 * 文件名:         L
 * 创建者:         Bugs
 * 创建时间:        2017/7/17下午11:20
 * 描述            日志类
 */


object L {
    const val DEBUG: Boolean = true
    const val TAG = "Smartbutler"

    //4个等级 Debug,Info,Warning,Error

    fun d(text: String) {
        if (DEBUG)Log.d(TAG, text)
    }

    fun i(text: String) {
        if (DEBUG)Log.i(TAG, text)
    }
    fun w(text: String) {
        if (DEBUG)Log.w(TAG, text)
    }
    fun e(text: String) {
        if (DEBUG)Log.e(TAG, text)
    }
}

