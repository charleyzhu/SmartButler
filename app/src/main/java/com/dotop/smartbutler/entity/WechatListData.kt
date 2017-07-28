package com.dotop.smartbutler.entity

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.entity
 * 文件名:         WechatListData
 * 创建者:         Bugs
 * 创建时间:        2017/7/28下午5:49
 * 描述            微信精选数据类
 */


class WechatListData (var title:String,var source:String,var firstImg:String,var url:String){
    override fun toString(): String {
        return "title:$title,source:$source,firstImg:$firstImg,url:$url"
    }
}