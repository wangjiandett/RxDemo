package com.moa.baselib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import com.moa.baselib.base.Config;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/12/17 15:53
 */
public class AppUtils {
    
    /**
     * 更新多语言
     *
     * @param context
     * @return
     */
    public static Context updateConfiguration(Context context) {
        
        Locale locale = Config.getDefault().getCurrentLocale(context);
        
        Configuration config = context.getResources().getConfiguration();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        }
        else {
            config.locale = locale;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(config, dm);
        }
        return context;
    }
    
    public static String getVersion(Context context) {
        try {
            PackageInfo pkinfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                PackageManager.GET_CONFIGURATIONS);
            return pkinfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 打开相机照相
     *
     * @param activity
     * @param file
     * @param code
     */
    public static void startCamera(Activity activity, File file, int code) {
        if (activity != null && file != null) {
            try {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(activity, file));
                activity.startActivityForResult(intent, code);
            } catch (ActivityNotFoundException e) {
                LogUtils.e(e.getMessage());
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        }
    }
    
    
    public static void chooseImage(Fragment fragment, Activity activity, int CROP_REQUEST_CODE) {
//         1.
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    
//        2.
        
//        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");

//        3.
//        Intent intent = new Intent();
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        }
        if (fragment != null) {
            fragment.startActivityForResult(intent, CROP_REQUEST_CODE);
        }
        else {
            activity.startActivityForResult(intent, CROP_REQUEST_CODE);
        }
    }
    
    public static void cropImage(Uri uri, boolean scalable, int outWidth, int outHeight, File outFile, Fragment fragment,
                                 Activity activity, int CROP_REQUEST_CODE) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 加入访问权限
        /*解决跳转到裁剪提示“图片加载失败”问题*/
        // intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        
        intent.setDataAndType(uri, "image/*");
        //显示剪辑的小方框，不然没有剪辑功能，只能选取图
        intent.putExtra("crop", true);
        //是否可以缩放,解决图片有黑边问题
        intent.putExtra("scale", scalable);
        intent.putExtra("scaleUpIfNeeded", true);
        // 设置x,y的比例，截图方框就按照这个比例来截 若设置为0,0，或者不设置 则自由比例截图
        //intent.putExtra("aspectX", 1);
       // intent.putExtra("aspectY", 1);
        // 裁剪区的宽和高 其实就是裁剪后的显示区域 若裁剪的比例不是显示的比例，
        // 则自动压缩图片填满显示区域。若设置为0,0 就不显示。若不设置，则按原始大小显示
        if(outWidth >= 0){
            intent.putExtra("outputX", outWidth);
            intent.putExtra("outputY", outHeight);
        }
        
        // 输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        // 是否将数据保留在Bitmap中返回 此处回调中不在返回图片，而是放入outFile中
        intent.putExtra("return-data", false);
        // Android对Intent中所包含数据的大小是有限制的，一般不能超过1M，
        // 否则应用就会崩溃，这就是Intent中的图片数据只能是缩略图的原因。
        // 而解决的办法也很简单，我们需要给图片裁剪应用指定一个输出文件，用来存放裁剪后的图片
        intent.putExtra("output", Uri.fromFile(outFile));// 裁剪后的保存文件
        if (fragment != null) {
            fragment.startActivityForResult(intent, CROP_REQUEST_CODE);
        }
        else {
            activity.startActivityForResult(intent, CROP_REQUEST_CODE);
        }
    }


    public static Drawable getDrawable(Context context, int resid){
        return ContextCompat.getDrawable(context, resid);
    }

    /**
     * 判断是否主进程
     * @param context 上下文
     * @return true是主进程
     */
    public static boolean isMainProcess(Context context){
       return isSameProcess(context, Process.myPid(), getMainProcessName(context));
    }

    /**
     * 获取主进程名
     * @param context 上下文
     * @return 主进程名
     */
    public static String getMainProcessName(Context context){
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).processName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 是否是相同进程
     *
     * @param context 上下文
     * @param pid 进程id
     * @param processName 进程名
     * @return 是否是相同进程
     */
    public static boolean isSameProcess(Context context, int pid, String processName){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos){
            if(pid == info.pid && TextUtils.equals(info.processName, processName)){
                return true;
            }
        }

        return false;
    }

}
