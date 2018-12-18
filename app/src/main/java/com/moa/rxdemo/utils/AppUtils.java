package com.moa.rxdemo.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.moa.rxdemo.base.Config;

import java.io.File;
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
    public static Context updateConfiguration(Context context){
    
        Locale locale = Config.getDefault().getCurrentLocale(context);
        
        Configuration config = context.getResources().getConfiguration();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        }else{
            config.locale = locale;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(config, dm);
        }
        return context;
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                activity.startActivityForResult(intent, code);
            } catch (ActivityNotFoundException e) {
                LogUtils.e(e.getMessage());
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        }
    }
}
