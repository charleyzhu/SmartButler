package com.dotop.smartbutler.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.dotop.smartbutler.MainActivity
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.SmartUser
import com.dotop.smartbutler.utils.ShareUtils
import com.dotop.smartbutler.utils.StaticClass
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         LoginActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/18上午1:21
 * 描述            登录页面
 */


class LoginActivity : AppCompatActivity() {

    val mConText = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        btnClicks()

    }

    private fun initView() {

        //如果保存用户名和密码那就取出来
        ck_savepassword.isChecked = ShareUtils.get(this, StaticClass.isSavePassword, false) as Boolean
        if (ck_savepassword.isChecked) {
            val saveUser = ShareUtils.get(mConText,StaticClass.userName,"") as String
            val savePass = ShareUtils.get(mConText,StaticClass.passWord,"") as String
            et_UserName.setText(saveUser.toCharArray(), 0, saveUser.length)
            et_Password.setText(savePass.toCharArray(), 0, savePass.length)
        }

    }


    private fun btnClicks() {
        //注册按钮点击
        btn_register.onClick {
            startActivityForResult(Intent(this@LoginActivity, RegisterActivity::class.java), 101)
        }
        //登录按钮点击
        btn_Login.onClick {
            if (et_UserName.text.isEmpty()) {
                toast(getString(R.string.enterUserName))
                return@onClick
            } else if (et_Password.text.isEmpty()) {
                toast(getString(R.string.enterPassword))
                return@onClick
            }

            val su = SmartUser(et_UserName.text.toString().trim(), et_Password.text.toString().trim())
            su.login(object : SaveListener<SmartUser>() {
                override fun done(p0: SmartUser?, e: BmobException?) {
                    if (null == e) {
                        toast(getString(R.string.loginWasSuccessful))
                        if (ck_savepassword.isChecked) {
                            ShareUtils.put(mConText, StaticClass.isSavePassword, true)
                            ShareUtils.put(mConText, StaticClass.userName, et_UserName.text.toString().trim())
                            ShareUtils.put(mConText, StaticClass.passWord, et_Password.text.toString().trim())
                        }
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                        return
                    }

                    toast("${getString(R.string.error)}:$e")

                }
            })
        }

        //找回密码
        tv_forget.onClick {
            startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 102) {
            val username = data?.getStringExtra("username") ?: ""
            val password = data?.getStringExtra("password") ?: ""
            et_UserName.setText(username.toCharArray(), 0, username.length)
            et_Password.setText(password.toCharArray(), 0, password.length)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //如果不保存密码则删除这些key
        if (!ck_savepassword.isChecked){
            ShareUtils.delKey(mConText, StaticClass.userName)
            ShareUtils.delKey(mConText, StaticClass.passWord)
        }
    }
}