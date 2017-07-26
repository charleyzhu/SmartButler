package com.dotop.smartbutler.ui

import android.os.Bundle
import com.dotop.smartbutler.R
import com.dotop.smartbutler.adapter.CourierAdapter
import com.dotop.smartbutler.entity.CourierData
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.utils.StaticClass
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import kotlinx.android.synthetic.main.activity_courier.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*


/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         CourierActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/26下午3:09
 * 描述            TODO
 */


class CourierActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier)

        btnClicks()
    }

    private fun btnClicks() {
        var tt =btn_query
        btn_query.onClick {
            if (et_Company.text.isEmpty()) {
                toast("请输入快递公司!")
                return@onClick
            } else if (et_Number.text.isEmpty()) {
                toast("开玩笑吧，没单号怎么查？")
                return@onClick
            }


            RxVolley.get("http://v.juhe.cn/exp/index?key=${StaticClass.JUHE_COURIER}&com=${et_Company.text}&no=${et_Number.text}", object : HttpCallback() {
                override fun onSuccess(t: String?) {
                    L.i("Get Company:${et_Company.text} Num:${et_Number.text} \nData:$t")
                    parsingJson(t)
                }
            })
        }
    }

    private fun parsingJson(t: String?) {
        val resultObject = JSONObject(t)
        val resultcode = resultObject.getString("resultcode")
        if (resultcode != "200")return
        val result = resultObject.getJSONObject("result")
        val dataArray = result.getJSONArray("list")


        var mList:MutableList<CourierData> = mutableListOf()

        for (i in 0..dataArray.length()-1) {
            val itemObject = dataArray.get(i) as JSONObject
            val courierData = CourierData(itemObject.getString("datetime"), itemObject.getString("remark"), itemObject.getString("zone"))
            mList.add(courierData)
        }
        //倒序处理

        Collections.reverse(mList)
        lv_rows.adapter = CourierAdapter(this,mList)


    }


}


