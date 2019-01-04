package com.moa.rxdemo.mvp.view.demons

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseActiivty
import com.moa.rxdemo.base.ui.H5Activity
import com.moa.rxdemo.utils.LogUtils
import com.moa.rxdemo.utils.ScreenUtils
import com.moa.rxdemo.utils.matisse.GifSizeFilter
import com.moa.rxdemo.utils.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.tt_activity_scan.*


/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/4 10:14
 */

class ScanActivity : BaseActiivty(), QRCodeView.Delegate {

    private val REQUEST_CODE_CHOOSE = 23

    var isFlashlight = false;

    override fun getLayoutId(): Int {
        return R.layout.tt_activity_scan
    }

    override fun initView() {
        super.initView()
        mZXingView.setDelegate(this)
        tv_flash_light.setOnClickListener{
            isFlashlight = !isFlashlight

            if (isFlashlight) {
                // 打开闪光灯
                mZXingView.openFlashlight()
            } else {
                // 关闭闪光灯
                mZXingView.closeFlashlight()
            }
        }

        tv_choose_image.setOnClickListener{


            // 以下是图片选择器的使用
            Matisse.from(this@ScanActivity)
                    .choose(MimeType.ofAll(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            CaptureStrategy(true, "$packageName.android7.FileProvider"))
                    .maxSelectable(1)
                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(ScreenUtils.dp(120F))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    // .imageEngine(new GlideEngine())  // for glide-V3
                    .imageEngine(Glide4Engine())    // for glide-V4
                    .setOnSelectedListener { uriList, pathList ->
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=$pathList")
                    }
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .setOnCheckedListener { isChecked ->
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=$isChecked")
                    }
                    .forResult(REQUEST_CODE_CHOOSE)


//            Matisse.from(this@ScanActivity)
//                    .choose(MimeType.ofImage())
//                    .theme(R.style.Matisse_Dracula)
//                    .countable(false)
//                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .maxSelectable(9)
//                    .originalEnable(true)
//                    .maxOriginalSize(10)
//                    .imageEngine(Glide4Engine())
//                    .forResult(REQUEST_CODE_CHOOSE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            // uris
            println(Matisse.obtainResult(data!!))
            // paths
            println(Matisse.obtainPathResult(data))
            mZXingView.decodeQRCode(Matisse.obtainPathResult(data)[0])
        }
    }

    override fun onStart() {
        super.onStart()

        mZXingView.scanBoxView.isOnlyDecodeScanBoxArea = true // 仅识别扫描框中的码
        mZXingView.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        mZXingView.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        mZXingView.onDestroy() // 销毁二维码扫描控件
        super.onDestroy()
    }

    // Vibrate for 150 milliseconds
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(150, 10))
        } else {
            vibrator.vibrate(150)
        }
    }

    // 此处由于result可能为空，所以String后面必须加个？，否则当result为空时报错
    override fun onScanQRCodeSuccess(result: String?) {
        LogUtils.d("识别结果是：$result")
        vibrate()
        H5Activity.go(this, H5Activity.H5Request("识别结果", "识别结果是：$result"))
        // mZXingView.startSpot() // 再次开始识别
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = mZXingView.scanBoxView.tipText
        val ambientBrightnessTip = "\n环境过暗，请右上角打开闪光灯"
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.scanBoxView.tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                mZXingView.scanBoxView.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        showToast("打开相机出错")
    }

}