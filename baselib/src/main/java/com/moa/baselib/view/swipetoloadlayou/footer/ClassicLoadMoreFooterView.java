package com.moa.baselib.view.swipetoloadlayou.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moa.baselib.R;
import com.moa.baselib.view.swipetoloadlayou.SwipeLoadMoreFooterLayout;


/**
 * @author xujing
 * @version 2016/4/20
 */
public class ClassicLoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ProgressBar progressBar;

    private int mFooterHeight;

    public ClassicLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.tt_swipe_load_more_footer_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onSwipe(int y, boolean isComplete) {
        if (!isComplete) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadMore() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void complete() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onReset() {
    }


    public TextView getTvLoadMore() {
        return tvLoadMore;
    }

    public void setTvLoadMore(TextView tvLoadMore) {
        this.tvLoadMore = tvLoadMore;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setprogressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
