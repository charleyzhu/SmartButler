package com.dotop.smartbutler.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.util.Base64
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.utils
 * 文件名:         UtilTools
 * 创建者:         Bugs
 * 创建时间:        2017/7/12上午2:29
 * 描述            工具类
 */


object UtilTools {
    //设置字体
    fun setFont(mContext: Context, tv: TextView) {
        val typeFace = Typeface.createFromAsset(mContext.assets, "Fonts/FONT.TTF")
        tv.setTypeface(typeFace)
    }

    fun pubImageToShare(mContext: Context, mKey: String, mBitMap: Bitmap) {
        val bitMapBase64 = BitMapToBase64(mBitMap)
        ShareUtils.put(mContext, mKey, bitMapBase64)
    }

    fun getImageFromShare(mContext: Context, mKey: String): Bitmap? {
        val bitMapBase64 = ShareUtils.get(mContext,mKey,"") as String
        if (bitMapBase64 == "") return null
        return Base64ToBitMap(bitMapBase64)

    }

    fun BitMapToBase64(mBitMap: Bitmap): String {
        var result: String = ""
        try {
            val baos = ByteArrayOutputStream()
            mBitMap.compress(Bitmap.CompressFormat.PNG, 0, baos)
            baos.flush()
            baos.close()

            val bitMapBytes = baos.toByteArray()
            result = Base64.encodeToString(bitMapBytes, Base64.DEFAULT)


        } catch (e: IOException) {

        }
        return result
    }

    fun Base64ToBitMap(mBase64: String): Bitmap {
        val bitMapBytes = Base64.decode(mBase64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bitMapBytes, 0, bitMapBytes.size)
    }

}