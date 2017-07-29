package com.dotop.smartbutler.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import com.dotop.smartbutler.R
import com.dotop.smartbutler.adapter.GirlAdapter
import com.dotop.smartbutler.entity.GirlData
import com.dotop.smartbutler.utils.StaticClass
import com.dotop.smartbutler.view.CustomDialog
import com.github.chrisbanes.photoview.PhotoView
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import com.kymjs.rxvolley.http.VolleyError
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.fragment
 * 文件名:         GirlFragment
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午12:26
 * 描述            美女社区
 */


class GirlFragment : Fragment() {

    val mList: MutableList<GirlData> = mutableListOf()
    lateinit var mGirl_GridView: GridView
    lateinit var dialog:CustomDialog
    lateinit var mAttacher:PhotoViewAttacher
    lateinit var grilImageView:PhotoView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val girlView = inflater?.inflate(R.layout.fragment_girl, null)

        setupView(girlView)

        return girlView
    }

    private fun setupView(girlView: View?) {

        mGirl_GridView = girlView!!.findViewById(R.id.mGridView)
        dialog = CustomDialog(getActivity(), 0, 0, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style)
        grilImageView = dialog.findViewById(R.id.iv_girlImageView)

        mGirl_GridView.onItemClick { parent, view, position, id ->
            Picasso.with(activity).load(mList[position].url).into(grilImageView)
//            mAttacher = PhotoViewAttacher(grilImageView)
//            mAttacher.update()
            dialog.show()
        }


        RxVolley.get(StaticClass.GIRL_INTERFACE, object : HttpCallback() {
            override fun onSuccess(t: String?) {
                parsingJsong(t)
            }

            override fun onFailure(error: VolleyError?) {
                toast("请求美女数据发生错误:$error")
            }
        })
    }

    private fun parsingJsong(t: String?) {
        val webResult = JSONObject(t)
        val isError = webResult.getBoolean("error")
        if (isError) {
            toast("请求美女数据发生错误!")
            return
        }

        val results = webResult.getJSONArray("results")
        for (i in 0..results.length() - 1) {
            val item = results[i] as JSONObject
            val girdata = GirlData(item.getString("url"))
            mList.add(girdata)
        }

        val adapter = GirlAdapter(activity,mList)

        mGirl_GridView.adapter = adapter

    }
}