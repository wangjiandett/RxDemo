package com.moa.baselib.base;

import android.content.Context;

import com.moa.baselib.utils.SharePreUtils;

import java.util.Locale;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/12/17 15:50
 */
public class Config {

    private static final String SP_KEY_MULTI_LANGUAGE = "sp_key_multi_language";

    private static Config defaultInstance;
    // 保存系统语言
    private Locale mSystemLocale = Locale.getDefault();
    // 当前语言
    private Locale mCurrentLocale = null;

    private Config() {
    }

    public static Config getDefault() {
        if (defaultInstance == null) {
            synchronized (Config.class) {
                if (defaultInstance == null) {
                    defaultInstance = new Config();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 获得当前系统语音
     *
     * @param context
     * @return
     */
    public Locale getCurrentLocale(Context context) {
        if (mCurrentLocale == null) {
            mCurrentLocale = getLocaleByTag(getMultiLanguage(context, Constants.LAN_AUTO));
        }
        return mCurrentLocale;
    }

    /**
     * 设置语言类型
     *
     * @param context
     * @param language see <br/>
     *                 {@link Constants#LAN_AUTO}<br/>
     *                 {@link Constants#LAN_ENGLISH}<br/>
     *                 {@link Constants#LAN_SIMPLE_CHINESE}<br/>
     */
    public void setCurrentLocale(Context context, String language) {
        saveMultiLanguage(context, language);
        this.mCurrentLocale = getLocaleByTag(language);
    }

    /**
     * 获取当前多语言tag
     * @return <br/>
     *
     *  {@link Constants#LAN_AUTO}<br/>
     *  {@link Constants#LAN_ENGLISH}<br/>
     *  {@link Constants#LAN_SIMPLE_CHINESE}<br/>
     *
     */
    public String getCurrentLocaleTag(Context context) {
        return getMultiLanguage(context, Constants.LAN_AUTO);
    }

    private Locale getLocaleByTag(String localeTag) {
        Locale locale = null;
        // 简体中文
        if (Constants.LAN_SIMPLE_CHINESE.equalsIgnoreCase(localeTag)) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }
        // 英文
        else if (Constants.LAN_ENGLISH.equalsIgnoreCase(localeTag)) {
            locale = Locale.ENGLISH;
        } else {
            // 默认语言
            locale = mSystemLocale;
        }

        return locale;
    }

    /**
     * 保存多语言
     *
     * @param context context
     * @param value   see <br/>
     *                {@link Constants#LAN_AUTO}<br/>
     *                {@link Constants#LAN_ENGLISH}<br/>
     *                {@link Constants#LAN_SIMPLE_CHINESE}<br/>
     */
    public static void saveMultiLanguage(Context context, String value) {
        SharePreUtils.saveString(context, SP_KEY_MULTI_LANGUAGE, value);
    }

    /**
     * 获得多语言
     *
     * @param context  context
     * @param defValue 默认语言
     *
     * @return <br/>
     * {@link Constants#LAN_AUTO}<br/>
     * {@link Constants#LAN_ENGLISH}<br/>
     * {@link Constants#LAN_SIMPLE_CHINESE}<br/>
     */
    public static String getMultiLanguage(Context context, String defValue) {
        return SharePreUtils.getString(context, SP_KEY_MULTI_LANGUAGE, defValue);
    }
}
