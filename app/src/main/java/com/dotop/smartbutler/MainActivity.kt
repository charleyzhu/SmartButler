package com.dotop.smartbutler


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
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
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.dotop.smartbutler.R.id.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity() {

    lateinit var mTitle: Array<String>
    lateinit var mFragment: Array<Fragment>


    @RequiresApi(Build.VERSION_CODES.M)
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

        mFragment = arrayOf(ButlerFragment(), WechatFragment(), GirlFragment(), UserFragment())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun initView() {
        //请求悬浮窗权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            askForPermission()
        }


        //检查权限

        val perms: Array<String> = arrayOf(Manifest.permission.RECEIVE_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(this,"必须使用一下权限!",3001,*perms)
        }


//        var permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
//        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.BROADCAST_SMS,
//                    Manifest.permission.SEND_SMS, Manifest.permission.SYSTEM_ALERT_WINDOW), 2002)
//        }


        //使用anko的点击方式
        fab_setting.onClick {
            val intnet = Intent(this@MainActivity, SettingActivity::class.java)
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
                } else {
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askForPermission() {
        if (!Settings.canDrawOverlays(this)){
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
            startActivityForResult(intent, 4001)
        }else{
//            startService(floatWinIntent);
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        super.startActivityForResult(intent, requestCode, options)
        if (requestCode == 4001){
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                //启动FxService
//                startService(floatWinIntent);
            }
        }
    }

}
