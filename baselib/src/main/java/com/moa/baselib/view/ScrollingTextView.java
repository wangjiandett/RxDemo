package com.moa.baselib.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * 超过宽度自动滚动的textview，在xml布局文件中如下使用，需要view获取焦点才可实现滚动
 *
 *      <com.moa.rxdemo.view.ScrollingTextView
 *         android:id="@+id/item_name"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:layout_gravity="center_horizontal"
 *         android:singleLine="true"
 *         android:ellipsize="marquee"
 *         android:focusable="true"
 *         android:marqueeRepeatLimit="marquee_forever"/>
 *
 * @author wangjian
 * @version 2016/08/15
 *
 */
public class ScrollingTextView extends AppCompatTextView {
    
    public ScrollingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public ScrollingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public ScrollingTextView(Context context) {
        super(context);
    }
    
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }
    
    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) {
            super.onWindowFocusChanged(focused);
        }
    }
    
    
    @Override
    public boolean isFocused() {
        return true;
    }
    
}
