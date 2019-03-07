package com.moa.baselib.utils;

import android.content.res.Resources;

import com.moa.baselib.BaseApp;


/**
 * 与手机屏幕相关api
 */
public class ScreenUtils {
    
    private static float density;
    private static float scaledDensity;
    
    public static int dp(float dp) {
        if (density == 0f) {
            density = BaseApp.getAppContext().getResources().getDisplayMetrics().density;
        }
        
        return (int) (dp * density + .5f);
    }
    
    public static int sp(float sp) {
        if (scaledDensity == 0f) {
            scaledDensity = BaseApp.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        }
        
        return (int) (sp * scaledDensity + .5f);
    }
    
    public static int getWidth() {
        return BaseApp.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getHeight() {
        return BaseApp.getAppContext().getResources().getDisplayMetrics().heightPixels;
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
        return density;
    }
}