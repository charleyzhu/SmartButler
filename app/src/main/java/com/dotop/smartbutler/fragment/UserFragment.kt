package com.dotop.smartbutler.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.SmartUser
import com.dotop.smartbutler.ui.LoginActivity
import com.dotop.smartbutler.utils.L
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_user.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.fragment
 * 文件名:         UserFragment
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午12:26
 * 描述            个人中心
 */


class UserFragment : Fragment() {

    lateinit var exitBtn: Button
    lateinit var changeBtn: Button
    lateinit var commitBtn: Button

    lateinit var et_name: EditText
    lateinit var et_sex: EditText
    lateinit var et_age: EditText
    lateinit var et_desc: EditText


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val userView = inflater?.inflate(R.layout.fragment_user, null)

        initView(userView!!)
        changeInfoModel(false)
        btnClicks()

        return userView
    }

    private fun initView(view: View) {
        exitBtn = view.findViewById<View>(R.id.btn_exit_user) as Button
        changeBtn = view.findViewById<View>(R.id.btn_changeInfo) as Button
        commitBtn = view.findViewById<View>(R.id.btn_commitChange) as Button

        et_name = view.findViewById<View>(R.id.et_name) as EditText
        et_age = view.findViewById<View>(R.id.et_age) as EditText
        et_sex = view.findViewById<View>(R.id.et_sex) as EditText
        et_desc = view.findViewById<View>(R.id.et_desc) as EditText


        val su:SmartUser = BmobUser.getCurrentUser(SmartUser::class.java)
        et_name.setText(su.mUsername.toCharArray(),0,su.username.length)
        et_age.setText(su.mAge.toCharArray(),0,su.mAge.length)
        if (su.mSex){
            et_sex.setText("男")
        }else {
            et_sex.setText("女")
        }

        et_desc.setText(su.mDesc.toCharArray(),0,su.mDesc.length)

    }

    private fun changeInfoModel(isChange: Boolean) {
        et_name.isEnabled = isChange
        et_age.isEnabled = isChange
        et_sex.isEnabled = isChange
        et_desc.isEnabled = isChange
        if (isChange){
            commitBtn.visibility = View.VISIBLE
        }else {
            commitBtn.visibility = View.GONE
        }
    }

    private fun btnClicks() {
        //退出按钮点击
        exitBtn.onClick {
            BmobUser.logOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity.finish()
        }

        changeBtn.onClick {
            changeInfoModel(true)
        }

        commitBtn.onClick {
            val serverSu =  BmobUser.getCurrentUser(SmartUser::class.java)
            val su = SmartUser(et_name.text.toString(),serverSu.mPassword,mEmail = serverSu.email,mAge = et_age.text.toString(),mDesc = et_desc.text.toString())
            su.update(serverSu.objectId,object :UpdateListener(){
                override fun done(e: BmobException?) {
                    if (null == e){
                        toast("更新资料成功")
                        changeInfoModel(false)
                    }else{
                        toast("更新资料失败:$e")
                    }
                }

            })
        }


    }

}