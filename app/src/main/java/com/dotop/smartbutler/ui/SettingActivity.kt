package com.dotop.smartbutler.ui


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.dotop.smartbutler.R
import com.dotop.smartbutler.service.SmsService
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.utils.ShareUtils
import com.dotop.smartbutler.utils.StaticClass
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         SettingActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午2:09
 * 描述            设置界面
 */


class SettingActivity : BaseActivity() {

    var versionCode: Int? = null
    var versionName: String? = null

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

        getAppVersionCode()

        tv_version.text = "软件版本:$versionName"


    }

    private fun setupClicks() {
        val mContext = this
        sw_tts.onCheckedChange { buttonView, isChecked ->
            ShareUtils.put(mContext, "isSpeek", sw_tts.isChecked)
        }

        sw_sms.onCheckedChange { buttonView, isChecked ->
            ShareUtils.put(mContext, "isSms", sw_sms.isChecked)

            if (isChecked) {
                startService(Intent(mContext, SmsService::class.java))
            } else {
                stopService(Intent(mContext, SmsService::class.java))
            }

        }

        ll_update.onClick {
            RxVolley.get(StaticClass.APP_CEHCK_UPDATE_INTERFACE, object : HttpCallback() {
                override fun onSuccess(t: String?) {
                    super.onSuccess(t)
                    parsingJson(t)
                }
            })
        }

    }

    private fun parsingJson(t: String?) {
        val result = JSONObject(t)
        val code = result.getInt("versionCode")
        val content = result.getString("versionName")
        val url = result.getString("url")
        if (code > versionCode!!) {
            showUpdateDialog(content,url)
        } else {
            toast("您的版本是最新版本，不需要更新!")
        }

    }

    private fun showUpdateDialog(content: String,url:String) {
        AlertDialog.Builder(this)
                .setTitle("有新版本啦!")
                .setMessage(content)
                .setPositiveButton("更新", { dialog, which ->
                    val intnet = Intent(this,UpdateActivity::class.java)
                    intnet.putExtra("url",url)
                    startActivity(intnet)
                    dialog.dismiss()
                }).setNegativeButton("取消", { dialog, which ->
            dialog.dismiss()
        }).show()
    }


    private fun getAppVersionCode() {

        val info = packageManager.getPackageInfo(packageName, 0)
        versionCode = info.versionCode
        versionName = info.versionName
    }


}