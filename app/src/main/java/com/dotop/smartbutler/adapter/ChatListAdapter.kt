package com.dotop.smartbutler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.ChatListData

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.adapter
 * 文件名:         ChatListAdapter
 * 创建者:         Bugs
 * 创建时间:        2017/7/28上午10:17
 * 描述            聊天适配器
 */


class ChatListAdapter(var mContext: Context, var mList: MutableList<ChatListData>) : BaseAdapter() {

    val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var viewHolder:ChatViewHolder


    override fun getView(position: Int, convert: View?, viewGroup: ViewGroup?): View {
        var convertView = convert

        val CHATTYPE_LEFT_TEXT = 1
        val CHATTYPE_RIGHT_TEXT = 2
        val type = getItemViewType(position)
        if (convertView == null) {

            when (type) {

                CHATTYPE_LEFT_TEXT -> {
                    convertView = inflater.inflate(R.layout.chatlist_left_item,null)
                    val tv_message = convertView!!.findViewById<View>(R.id.tv_chatList_leftText) as TextView
                    viewHolder = ChatViewHolder(tv_message)
                    convertView!!.tag = viewHolder

                }
                CHATTYPE_RIGHT_TEXT -> {
                    convertView = inflater.inflate(R.layout.chatlist_right_item,null)
                    val tv_message = convertView!!.findViewById<View>(R.id.tv_chatList_RightText) as TextView
                    viewHolder = ChatViewHolder(tv_message)
                    convertView!!.tag = viewHolder
                }
            }
        } else {
            viewHolder = convertView!!.tag as ChatViewHolder
        }
        val chatData = mList[position]
        viewHolder.tv_messageView.setText(chatData.mMsg.toCharArray(),0,chatData.mMsg.length)
        return convertView!!
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        val charData = mList[position]
        val type = charData.mType
        return type
    }

    override fun getViewTypeCount(): Int {
        return 3
    }

    class ChatViewHolder(var tv_messageView: TextView)
}

