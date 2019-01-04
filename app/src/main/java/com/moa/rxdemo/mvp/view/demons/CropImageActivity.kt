package com.moa.rxdemo.mvp.view.demons

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseActiivty
import com.moa.rxdemo.utils.AppUtils
import com.moa.rxdemo.utils.Files
import com.moa.rxdemo.utils.PermissionHelper
import com.moa.rxdemo.utils.matisse.GifSizeFilter
import com.moa.rxdemo.utils.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import kotlinx.android.synthetic.main.tt_activity_crop.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/2 17:05
 */
class CropImageActivity : BaseActiivty() {

    private val CROP_REQUEST_CODE: Int = 1
    private val SELECT_REQUEST_CODE: Int = 2

    override fun getLayoutId(): Int {
        return R.layout.tt_activity_crop
    }

    override fun initView() {
        super.initView()

        PermissionHelper.checkSDcardPermission(null, this, 0)

        btn_crop.setOnClickListener {

            // 系统图库选择图片
            // AppUtils.chooseImage(null, this, SELECT_REQUEST_CODE)

            // 知乎图库选择图片
            Matisse.from(this@CropImageActivity)
                    .choose(MimeType.ofImage())
                    .theme(R.style.Matisse_Dracula)
                    .countable(false)
                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .maxSelectable(1)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .imageEngine(Glide4Engine())
                    .forResult(SELECT_REQUEST_CODE)
        }
    }

    var cropFile: File? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_REQUEST_CODE) {
                // uris
                println(Matisse.obtainResult(data!!))
                // paths
                println(Matisse.obtainPathResult(data))

                cropFile = Files.getCropFile("crop.jpeg")
                AppUtils.cropImage(
                        Matisse.obtainResult(data)[0],// data?.data
                        true,
                        -1,
                        -1,
                        cropFile,
                        null,
                        this,
                        CROP_REQUEST_CODE)
            } else if (requestCode == CROP_REQUEST_CODE) {
                iv_crop.setImageBitmap(BitmapFactory.decodeFile(cropFile?.absolutePath))
            }
        }
    }


    private fun getImagePath(uri: Uri?): Bitmap? {

        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitmap
    }

    private fun updateUri(intent: Intent): Uri? {
        var uri: Uri? = null
        var bitmap: Bitmap? = null

        intent.data?.let {
            try {

                // 获取到图片
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

           // bitmap = getImagePath(it)

            // 重新插入，更新uri
            uri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, bitmap, null, null));
        }

        return uri
    }


}