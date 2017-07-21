package com.dotop.smartbutler.ui

import android.content.Intent
import android.os.Bundle
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.SmartUser
import kotlinx.android.synthetic.main.activity_forgetpassword.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by charley on 2017/7/21.
 */
class ForgetPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpassword)

        BtnClicks()
    }

    private fun BtnClicks() {
        //修改密码
        btn_changerPassword.onClick {
            if (et_oldPassword.text.isEmpty()) {
                toast(getString(R.string.enterOldPassword))
                return@onClick
            } else if (et_Password.text.isEmpty()) {
                toast(getString(R.string.enterPassword))
                return@onClick
            } else if (et_rePassword.text.isEmpty()) {
                toast(getString(R.string.enterRePassword))
                return@onClick
            } else if (et_Password.text.trim() == et_rePassword.text.trim()) {
                toast(getString(R.string.checkPasswordAndRePassword))
                return@onClick
            }

            BmobUser.updateCurrentUserPassword(et_oldPassword.text.toString().trim(), et_Password.text.toString().trim(),
                    object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            if (null == e) {
                                toast(getString(R.string.changeSuccessfully))
                                finish()
                            } else {
                                toast("${getString(R.string.error)}:$e")
                            }
                        }

                    })

        }

        //邮箱找回密码
        btn_getPaswordFromEmail.onClick {
            if (et_eMail.text.isEmpty()){
                toast(getString(R.string.enterEmail))
                return@onClick
            }

            BmobUser.resetPasswordByEmail(et_eMail.text.toString().trim(),object :UpdateListener(){
                override fun done(e: BmobException?) {
                    if (null == e) {
                        toast("重置密码成功,请到${et_eMail.text.toString().trim()}邮箱进行密码重置操作!")
                        finish()
                    } else {
                        toast("${getString(R.string.error)}:$e")
                    }
                }

            })

        }
    }
}