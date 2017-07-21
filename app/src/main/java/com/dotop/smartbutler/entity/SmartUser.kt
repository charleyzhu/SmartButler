package com.dotop.smartbutler.entity

import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser

/**
 * Created by charley on 2017/7/21.
 */
class SmartUser(var mUsername: String,
                var mPassword: String,
                var mEmail: String = "",
                var mAge: String = "",
                var mDesc: String = "",
                var mSex: Boolean = true) : BmobUser() {
    init {
        this.username = mUsername
        this.setPassword(mPassword)
        this.email = mEmail

    }
}