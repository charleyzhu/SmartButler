package com.dotop.smartbutler.application

import android.app.Application
import android.os.Build
import android.os.StrictMode
import com.dotop.smartbutler.utils.StaticClass
import com.tencent.bugly.crashreport.CrashReport
import cn.bmob.v3.Bmob
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.application
 * 文件名:         BaseApplicaion
 * 创建者:         Bugs
 * 创建时间:        2017/7/12上午1:46
 * 描述            Application 基类
 */
class BaseApplicaion:Application() {

    //创建
    override fun onCreate() {
        super.onCreate()
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(),StaticClass.BUGLY_APPID, false)
        //初始化bmob
        Bmob.initialize(this, StaticClass.BMOB_APPID)
        SpeechUtility.createUtility(applicationContext, SpeechConstant.APPID +"=${StaticClass.XUNFEI_TTS_KEY}")

    }
}