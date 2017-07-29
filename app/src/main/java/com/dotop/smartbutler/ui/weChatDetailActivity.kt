package com.dotop.smartbutler.ui

import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import com.dotop.smartbutler.R
import com.tencent.bugly.proguard.m
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

        wv_webview.webChromeClient = ChromeWebViewClient(mProgressBar)


        wv_webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return false
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return shouldOverrideUrlLoading(view,request?.url.toString())
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                toast("ReceivedError:$error")
            }
        }
        wv_webview.loadUrl(intent.getStringExtra("url"))
        supportActionBar?.title = intent.getStringExtra("title")


    }

    class ChromeWebViewClient(var mProgressBar:ProgressBar ) : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress == 100){
                mProgressBar.visibility = View.GONE
            }
            super.onProgressChanged(view, newProgress)
        }

    }
}