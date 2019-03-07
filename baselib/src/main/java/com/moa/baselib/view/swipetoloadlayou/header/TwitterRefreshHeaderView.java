package com.moa.baselib.view.swipetoloadlayou.header;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moa.baselib.R;
import com.moa.baselib.utils.LogUtils;
import com.moa.baselib.view.swipetoloadlayou.SwipeRefreshHeaderLayout;

/**
 * @author xujing
 * @version 2016/4/20
 */
public class TwitterRefreshHeaderView extends SwipeRefreshHeaderLayout {
    
    private TextView tvRefresh;
    
    private ProgressBar progressBar;
    
    private int mHeaderHeight;
    
    public TwitterRefreshHeaderView(Context context) {
        this(context, null);
    }
    
    public TwitterRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TwitterRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.tt_swipe_refresh_header_height);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }
    
    @Override
    public void onRefresh() {
        progressBar.setVisibility(VISIBLE);
        tvRefresh.setVisibility(View.GONE);
    }
    
    @Override
    public void onPrepare() {
        LogUtils.d("onPrepare()");
    }
    
    @Override
    public void onSwipe(int y, boolean isComplete) {
        if (!isComplete) {
            progressBar.setVisibility(VISIBLE);
            tvRefresh.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onRelease() {
        Log.d("TwitterRefreshHeader", "onRelease()");
    }
    
    @Override
    public void complete() {
        progressBar.setVisibility(GONE);
        tvRefresh.setText(R.string.tt_swipe_refresh_complete);
        tvRefresh.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void onReset() {
        progressBar.setVisibility(GONE);
    }
}
