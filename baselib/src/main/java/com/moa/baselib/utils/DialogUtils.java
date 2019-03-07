package com.moa.baselib.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.moa.baselib.R;


/**
 * 自定义dialog显示
 * <p>
 * Created by：wangjian on 2017/12/27 11:24
 */
public class DialogUtils {
    
    /**
     * 底部显示diaolog
     *
     * @param context
     */
    public static Dialog showBottomDialog(Context context, int resId) {
        Dialog dialog = getDialog(context, resId, Gravity.BOTTOM);
        Window window = dialog.getWindow();
        if(window != null){
            window.setWindowAnimations(R.style.tt_AnimBottom);
        }
        dialog.show();
        return dialog;
    }
    
    /**
     * 中间显示diaolog
     *
     * @param context
     */
    public static Dialog showCenterDialog(Context context, int resId) {
        return showDialog(context, resId, Gravity.CENTER);
    }
    
    /**
     * 显示diaolog
     *
     * @param context
     */
    public static Dialog showDialog(Context context, int resId, int gravity) {
        Dialog dialog = getDialog(context, resId, gravity);
        dialog.show();
        return dialog;
    }

    public static Dialog getDialog(Context context, int resId, int gravity) {
        if (context == null || resId <= 0) {
            return null;
        }
        Dialog dialog = new Dialog(context, R.style.tt_progressDialog);
        View view = LayoutInflater.from(context).inflate(resId, null);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.setContentView(view);
        window.setGravity(gravity);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
    
}
