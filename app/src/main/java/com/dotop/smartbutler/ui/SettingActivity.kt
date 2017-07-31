package com.dotop.smartbutler.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.dotop.smartbutler.R
import com.dotop.smartbutler.service.SmsService
import com.dotop.smartbutler.utils.ShareUtils
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         SettingActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午2:09
 * 描述            设置界面
 */


class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setupViews()
        setupClicks()
    }


    private fun setupViews() {

        val isSpeek = ShareUtils.get(this@SettingActivity, "isSpeek", false) as Boolean
        sw_tts.isChecked = isSpeek

        val isSms = ShareUtils.get(this@SettingActivity, "isSms", false) as Boolean
        sw_sms.isChecked = isSms



    }

    private fun setupClicks() {
        val mContext = this
        sw_tts.onCheckedChange { buttonView, isChecked ->
            ShareUtils.put(mContext, "isSpeek", sw_tts.isChecked)
        }

        sw_sms.onCheckedChange { buttonView, isChecked ->
            ShareUtils.put(mContext, "isSms", sw_sms.isChecked)

            if (isChecked) {
                startService(Intent(mContext,SmsService::class.java))
            } else {
                stopService(Intent(mContext,SmsService::class.java))
            }

        }

    }
}