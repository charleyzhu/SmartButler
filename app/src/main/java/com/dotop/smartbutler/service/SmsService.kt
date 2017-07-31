package com.dotop.smartbutler.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.SmsMessage
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.utils.StaticClass
import java.util.*

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.service
 * 文件名:         SmsService
 * 创建者:         Bugs
 * 创建时间:        2017/7/31上午9:44
 * 描述            TODO
 */


class SmsService : Service() {

    lateinit var smsReceiver: SmsReceiver
    lateinit var sms_Phone:String
    lateinit var sms_Content:String

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
    }

    private fun init() {
        L.i("init Sms Service")
        smsReceiver = SmsReceiver(this)
        val intent = IntentFilter()
        intent.addAction(StaticClass.SMS_ACTION)
        intent.priority = Int.MAX_VALUE
        registerReceiver(smsReceiver, intent)
    }

    //短信广播
    class SmsReceiver(var service :SmsService) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            when (intent?.action) {
                StaticClass.SMS_ACTION -> {
                    L.i("收到短信")
                    val objs = intent.extras.get("pdus") as Array<ByteArray>
                    for (obj in objs){
                        val sms = SmsMessage.createFromPdu(obj)
                        service.sms_Phone = sms.originatingAddress
                        service.sms_Content = sms.messageBody
                        L.i("收到短信: ${service.sms_Phone} : ${service.sms_Content}")
                        showWindow()
                    }
                }
            }

        }

        private fun showWindow() {

        }

    }

}