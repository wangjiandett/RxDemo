package com.moa.baselib.view.auto;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自动计算高度
 *
 * @author wangjian
 * @version 2016/08/15
 */
public class AutoHeightGridView extends GridView {
    public AutoHeightGridView(Context context) {
        super(context);
    }
    
    public AutoHeightGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public AutoHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
