package com.moa.baselib.view.swipetoloadlayou.header;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.moa.baselib.R;
import com.moa.baselib.utils.LogUtils;
import com.moa.baselib.view.swipetoloadlayou.SwipeRefreshHeaderLayout;

/**
 * @author xujing
 * @version 2016/4/20
 */
public class TaskRefreshHeaderView extends SwipeRefreshHeaderLayout {

    private TextView tvRefresh;

    private int mHeaderHeight;

    public TaskRefreshHeaderView(Context context) {
        this(context, null);
    }

    public TaskRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.tt_swipe_refresh_header_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvRefresh = (TextView) findViewById(R.id.onload_hint_text);
        tvRefresh.setText(R.string.tt_swipe_onload_data_hint);
    }

    @Override
    public void onRefresh() {
        tvRefresh.setText(R.string.tt_swipe_onload_data_hint);
    }

    @Override
    public void onPrepare() {
        LogUtils.d("onPrepare()");
    }

    @Override
    public void onSwipe(int y, boolean isComplete) {
        if (!isComplete) {
            tvRefresh.setText(R.string.tt_swipe_onload_data_hint);
        }
    }

    @Override
    public void onRelease() {
        Log.d("TaskRefreshHeaderView", "onRelease()");
    }

    @Override
    public void complete() {
        tvRefresh.setText(R.string.tt_swipe_refresh_complete);
    }

    @Override
    public void onReset() {

    }
}
