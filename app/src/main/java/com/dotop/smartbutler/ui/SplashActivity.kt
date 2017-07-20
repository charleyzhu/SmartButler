package com.dotop.smartbutler.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.dotop.smartbutler.MainActivity
import com.dotop.smartbutler.R
import com.dotop.smartbutler.utils.ShareUtils
import com.dotop.smartbutler.utils.StaticClass
import com.dotop.smartbutler.utils.UtilTools
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         SplashActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/18上午12:47
 * 描述            功能介绍
 */


class SplashActivity : AppCompatActivity() {

    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */
    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var intnet:Intent? = null

            when (msg?.what) {
                StaticClass.HANDLER_SPLASH->{
                    if (isFirst()) {
                        intnet = Intent(this@SplashActivity,GuideActivity::class.java)
                    }else {
                        intnet = Intent(this@SplashActivity,LoginActivity::class.java)

                    }
                }
            }
            startActivity(intnet)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        UtilTools.setFont(this,tv_splash)
        initView()


    }

    private fun initView() {

        //延时两秒
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000)

    }

    private fun  isFirst(): Boolean {
        val isFirst:Boolean = ShareUtils.get(this,StaticClass.SHARE_IS_FIRST,true) as Boolean
        if (isFirst) {
            ShareUtils.put(this,StaticClass.SHARE_IS_FIRST,false)
            return isFirst
        }
        return true
//        return isFirst
    }

    //禁止返回键
    override fun onBackPressed() {

    }
}