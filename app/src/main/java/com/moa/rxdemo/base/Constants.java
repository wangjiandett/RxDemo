package com.moa.rxdemo.base;

import android.content.Context;

import com.moa.rxdemo.utils.SharePreUtils;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/12/17 16:01
 */
public class Constants {
    
    /**
     * 跟随系统
     */
    public final static String LAN_AUTO = "auto";
    /**
     * 简体中文
     */
    public final static String LAN_SIMPLE_CHINESE = "simple_chinese";
    /**
     * 英文
     */
    public final static String LAN_ENGLISH = "english";
    
    private static final String SP_KEY_MULTI_LANGUAGE = "sp_key_multi_language";
    
    
    /**
     * 保存多语言
     *
     * @param context
     * @param value
     */
    public static void saveMultiLanguage(Context context, String value) {
        SharePreUtils.saveString(context, SP_KEY_MULTI_LANGUAGE, value);
    }
    
    /**
     * 获得多语言
     *
     * @param context
     * @param defValue
     * @return
     */
    public static String getMultiLanguage(Context context, String defValue) {
        return SharePreUtils.getString(context, SP_KEY_MULTI_LANGUAGE, defValue);
    }
    
    
}
