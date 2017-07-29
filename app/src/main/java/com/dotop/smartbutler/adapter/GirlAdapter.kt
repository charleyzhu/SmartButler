package com.dotop.smartbutler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.ImageView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.GirlData
import com.squareup.picasso.Picasso
import com.tencent.bugly.proguard.m
import com.tencent.bugly.proguard.w

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.adapter
 * 文件名:         GirlAdapter
 * 创建者:         Bugs
 * 创建时间:        2017/7/29上午11:20
 * 描述            美女社区适配器
 */


class GirlAdapter(var mContext: Context, var mList: MutableList<GirlData>) : BaseAdapter() {

    val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var viewholder:GirlViewHolder
    var width:Int = 0

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        width = wm.defaultDisplay.width

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.girl_item, null)
            viewholder = GirlViewHolder(convertView!!.findViewById(R.id.iv_girl))
            convertView!!.tag = viewholder
        }else {
            viewholder = convertView!!.tag as GirlViewHolder
        }

        val girlData = mList[position]
        Picasso.with(mContext).load(girlData.url).resize(width /2, width /2).centerCrop().into(viewholder.iv_img)
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

    class GirlViewHolder(val iv_img:ImageView)

}