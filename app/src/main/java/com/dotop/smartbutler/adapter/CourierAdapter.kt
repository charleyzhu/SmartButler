package com.dotop.smartbutler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.CourierData

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.adapter
 * 文件名:         CourierAdapter
 * 创建者:         Bugs
 * 创建时间:        2017/7/26下午5:39
 * 描述            TODO
 */


class CourierAdapter(var mContext: Context, var mList: MutableList<CourierData>) : BaseAdapter() {
    lateinit var inflater: LayoutInflater
    lateinit var viewHolder: ViewHolder

    init {

        inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convert: View?, parent: ViewGroup?): View {
        var convertView = convert
        //如果item的convertView为空的话说明这个item没有载入过，开始初始化
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_courier_item, null)

            viewHolder = ViewHolder(convertView!!.findViewById<View>(R.id.tv_remark) as TextView,
                    convertView!!.findViewById<View>(R.id.tv_zone) as TextView,
                    convertView!!.findViewById<View>(R.id.tv_datetime) as TextView)

            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView!!.tag as ViewHolder
        }

        val data = mList[position]
        viewHolder.tv_remark.setText(data.remark.toCharArray(), 0, data.remark.length)
        viewHolder.tv_zone.setText(data.zone.toCharArray(), 0, data.zone.length)
        viewHolder.tv_datetime.setText(data.datetime.toCharArray(), 0, data.datetime.length)

        return convertView

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
}

class ViewHolder(var tv_remark: TextView, var tv_zone: TextView, var tv_datetime: TextView)