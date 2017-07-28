package com.dotop.smartbutler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.WechatListData

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.adapter
 * 文件名:         WechatListAdapter
 * 创建者:         Bugs
 * 创建时间:        2017/7/28下午5:51
 * 描述            微信精选适配器
 */


class WechatListAdapter(var mContext: Context, var mList: MutableList<WechatListData>) : BaseAdapter() {

    val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var viewHolder: WechatViewHolder

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_wechat_item, null)
            viewHolder = WechatViewHolder(convertView!!.findViewById(R.id.iv_img),
                    convertView!!.findViewById(R.id.tv_title), convertView!!.findViewById(R.id.tv_source))
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView!!.tag as WechatViewHolder
        }
        val weChatData = mList[position]

        viewHolder.tv_title.setText(weChatData.title.toCharArray(), 0, weChatData.title.length)
        viewHolder.tv_source.setText(weChatData.source.toCharArray(), 0, weChatData.source.length)

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

    class WechatViewHolder(var iv_img: ImageView, var tv_title: TextView, var tv_source: TextView)

}

