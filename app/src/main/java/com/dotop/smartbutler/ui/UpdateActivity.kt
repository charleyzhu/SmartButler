package com.dotop.smartbutler.ui

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.dotop.smartbutler.R
import com.dotop.smartbutler.utils.L
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import com.kymjs.rxvolley.client.ProgressListener
import com.kymjs.rxvolley.http.VolleyError
import com.kymjs.rxvolley.toolbox.FileUtils
import kotlinx.android.synthetic.main.activity_update.*
import org.jetbrains.anko.toast
import android.R.attr.path
import android.content.Intent
import android.net.Uri
import java.io.File


/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         UpdateActivity
 * 创建者:         Bugs
 * 创建时间:        2017/8/1下午2:53
 * 描述            更新界面
 */


class UpdateActivity : BaseActivity() {
    var updateUrl: String = ""
    val HANDLE_LOGIN = 10001
    val HANDLE_OK = 10002
    val HANDLE_NO = 10003
    var path:String? = ""

    val handle = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                HANDLE_LOGIN -> {
                    val transferredBytes = msg!!.data.get("transferredBytes") as Long
                    val totalSize = msg!!.data.get("totalSize") as Long
                    tv_size.text = "$transferredBytes/$totalSize"
//
                    val progress = ((transferredBytes.toFloat() / totalSize.toFloat())*100).toInt()
                    number_progress_bar.progress = progress

                }
                HANDLE_OK -> {
                    L.i("下载完成")
                    startInstallApk()
                }
                HANDLE_NO -> {
                    toast("更新失败!")
                    L.i("下载失败")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        setupView()
        SoftUdpate()
    }

    private fun SoftUdpate() {

        path = "${FileUtils.getSDCardPath()}/${System.currentTimeMillis()}.apk"
        RxVolley.download(path, updateUrl, { transferredBytes, totalSize ->

            //            tv_size.text = "$transferredBytes/$totalSize"
            var msg = Message()
            msg.what = HANDLE_LOGIN
            val bundel = Bundle()
            bundel.putLong("transferredBytes", transferredBytes)
            bundel.putLong("totalSize", totalSize)
            msg.data = bundel

            handle.sendMessage(msg)

        }, object : HttpCallback() {
            override fun onSuccess(t: String?) {
                super.onSuccess(t)
                handle.sendEmptyMessage(HANDLE_OK)
            }

            override fun onFailure(error: VolleyError?) {
                super.onFailure(error)
                handle.sendEmptyMessage(HANDLE_NO)
            }
        })
    }
    //启动安装
    private fun startInstallApk() {
        val i = Intent()
        i.action = Intent.ACTION_VIEW
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.setDataAndType(Uri.fromFile( File(path)), "application/vnd.android.package-archive");
        startActivity(i)
        finish()
    }
    private fun setupView() {
        updateUrl = intent.getStringExtra("url")
        number_progress_bar.max = 100
    }
}