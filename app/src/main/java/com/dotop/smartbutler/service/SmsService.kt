package com.dotop.smartbutler.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.net.Uri
import android.os.IBinder
import android.telephony.SmsMessage
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.utils.StaticClass
import com.dotop.smartbutler.view.DispatchLinearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.service
 * 文件名:         SmsService
 * 创建者:         Bugs
 * 创建时间:        2017/7/31上午9:44
 * 描述            设置页面
 */


class SmsService : Service() {

    lateinit var smsReceiver: SmsReceiver
    lateinit var sms_Phone: String
    lateinit var sms_Content: String
    //窗口管理
    var wm: WindowManager? = null
    //布局参数
    var layoutparams: WindowManager.LayoutParams? = null
    var mView: DispatchLinearLayout? = null

    //home 按键广播
    var homeWatchReceiver: HomeWatchReceiver? = null

    val SYSTEM_ACTION_CLOSE_RESON_KEY = "reason"
    val SYSTEM_ACTION_CLOSE_HOME_KEY = "homekey"

    override fun onBind(intent: Intent?): IBinder? {
        return null

    }

    override fun onCreate() {
        super.onCreate()
        init()
    }


    override fun onDestroy() {
        super.onDestroy()
        L.i("stop Sms Service")
        unregisterReceiver(smsReceiver)
        unregisterReceiver(homeWatchReceiver)
    }

    private fun init() {
        L.i("init Sms Service")
        smsReceiver = SmsReceiver(this)
        val intent = IntentFilter()
        intent.addAction(StaticClass.SMS_ACTION)
        intent.priority = Int.MAX_VALUE
        registerReceiver(smsReceiver, intent)

        homeWatchReceiver = HomeWatchReceiver()
        val homeIntent = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        registerReceiver(homeWatchReceiver, homeIntent)


    }

    //短信广播
    inner class SmsReceiver(var service: SmsService) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            L.i("action:${intent?.action}")
            intent ?: return
            when (intent?.action) {
                StaticClass.SMS_ACTION -> {
                    L.i("收到短信")
                    val objs = intent.extras.get("pdus") as Array<ByteArray>
                    for (obj in objs) {
                        val sms = SmsMessage.createFromPdu(obj)
                        sms_Phone = sms.originatingAddress
                        sms_Content = sms.messageBody
                        L.i("收到短信: ${service.sms_Phone} : ${service.sms_Content}")
                        showWindow()
                    }
                }
            }

        }

        private fun showWindow() {
            //获取系统服务
            wm = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            //实例化布局参数
            layoutparams = WindowManager.LayoutParams()
            //定义宽高
            layoutparams?.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutparams?.height = WindowManager.LayoutParams.MATCH_PARENT
            //定义标记
            layoutparams?.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH

            //定义格式
            layoutparams?.format = PixelFormat.TRANSLUCENT
            layoutparams?.type = WindowManager.LayoutParams.TYPE_PHONE
            //加载布局
            mView = View.inflate(applicationContext, R.layout.sms_item, null) as DispatchLinearLayout

            val mTV_from = mView?.findViewById<View>(R.id.tv_from) as TextView
            mTV_from.text = sms_Phone

            val mTV_Context = mView?.findViewById<View>(R.id.tv_Context) as TextView
            mTV_Context.text = sms_Content

            val mBtn_send = mView?.findViewById<View>(R.id.btn_send) as Button
            mBtn_send.onClick {
                val uri = Uri.parse("smsto:$sms_Phone")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                //设置启动模式
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("sms_body", "")
                startActivity(intent)
                wm?.removeView(mView)
            }


            //添加View到窗口
            wm?.addView(mView, layoutparams)

            mView?.dispatchKeyEventListener = object : DispatchLinearLayout.DispatchKeyEventListener {
                override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                    if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mView?.parent != null) {
                            wm?.removeView(mView)
                            return true
                        }
                    }
                    return false
                }

            }

        }


    }

    //监听home按键的广播
    inner class HomeWatchReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
                var reason = intent.getStringExtra(SYSTEM_ACTION_CLOSE_RESON_KEY)
                if (reason == SYSTEM_ACTION_CLOSE_HOME_KEY) {
                    if (mView?.parent != null) {
                        wm?.removeView(mView)
                    }
                }
            }
        }

    }

}