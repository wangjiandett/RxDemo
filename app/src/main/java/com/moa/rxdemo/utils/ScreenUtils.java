package com.moa.rxdemo.utils;

import android.content.res.Resources;

import com.moa.rxdemo.App;

/**
 * 与手机屏幕相关api
 */
public class ScreenUtils {
    
    private static float density;
    private static float scaledDensity;
    
    public static int dp(float dp) {
        if (density == 0f) {
            density = App.getContext().getResources().getDisplayMetrics().density;
        }
        
        return (int) (dp * density + .5f);
    }
    
    public static int sp(float sp) {
        if (scaledDensity == 0f) {
            scaledDensity = App.getContext().getResources().getDisplayMetrics().scaledDensity;
        }
        
        return (int) (sp * scaledDensity + .5f);
    }
    
    public static int getWidth() {
        return App.getContext().getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getHeight() {
        return App.getContext().getResources().getDisplayMetrics().heightPixels;
    }
    
    public static int getStatusBarHeight() {
        
        int result = 0;
        int resourceId = App.getContext().getResources().getIdentifier("status_bar_height", "dimen",
            "android");
        if (resourceId > 0) {
            result = App.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    
    public static int getNavbarHeight() {
        if (hasNavigationBar()) {
            int resourceId = App.getContext().getResources().getIdentifier("navigation_bar_height", "dimen",
                "android");
            if (resourceId > 0) {
                return App.getContext().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }
    
    public static boolean hasNavigationBar() {
        Resources resources = App.getContext().getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return (id > 0) && resources.getBoolean(id);
    }
    
    public static float getDensity() {
        return density;
    }
}