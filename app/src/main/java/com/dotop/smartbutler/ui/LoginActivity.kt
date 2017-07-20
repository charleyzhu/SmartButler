package com.dotop.smartbutler.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dotop.smartbutler.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         LoginActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/18上午1:21
 * 描述            登录页面
 */


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnClicks()
    }

    //注册按钮点击
    private fun btnClicks() {
        btn_register.onClick {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
    }
}