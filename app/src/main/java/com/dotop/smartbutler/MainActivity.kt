package com.dotop.smartbutler


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.dotop.smartbutler.R.string.*
import com.dotop.smartbutler.fragment.ButlerFragment
import com.dotop.smartbutler.fragment.GirlFragment
import com.dotop.smartbutler.fragment.UserFragment
import com.dotop.smartbutler.fragment.WechatFragment
import com.dotop.smartbutler.ui.SettingActivity
import com.dotop.smartbutler.utils.L
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    lateinit var mTitle: Array<String>
    lateinit var mFragment: Array<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //显示返回键
        supportActionBar?.elevation = 0F

        initData()
        initView()
    }


    fun initData() {
        mTitle = arrayOf(getString(tab_ServerButler),
                getString(tab_Wechat),
                getString(tab_girl),
                getString(tab_User))

        mFragment = arrayOf(ButlerFragment(),WechatFragment(),GirlFragment(),UserFragment())
    }

    fun initView() {


        //使用anko的点击方式
        fab_setting.onClick {
            val intnet = Intent(this@MainActivity,SettingActivity::class.java)
            startActivity(intnet)
        }
        fab_setting.visibility = View.GONE

        //预加载
        mViewPage.offscreenPageLimit = mFragment.size
//        //ViewPage的滑动监听
        mViewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    fab_setting.visibility = View.GONE
                }else {
                    fab_setting.visibility = View.VISIBLE
                }
            }

        })


        //设置适配器
        mViewPage.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            //选中的item
            override fun getItem(position: Int): Fragment {
                return mFragment[position]
            }
            //tab的总数
            override fun getCount(): Int {
                return mFragment.size
            }
            //tab的标题
            override fun getPageTitle(position: Int): CharSequence {
                return mTitle[position]
            }
        }
        //绑定tablayout绑定pageView
        mTabLayout.setupWithViewPager(mViewPage)
    }

}
