package com.dotop.smartbutler.ui

import android.os.Bundle
import android.text.TextWatcher
import com.dotop.smartbutler.R
import com.dotop.smartbutler.utils.StaticClass
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import com.kymjs.rxvolley.http.VolleyError
import kotlinx.android.synthetic.main.activity_phone.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         PhoneActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/27下午2:46
 * 描述            TODO
 */


class PhoneActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        //点击事件
        setupClicks()
    }

    private fun setupClicks() {

        btn_del.onClick {
            tv_phoneNumber.DelectLast()
        }
        btn_del.onLongClick {
            tv_phoneNumber.Clean()
        }
        btn_1.onClick {
            tv_phoneNumber.AddNumber("1")
        }
        btn_2.onClick {
            tv_phoneNumber.AddNumber("2")
        }
        btn_3.onClick {
            tv_phoneNumber.AddNumber("3")
        }
        btn_4.onClick {
            tv_phoneNumber.AddNumber("4")
        }
        btn_5.onClick {
            tv_phoneNumber.AddNumber("5")
        }
        btn_6.onClick {
            tv_phoneNumber.AddNumber("6")
        }
        btn_7.onClick {
            tv_phoneNumber.AddNumber("7")
        }
        btn_8.onClick {
            tv_phoneNumber.AddNumber("8")
        }
        btn_9.onClick {
            tv_phoneNumber.AddNumber("9")
        }
        btn_0.onClick {
            tv_phoneNumber.AddNumber("0")
        }

        btn_phone_query.onClick {
            if (tv_phoneNumber.text.isEmpty()) {
                toast("没号码怎么查？")
                return@onClick
            }
            val url = "https://way.jd.com/jisuapi/query4?shouji=${tv_phoneNumber.text}&appkey=${StaticClass.jCloud_PhoneNumber}"
            RxVolley.get(url, object : HttpCallback() {
                override fun onSuccess(t: String?) {
                    val webResult = JSONObject(t)
                    val code = webResult.get("code")

                    if (code != "10000") {
                        toast("错误,code:$code")
                        return
                    }

                    val result = webResult.getJSONObject("result")
                    val status = result.getString("status")
                    if (status != "0") {
                        toast("错误,status:$status")
                        return
                    }

                    val infoResult = result.getJSONObject("result")

                    val province = infoResult.getString("province")
                    val city = infoResult.getString("city")
                    val company = infoResult.getString("company")
                    val areacode = infoResult.getString("areacode")

                    val InfoStr = "$province $city ($areacode)"
                    tv_PhoneInfo.setText(InfoStr.toCharArray(), 0, InfoStr.length)

                    if (company.indexOf("移动") > 0) {
                        iv_company.setImageResource(R.drawable.china_mobile)
                    }else if (company.indexOf("电信") > 0) {
                        iv_company.setImageResource(R.drawable.china_telecom)
                    }else if (company.indexOf("联通") > 0) {
                        iv_company.setImageResource(R.drawable.china_unicom)
                    }
                    tv_phoneNumber.isCleanPhoneNum = true

                }

                override fun onFailure(error: VolleyError?) {
                    toast("查询时候发生错误:$error")
                }

            })
        }


    }


}