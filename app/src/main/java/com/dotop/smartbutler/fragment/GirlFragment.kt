package com.dotop.smartbutler.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dotop.smartbutler.R

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.fragment
 * 文件名:         GirlFragment
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午12:26
 * 描述            TODO
 */


class GirlFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val girlView = inflater?.inflate(R.layout.fragment_girl,null)
        return girlView
    }
}