/*
 *  Copyright (C) 2016-2017 浙江海宁山顶动力网络科技有限公司
 * 文件说明：
 *  <p>
 *  更新说明:
 *
 *  @author wangjian@xiaokebang.com
 *  @version 1.0.0
 *  @create 17-7-17 下午3:26
 *  @see <a href="http://www.top4s.net/">http://www.top4s.net/</a>
 */

package com.moa.baselib.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ViewUtils {
    
    /**
     * Set background drawable to view.
     *
     * @param view view
     * @param d    background drawable
     * @see View#setBackground(Drawable)
     */
    //--> for android sdk compatibility
    public static void setBackground(View view, Drawable d) {
        ViewCompat.setBackground(view, d);
    }
    
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
    
    /**
     * 测量这个view获取宽度和高度.
     *
     * @param view 要测量的view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        }
        else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }
    
    /**
     * 设置输入框的光标到末尾
     */
    public static final void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }
    
}
