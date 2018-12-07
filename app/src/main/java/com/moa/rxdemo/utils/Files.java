package com.moa.rxdemo.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.moa.rxdemo.MyApplication;
import com.moa.rxdemo.R;

import java.io.File;

/**
 * 管理文件系统
 */
public class Files {
    
    
    public static final String APP_NAME = MyApplication.getInstance().getString(R.string.app_name);
    
    public static final String DIR_CRASH = "crash";
    
    //------------------外部存储--------------------
    
    /**
     * 获得crash目录
     *
     * @return
     */
    public static String getCrashDir(){
        return getExternalStorageChildDir(DIR_CRASH);
    }
    
    
    /**
     * 获取sdcard项目文件夹根目录
     *
     * @return
     */
    public static String getExternalStorageFileDir() {
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFile = Environment.getExternalStorageDirectory();
            if (externalFile != null) {
                String externalPath = externalFile.getAbsolutePath();
                path = FileUtil.createDir(externalPath, APP_NAME).getAbsolutePath();
            }
        }
        
        return path;
    }
    
    /**
     * sdcard 获取不到时，存储到手机内存中
     *
     * @param dirname 要获得的文件名
     * @return 文件路径
     */
    public static String getExternalStorageChildDir(String dirname){
        String dir = getExternalStorageFileDir();
        if(!TextUtils.isEmpty(dir)){
            return FileUtil.createDir(dir, dirname).getAbsolutePath();
        }
        return getExternalFileChildDir(dirname);
    }
    
    //------------------内部存储--------------------
    
    /**
     * 获得程序内存中files目录或子目录
     *
     * @param dirname 为null时返回根目录，否则返回对应的子目录
     * @return 目录路径
     */
    public static String getExternalFileChildDir(String dirname) {
        File file = MyApplication.getInstance().getExternalFilesDir(dirname);
        if (file == null) {
            return null;
        }
        
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
    
   
}
