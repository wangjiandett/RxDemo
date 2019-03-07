package com.moa.baselib.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class ColorUtils {
    private static final int ENABLE_ATTR = android.R.attr.state_enabled;
    private static final int CHECKED_ATTR = android.R.attr.state_checked;
    private static final int PRESSED_ATTR = android.R.attr.state_pressed;

    public static ColorStateList generateThumbColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {PRESSED_ATTR, -CHECKED_ATTR},
                {PRESSED_ATTR, CHECKED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xAA000000,
                0xFFBABABA,
                tintColor - 0x99000000,
                tintColor - 0x99000000,
                tintColor | 0xFF000000,
                0xFFEEEEEE
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList generateBackColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {CHECKED_ATTR, PRESSED_ATTR},
                {-CHECKED_ATTR, PRESSED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xE1000000,
                0x10000000,
                tintColor - 0xD0000000,
                0x20000000,
                tintColor - 0xD0000000,
                0x20000000
        };
        return new ColorStateList(states, colors);
    }
    
    /**
     * 获取当前颜色的较暗值
     * <a href="http://www.cnblogs.com/klitech/p/5953059.html">HSV参看
     *
     * @param color
     * @return
     */
    public static int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        // make darker
        // hsv[1] = hsv[1] + 0.03f; // more saturation
        hsv[2] = hsv[2] - 0.1f; // less brightness
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }
    
    public static int getBrighterColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        
        // hsv[1] = hsv[1] - 0.1f; // less saturation
        hsv[2] = hsv[2] + 0.1f; // more brightness
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }

    public static int getColor(@NonNull Context context, @ColorRes int id){
        return ContextCompat.getColor(context, id);
    }
}
