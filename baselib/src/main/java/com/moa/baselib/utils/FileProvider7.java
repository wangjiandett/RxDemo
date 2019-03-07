package com.moa.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/6/28 14:22
 */
public class FileProvider7 {
    
    /**
     * 获取文件时通用
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        }
        else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }
    
    private static Uri getUriForFile24(Context context, File file) {
        return android.support.v4.content.FileProvider.getUriForFile(context,
            context.getPackageName() + ".android7.FileProvider", file);
    }
    
    /**
     * 安装apk时使用
     * {@link android.Manifest.permission#REQUEST_INSTALL_PACKAGES}
     *
     * @param context
     * @param intent
     * @param type
     * @param file
     */
    public static void setIntentDataAndType(Context context, Intent intent, String type, File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }
}
