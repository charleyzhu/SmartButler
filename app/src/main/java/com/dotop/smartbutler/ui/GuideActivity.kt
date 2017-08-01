package com.dotop.smartbutler.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.dotop.smartbutler.MainActivity
import com.dotop.smartbutler.R
import com.dotop.smartbutler.utils.UtilTools
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.pager_item_one.view.*
import kotlinx.android.synthetic.main.pager_item_three.*
import kotlinx.android.synthetic.main.pager_item_three.view.*
import kotlinx.android.synthetic.main.pager_item_two.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.ui
 * 文件名:         GuideActivity
 * 创建者:         Bugs
 * 创建时间:        2017/7/18上午1:20
 * 描述            新功能介绍
 */


class GuideActivity : AppCompatActivity() {

    lateinit var view1: View
    lateinit var view2: View
    lateinit var view3: View
    lateinit var mList: Array<View>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        intView()
        btnClicks()

    }

    private fun btnClicks() {

        btn_skip.onClick {
            val intent = Intent(this@GuideActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        view3.btn_enter.onClick {
            val intent = Intent(this@GuideActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun setPagePrint(check1:Boolean,check2:Boolean,check3:Boolean)
    {
        if (check1){
            iv_print1.backgroundResource = R.drawable.point_on
        }else {
            iv_print1.backgroundResource = R.drawable.point_off
        }

        if (check2){
            iv_print2.backgroundResource = R.drawable.point_on
        }else {
            iv_print2.backgroundResource = R.drawable.point_off
        }

        if (check3){
            iv_print3.backgroundResource = R.drawable.point_on
        }else {
            iv_print3.backgroundResource = R.drawable.point_off
        }
    }

    private fun intView() {
        view1 = View.inflate(this, R.layout.pager_item_one, null)
        view2 = View.inflate(this, R.layout.pager_item_two, null)
        view3 = View.inflate(this, R.layout.pager_item_three, null)

        UtilTools.setFont(this,view1.tv_one)
        UtilTools.setFont(this,view2.tv_two)
        UtilTools.setFont(this,view3.tv_three)

        mList = arrayOf(view1, view2, view3)

        //预加载
        vp_ViewPager.offscreenPageLimit = mList.size
        setPagePrint(true,false,false)

        vp_ViewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                //在第三个页面隐藏
                if (position == 2){
                    btn_skip.visibility = View.GONE
                }else{
                    btn_skip.visibility = View.VISIBLE
                }

                when (position) {
                    0-> setPagePrint(true,false,false)
                    1-> setPagePrint(false,true,false)
                    2-> setPagePrint(false,false,true)
                }
            }
        })

        vp_ViewPager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return mList.size
            }

            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                (container as ViewPager).addView(mList[position])
                return mList[position]
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                (container as ViewPager).removeView(mList[position])
            }
        }

    }


}