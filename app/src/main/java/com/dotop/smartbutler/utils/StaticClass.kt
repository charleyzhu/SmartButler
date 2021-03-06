package com.dotop.smartbutler.utils

import cn.bmob.v3.Bmob

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.utils
 * 文件名:         StaticClass
 * 创建者:         Bugs
 * 创建时间:        2017/7/12上午2:30
 * 描述            数据/常量
 */


object StaticClass {
    //延迟
    const val HANDLER_SPLASH = 1001
    //是否第一次运行
    const val SHARE_IS_FIRST = "isFirst"
    //buglyID
    const val BUGLY_APPID = "1190666b4a"
    const val BMOB_APPID = "9fca76f8b794026a1e47aec541b5b8b7"

    //是否保存密码
    const val isSavePassword = "isSavePassword"
    //用户名
    const val userName = "UserName"
    //密码
    const val passWord = "PassWord"
    //头像
    const val PROFILEKEY = "frofileImage"
    //聚合数据快递APPID
    const val JUHE_COURIER = "e0555462055ffb153b55ae34a1cbb7e8"
    //京东万象手机归属地查询
    const val jCloud_PhoneNumber = "9ee825aa5e8760d8585ce038e3cc41c0"
    //聚合数据，机器人问答
    const val JUHE_ROBOTKEY = "502359e9a9aef6a75bac003cabb08ba8"
    //聚合数据,微信精选
    const val JUHE_WECHAT = "c5cce6c6fafc7735d642e83ba4b6781d"
    //美女接口
    const val GIRL_INTERFACE = "http://gank.io/api/search/query/listview/category/%E7%A6%8F%E5%88%A9/count/50/page/1"
    //讯飞tts
    const val XUNFEI_TTS_KEY = "597af084"
    //短信action
    const val SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED"
    //更新接口
    const val APP_CEHCK_UPDATE_INTERFACE = "http://172.16.100.241/update.php"
}