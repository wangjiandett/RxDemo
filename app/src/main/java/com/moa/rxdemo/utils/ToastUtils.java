package com.moa.rxdemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ToastUtils
 */
public class ToastUtils {

    private static Toast toast;
    
    /**
     * 使用系统界面的toast
     *
     * @param context 上下文
     * @param text 显示的字符串
     * @param res 显示的字符串资源
     * @param duration 显示时间
     */
    @SuppressLint("ShowToast")
    private static void getToast(Context context, CharSequence text, int res, int duration) {
        if(context == null){
            throw new NullPointerException("the context can not be null");
        }
        if (toast == null) {
            if (!TextUtils.isEmpty(text)) {
                toast = Toast.makeText(context.getApplicationContext(), text, duration);
            }
            else {
                toast = Toast.makeText(context.getApplicationContext(), res, duration);
            }
        }
        else {
            if (!TextUtils.isEmpty(text)) {
                toast.setText(text);
            }
            else {
                toast.setText(res);
            }
        }
        toast.show();
    }
    
    /**
     * 自定义界面的toast
     *
     * @param context 上下文
     * @param text 显示的字符串
     * @param res 显示的字符串资源
     * @param duration 显示时间
     * @param gravity 显示的位置
     */
    private static void getToast(Context context, CharSequence text, int res, int duration, int gravity) {
        
        // 自定义布局，此处暂时使用系统界面，需要在此处修改
        int layoutId = android.R.layout.test_list_item;
        int messageViewId = android.R.id.message;
        
        TextView tv = null;
        View view = null;
        
        if (toast == null) {
            toast = new Toast(context);
            toast.setDuration(duration);
            toast.setGravity(gravity, 0, 10);
            LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflate.inflate(layoutId, null);
            toast.setView(view);
        }
        view = toast.getView();
        tv = (TextView)view.findViewById(messageViewId);
        
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        }
        else {
            tv.setText(res);
        }
        toast.show();
    }
    
    public static void showToast(Context context, CharSequence msg) {
        getToast(context, msg, 0, Toast.LENGTH_SHORT);
    }
    
    public static void showToast(Context context, int resId) {
        getToast(context, null, resId, Toast.LENGTH_SHORT);
    }
    
    public static void showLongToast(Context context, CharSequence msg) {
        getToast(context, msg, 0, Toast.LENGTH_LONG);
    }
    
    public static void showLongToast(Context context, int resId) {
        getToast(context, null, resId, Toast.LENGTH_LONG);
    }
    
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
