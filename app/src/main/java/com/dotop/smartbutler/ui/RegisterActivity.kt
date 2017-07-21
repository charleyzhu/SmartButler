package com.dotop.smartbutler.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.inputmethod.InputMethodManager
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.SmartUser
import com.dotop.smartbutler.utils.L
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         RegisterActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/19下午11:22
 * 描述            用户注册
 */


class RegisterActivity : BaseActivity() {

    var sex: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        btnClicks()
    }

    private fun initView() {
        rg_sexGroup.setOnCheckedChangeListener { radioGroup, index ->
            if (index == R.id.rb_man) {
                sex = true
            } else if (index == R.id.rb_woman) {
                sex = false
            }
        }
    }

    private fun btnClicks() {
        btn_register.onClick {

            val view = window.peekDecorView()
            if (null != view){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }

            var desc: String

            if (et_UserName.text.isEmpty()) {
                toast(getString(R.string.enterUserName))
                return@onClick
            } else if (et_age.text.isEmpty()) {
                toast(getString(R.string.enterAge))
                return@onClick
            } else if (et_Password.text.isEmpty()) {
                toast(getString(R.string.enterPassword))
                return@onClick
            } else if (et_rePassword.text.isEmpty()) {
                toast(getString(R.string.enterRePassword))
                return@onClick
            } else if (et_Password.text.trim() != et_rePassword.text.trim()) {
                toast(getString(R.string.checkPasswordAndRePassword))
                return@onClick
            } else if (et_eMail.text.isEmpty()) {
                toast(getString(R.string.enterEmail))
                return@onClick
            }

            if (et_desc.text.isEmpty()) {
                desc = getString(R.string.noDesc)
            } else {
                desc = et_desc.text.toString()
            }


            val su = SmartUser(et_UserName.text.toString(),et_Password.text.toString(),et_eMail.text.toString(),et_age.text.toString(), desc,sex)

            su.signUp(object : SaveListener<SmartUser>(){
                override fun done(p0: SmartUser?, e: BmobException?) {
                    if (e == null){
                        toast(getString(R.string.regsigerWasSuccessful))

                        val intent = Intent()
                        intent.putExtra("username",su.mUsername)
                        intent.putExtra("password",su.mPassword)
                        //记录下要返回给那个父亲，可能还有什么继父什么的
                        setResult(102, intent)
                        finish()
                    }else {
                        toast("${getString(R.string.error)}:$e")
                    }

                }
            })
        }
    }


}