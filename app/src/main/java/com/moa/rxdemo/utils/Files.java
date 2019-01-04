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
    
    //crash日志保存的根目录
    public static final String DIR_CRASH = "crash";
    
    //crash日志保存的根目录
    public static final String DIR_CROP = "crop";
    
    
    /**
     * 获取裁剪图片
     *
     * @param cropFileName 文件名称
     * @return 图片文件
     */
    public static File getCropFile(String cropFileName){
        return FileUtil.createFile(getExternalCacheDir(DIR_CROP), cropFileName);
    }
    
    /**
     * 获得crash日志保存的根目录
     *
     * @return
     */
    public static String getCrashDir(){
        return getExternalFileChildDir(DIR_CRASH);
    }
    
    //------------------外部存储--------------------
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
    
    /**
     * 获得程序内存中Cache目录或子目录
     *
     * @param dirname 为null时返回根目录，否则返回对应的子目录
     * @return 目录路径
     */
    public static String getExternalCacheDir(String dirname) {
        File cacheDir = MyApplication.getInstance().getExternalCacheDir();
        if(!TextUtils.isEmpty(dirname)){
            cacheDir = new File(cacheDir, dirname);
    
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        
        return cacheDir.getAbsolutePath();
    }
}
