package com.moa.baselib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.moa.baselib.BaseApp;


/**
 * 与手机屏幕相关api
 *
 * Created by：wangjian on 2018/4/16 13:38
 */
public class DisplayUtils {
    
    private static DisplayMetrics displayMetrics = BaseApp.getAppContext().getResources().getDisplayMetrics();
    
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * <p/>
     * （DisplayMetrics类中属性density）
     *
     * @return
     */
    public static int px2dip(float pxValue) {
        final float scale = displayMetrics.density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * <p/>
     * （DisplayMetrics类中属性density）
     *
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = displayMetrics.density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * <p/>
     * （DisplayMetrics类中属性scaledDensity）
     *
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = displayMetrics.scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * <p/>
     * （DisplayMetrics类中属性scaledDensity）
     *
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = displayMetrics.scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕宽度
     */
    public static int getWindowWidth() {
        return displayMetrics.widthPixels;
    }

    /**
     *
     * @return
     */
    public static int getWindowHeight() {
        return displayMetrics.heightPixels;
    }
    
    public static int getStatusBarHeight() {
        
        int result = 0;
        int resourceId = BaseApp.getAppContext().getResources().getIdentifier("status_bar_height", "dimen",
            "android");
        if (resourceId > 0) {
            result = BaseApp.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    
    public static int getNavbarHeight() {
        if (hasNavigationBar()) {
            int resourceId = BaseApp.getAppContext().getResources().getIdentifier("navigation_bar_height", "dimen",
                "android");
            if (resourceId > 0) {
                return BaseApp.getAppContext().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }
    
    public static boolean hasNavigationBar() {
        Resources resources = BaseApp.getAppContext().getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return (id > 0) && resources.getBoolean(id);
    }
    
    public static float getDensity() {
        return displayMetrics.density;
    }
}