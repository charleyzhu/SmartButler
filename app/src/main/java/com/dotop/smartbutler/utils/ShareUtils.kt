package com.dotop.smartbutler.utils

import android.content.Context

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.utils
 * 文件名:         ShareUtils
 * 创建者:         Bugs
 * 创建时间:        2017/7/17下午11:52
 * 描述            TODO
 */


object ShareUtils {
    const val NAME = "config"

    fun put(mContext: Context, key: String,value:Any ) {
        val sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        val spedit = sp.edit()
        if (value is String){
            spedit.putString(key,value)
        }else if (value is Boolean) {
            spedit.putBoolean(key,value)
        }else if (value is Float) {
            spedit.putFloat(key,value)
        }else if (value is Int){
            spedit.putInt(key,value)
        }else if (value is Long){
            spedit.putLong(key,value)
        }
        spedit.commit()

    }

    fun get(mContext: Context, key: String, default: Any): Any {
        val sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        if (default is Long){
            return sp.getLong(key,default)
        }else if (default is Boolean) {
            return sp.getBoolean(key,default)
        }else if (default is Float) {
            return sp.getFloat(key,default)
        }else if (default is Int){
            return sp.getInt(key,default)
        }else {
            return sp.getString(key,default as String)
        }
    }

    fun delKey(mContext: Context, key: String) {
        val sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        sp.edit().remove(key).commit()
    }

    fun delAll(mContext: Context){
        val sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        sp.edit().clear().commit()
    }
}