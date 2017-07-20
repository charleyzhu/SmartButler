package com.dotop.smartbutler.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Switch

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         BaseActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/12上午1:50
 * 描述            Activity 基类用于统一属性和方法
 */


/**
 * 主要做的事情：
 * 1.统一的属性
 * 2.统一的接口
 * 3.统一的方法
 */
open class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //显示返回键
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //点击事件判断点击的菜单
        when (item?.itemId) {
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}