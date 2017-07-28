package com.dotop.smartbutler.ui

import android.os.Bundle
import android.webkit.*
import com.dotop.smartbutler.R
import kotlinx.android.synthetic.main.activity_wechat_detail.*
import kotlinx.android.synthetic.main.activity_wechat_detail.view.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.webView

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         weChatDetailActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/29上午1:14
 * 描述            微信点击详情
 */


class weChatDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wechat_detail)

        wv_webview.settings.javaScriptEnabled = true



        wv_webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                toast("ReceivedError:$error")
            }
        }
        wv_webview.loadUrl(intent.getStringExtra("url"))


    }
}