package com.dotop.smartbutler.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.adapter.WechatListAdapter
import com.dotop.smartbutler.entity.WechatListData
import com.dotop.smartbutler.ui.weChatDetailActivity
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.utils.StaticClass
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import com.kymjs.rxvolley.http.VolleyError
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.fragment
 * 文件名:         WechatFragment
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午12:25
 * 描述            TODO
 */


class WechatFragment : Fragment() {

    lateinit var mListView: ListView
    val mList: MutableList<WechatListData> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val wechatView = inflater?.inflate(R.layout.fragment_wechat, null)

        setupView(wechatView!!)


        return wechatView
    }

    private fun setupView(wechatView: View) {
        mListView = wechatView.findViewById(R.id.mListView)

        mListView.onItemClick { parent, view, position, id ->
            val wechatData = mList[position]
            L.d(mList[position].toString())
            val intent = Intent(activity,weChatDetailActivity::class.java)
            intent.putExtra("url",wechatData.url)
            startActivity(intent)
        }

        val wechatUrl = "http://v.juhe.cn/weixin/query?key=${StaticClass.JUHE_WECHAT}"
        RxVolley.get(wechatUrl, object : HttpCallback() {
            override fun onSuccess(t: String?) {
                parsingJsong(t!!)
            }

            override fun onFailure(error: VolleyError?) {
                toast("请求微信数据发生错误:$error")
            }
        })
    }

    private fun parsingJsong(t: String) {
        val webResult = JSONObject(t)
        val error_code = webResult.getInt("error_code")
        if (error_code != 0) {
            toast("error_code错误:$error_code")
            return
        }
        val result = webResult.getJSONObject("result")
        val itemList = result.getJSONArray("list")

        for (i in 0..itemList.length()-1) {
            val item = itemList[i] as JSONObject
            val wechatData = WechatListData(item.getString("title"), item.getString("source"), item.getString("firstImg"), item.getString("url"))
            mList.add(wechatData)
        }

        val adapter = WechatListAdapter(activity,mList)
        mListView.adapter = adapter

    }
}