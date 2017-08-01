package com.dotop.smartbutler.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.EditText
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.dotop.smartbutler.R
import com.dotop.smartbutler.entity.SmartUser
import com.dotop.smartbutler.ui.LoginActivity
import com.dotop.smartbutler.utils.L
import com.dotop.smartbutler.view.CustomDialog
import de.hdodenhof.circleimageview.CircleImageView

import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import java.io.File
import android.graphics.Bitmap

import android.support.v4.content.FileProvider
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.dotop.smartbutler.ui.CourierActivity
import com.dotop.smartbutler.ui.PhoneActivity
import com.dotop.smartbutler.utils.ShareUtils
import com.dotop.smartbutler.utils.StaticClass
import com.dotop.smartbutler.utils.UtilTools
import com.yalantis.ucrop.UCrop
import org.jetbrains.anko.support.v4.act
import java.net.URI


/**
 * 项目名称:       SmartButler
 * 包名:           com.dotop.smartbutler.fragment
 * 文件名:         UserFragment
 * 创建者:         Bugs
 * 创建时间:        2017/7/15上午12:26
 * 描述            个人中心
 */


class UserFragment : Fragment() {

    //退出登录按钮
    lateinit var exitBtn: Button
    //    修改资料
    lateinit var changeBtn: Button
    //    保存修改
    lateinit var commitBtn: Button

    //    名字
    lateinit var et_name: EditText
    //   性别
    lateinit var et_sex: EditText
    //    年龄
    lateinit var et_age: EditText
    //    简介
    lateinit var et_desc: EditText

    //    头像
    lateinit var profileImage: CircleImageView
    //    头像选择对话框
    lateinit var dialog: CustomDialog

    //   对话框拍照按钮
    lateinit var dialog_camera: Button
    //    对话框图库按钮
    lateinit var dialog_photo: Button
    //    对话框取消按钮
    lateinit var dialog_cancel: Button

    //    sd卡路径
    lateinit var sdPath: String
    lateinit var picPath: String
    lateinit var tmpPicPath: String

    //拍照请求码
    val REQUEST_CAMERRA = 1001
    val REQUEST_IMAGE = 1002
    val REQUEST_UCROP = 1003


    //底部两个
    lateinit var textView_Courier: TextView
    lateinit var textView__AttributionQuery: TextView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val userView = inflater?.inflate(R.layout.fragment_user, null)

        initView(userView!!)
        changeInfoModel(false)
        btnClicks()

        return userView
    }

    private fun initView(view: View) {

        //检查权限
//        val permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2001)
//        }

        exitBtn = view.findViewById<View>(R.id.btn_exit_user) as Button
        changeBtn = view.findViewById<View>(R.id.btn_changeInfo) as Button
        commitBtn = view.findViewById<View>(R.id.btn_commitChange) as Button

        et_name = view.findViewById<View>(R.id.et_name) as EditText
        et_age = view.findViewById<View>(R.id.et_age) as EditText
        et_sex = view.findViewById<View>(R.id.et_sex) as EditText
        et_desc = view.findViewById<View>(R.id.et_desc) as EditText

        textView_Courier = view.findViewById<View>(R.id.tv_LogisticsQuery) as TextView
        textView__AttributionQuery = view.findViewById<View>(R.id.tv_AttributionQuery) as TextView



        profileImage = view.findViewById(R.id.profile_image)

        dialog = CustomDialog(activity, 0, 0, R.layout.dialog_profile_image, R.style.pop_anim_style, Gravity.BOTTOM, R.style.pop_anim_style)
        dialog.setCancelable(false)

        dialog_camera = dialog.findViewById(R.id.btn_camera)
        dialog_photo = dialog.findViewById(R.id.btn_photo)
        dialog_cancel = dialog.findViewById(R.id.btn_cancel)

        sdPath = Environment.getExternalStorageDirectory().path
        picPath = sdPath + "/cropPic.png"
        tmpPicPath = sdPath + "/temp.png"


        val su: SmartUser = BmobUser.getCurrentUser(SmartUser::class.java)
        et_name.setText(su.mUsername.toCharArray(), 0, su.username.length)
        et_age.setText(su.mAge.toCharArray(), 0, su.mAge.length)
        if (su.mSex) {
            et_sex.setText("男")
        } else {
            et_sex.setText("女")
        }

        et_desc.setText(su.mDesc.toCharArray(), 0, su.mDesc.length)

        val proFileBitmap = UtilTools.getImageFromShare(activity, StaticClass.PROFILEKEY)
        if (proFileBitmap != null) {
            profileImage.setImageBitmap(proFileBitmap)
        }

    }

    private fun changeInfoModel(isChange: Boolean) {
        et_name.isEnabled = isChange
        et_age.isEnabled = isChange
        et_sex.isEnabled = isChange
        et_desc.isEnabled = isChange
        if (isChange) {
            commitBtn.visibility = View.VISIBLE
        } else {
            commitBtn.visibility = View.GONE
        }
    }

    private fun btnClicks() {
        //退出按钮点击
        exitBtn.onClick {
            BmobUser.logOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity.finish()
        }

        changeBtn.onClick {
            changeInfoModel(true)
        }

        commitBtn.onClick {
            val serverSu = BmobUser.getCurrentUser(SmartUser::class.java)
            val su = SmartUser(et_name.text.toString(), serverSu.mPassword, mEmail = serverSu.email, mAge = et_age.text.toString(), mDesc = et_desc.text.toString())
            su.update(serverSu.objectId, object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (null == e) {
                        toast("更新资料成功")
                        changeInfoModel(false)
                    } else {
                        toast("更新资料失败:$e")
                    }
                }

            })
        }

        profileImage.onClick {
            dialog.show()
        }

        dialog_cancel.onClick {
            dialog.dismiss()
        }

        dialog_camera.onClick {
            dialog.dismiss()
            val intent_camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val tmpFile = File(picPath)
            val uri = FileProvider.getUriForFile(activity, "com.dotop.smartbutler.fileprovider", tmpFile)
            intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent_camera, REQUEST_CAMERRA)
        }

        dialog_photo.onClick {
            dialog.dismiss()


            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, REQUEST_IMAGE)
        }

        textView_Courier.onClick {
            startActivity(Intent(activity, CourierActivity::class.java))
        }
        textView__AttributionQuery.onClick {
            startActivity(Intent(activity, PhoneActivity::class.java))
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        L.d("requestCode:$requestCode resultCode:$resultCode")
        if (resultCode != 0) {
            when (requestCode) {

            //拍照回调
                REQUEST_CAMERRA -> {
                    val fileUrl = Uri.fromFile(File(tmpPicPath))
                    val outFileUil = Uri.fromFile(File(picPath))
                    val options = UCrop.Options()
                    options.setCompressionFormat(Bitmap.CompressFormat.PNG)
                    UCrop.of(fileUrl, outFileUil).withAspectRatio(1F, 1F).withMaxResultSize(320, 320).withOptions(options).start(activity, this@UserFragment)
                }
            //图库回调
                REQUEST_IMAGE -> {
                    val fileUrl = data?.data ?: return
                    val outUri = Uri.fromFile(File(picPath))
                    val options = UCrop.Options()
                    options.setCompressionFormat(Bitmap.CompressFormat.PNG)
                    UCrop.of(fileUrl!!, outUri).withAspectRatio(1F, 1F).withMaxResultSize(320, 320).withOptions(options).start(activity, this@UserFragment)
                }
            //裁剪结
                UCrop.REQUEST_CROP -> {
                    val resultUri = UCrop.getOutput(data ?: Intent())
                    L.d("resultUri:$resultUri")
                    val outfile = UCrop.getOutput(data!!)
                    val picBitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, outfile)
                    profileImage.setImageBitmap(picBitmap)
                    UtilTools.pubImageToShare(activity, StaticClass.PROFILEKEY, picBitmap)
                }

                UCrop.RESULT_ERROR -> {
                    L.d("UCrop Error:${UCrop.getError(data!!)}")
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun startPhotoZoom(fileUri: Uri?) {
        if (fileUri == null) {
            return
        }

        val intent = Intent("com.android.camera.action.CROP")


        intent.setDataAndType(fileUri, "image/*")
        //设置裁剪
        intent.putExtra("crop", "true")
        //裁剪宽高比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        //裁剪图片的质量
        intent.putExtra("outputX", 320)
        intent.putExtra("outputY", 320)
        //发送数据
        intent.putExtra("return-data", true)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString())
        val picFile = File(picPath)
        val uri = FileProvider.getUriForFile(activity, "com.dotop.smartbutler.fileprovider", picFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        //不启用人脸识别
        intent.putExtra("noFaceDetection", false)

//        startActivityForResult(intent, REQUEST_PHOTO_ALBUM)
    }


}
