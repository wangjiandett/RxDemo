package com.moa.baselib.view.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 控制间隔多个页面之间的跳转，出现空白现象
 */
public class ScrollerViewPager extends ViewPager {


    private ViewPageHelper helper;

    public ScrollerViewPager(Context context) {
        this(context, null);
    }

    public ScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        helper = new ViewPageHelper(this);

    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        ViewPageHelper.MScroller scroller = helper.getScroller();
        if (Math.abs(getCurrentItem() - item) > 1) {
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        } else {
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }
}
