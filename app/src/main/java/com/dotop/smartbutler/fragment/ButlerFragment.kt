package com.dotop.smartbutler.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import com.dotop.smartbutler.R
import com.dotop.smartbutler.adapter.ChatListAdapter
import com.dotop.smartbutler.entity.ChatListData
import com.dotop.smartbutler.entity.SmartUser
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.utils.StaticClass
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import com.kymjs.rxvolley.http.VolleyError
import kotlinx.android.synthetic.main.fragment_butler.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onKey
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.fragment
 * 文件名:         ButlerFragment
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午12:25
 * 描述            智能管家
 */


class ButlerFragment : Fragment() {
    var mList: MutableList<ChatListData> = mutableListOf()
    lateinit var chatAdapter: ChatListAdapter
    lateinit var mChatListView: ListView

    lateinit var mEt_send: EditText
    lateinit var mBtn_send: Button

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val butlerView = inflater?.inflate(R.layout.fragment_butler, null)


        setupUI(butlerView!!)
        setupClicks()

        addListViewItem(getString(R.string.hello), 1)

        return butlerView
    }

    private fun setupUI(butlerView: View) {
        mChatListView = butlerView.findViewById(R.id.lv_chatListView)

        mEt_send = butlerView.findViewById(R.id.et_send)
        mBtn_send = butlerView.findViewById(R.id.btn_send)

        chatAdapter = ChatListAdapter(activity, mList)
        mChatListView.adapter = chatAdapter

        mEt_send.onKey { v, keyCode, event ->

            if (mEt_send.text.isEmpty() || (mEt_send.text.length == 1 && mEt_send.text.toString() == "\n")) {
                return@onKey
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
                addListViewItem(mEt_send.text.toString().trim(), 2)


                if (mEt_send.text.length > 30) {
                    toast("没有必要说这么长吧？")
                    return@onKey
                }

                requetRobot(mEt_send.text.toString().trim())
                mEt_send.setText("".toCharArray(), 0, 0)
            }
        }
    }

    private fun setupClicks() {
        mBtn_send.onClick {
            addListViewItem(mEt_send.text.toString().trim(), 2)


            if (mEt_send.text.length > 30) {
                toast("没有必要说这么长吧？")
                return@onClick
            }

            requetRobot(mEt_send.text.toString().trim())
            mEt_send.setText("".toCharArray(), 0, 0)
        }
    }

    //添加ListItem
    private fun addListViewItem(text: String, type: Int) {
        val itemData = ChatListData(text, type)
        mList.add(itemData)
        chatAdapter.notifyDataSetChanged()
        mChatListView.setSelection(mChatListView.bottom)
    }

    private fun requetRobot(msg: String) {


        val user = java.net.URLEncoder.encode(BmobUser.getCurrentUser(SmartUser::class.java).username, "utf-8")
        val robotUrl = "http://op.juhe.cn/robot/index?info=$msg&loc=&userid=$user&key=${StaticClass.JUHE_ROBOTKEY}"
        RxVolley.get(robotUrl, object : HttpCallback() {
            override fun onSuccess(t: String?) {
                val webResult = JSONObject(t)
                val code = webResult.getInt("error_code")
                if (code != 0) {
                    val msg = webResult.getString("reason")
                    toast("机器人数据错误:$msg code:$code")
                    return
                }

                val result = webResult.getJSONObject("result")
                val text = result.getString("text")
                addListViewItem(text, 1)
            }

            override fun onFailure(error: VolleyError?) {
                L.e(error.toString())
                toast("机器人也有出错的时候:$error")
            }
        })
    }

}