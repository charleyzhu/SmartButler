package com.dotop.smartbutler.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.adapter.ChatListAdapter
import com.dotop.smartbutler.entity.ChatListData
import kotlinx.android.synthetic.main.fragment_butler.*
import org.jetbrains.anko.sdk25.coroutines.onClick

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
    }

    private fun setupClicks() {
        mBtn_send.onClick {
            addListViewItem(mEt_send.text.toString(),2)
            mEt_send.setText("".toCharArray(), 0, 0)
        }
//        mBtn_Left.onClick {
//            addListViewItem("左边", 1)
//        }
//        mBtn_Right.onClick {
//            addListViewItem("右边", 2)
//        }
    }

    //添加ListItem
    private fun addListViewItem(text: String, type: Int) {
        val itemData = ChatListData(text, type)
        mList.add(itemData)
        chatAdapter.notifyDataSetChanged()
        mChatListView.setSelection(mChatListView.bottom)
    }

}