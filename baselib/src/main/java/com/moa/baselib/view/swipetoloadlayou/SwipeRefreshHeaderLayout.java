package com.moa.baselib.view.swipetoloadlayou;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author xujing
 * @version 2016/4/20
 */
public class SwipeRefreshHeaderLayout extends FrameLayout implements SwipeRefreshTrigger, SwipeTrigger {

    public SwipeRefreshHeaderLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onSwipe(int y, boolean isComplete) {
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void onReset() {
    }
}
