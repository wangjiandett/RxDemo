package com.moa.baselib.view.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class ViewPageHelper {
    ViewPager viewPager;

    MScroller scroller;

    public ViewPageHelper(ViewPager viewPager) {
        this.viewPager = viewPager;
        init();
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    public MScroller getScroller() {
        return scroller;
    }

    public void setCurrentItem(int item, boolean smooth) {
        int current = viewPager.getCurrentItem();
        //如果页面相隔大于1,就设置页面切换的动画的时间为0
        if (Math.abs(current - item) > 1) {
            scroller.setNoDuration(true);
            viewPager.setCurrentItem(item, smooth);
            scroller.setNoDuration(false);
        } else {
            scroller.setNoDuration(false);
            viewPager.setCurrentItem(item, smooth);
        }
    }

    private void init() {
        scroller = new MScroller(viewPager.getContext());
        Class<ViewPager> cl = ViewPager.class;
        try {
            Field field = cl.getDeclaredField("mScroller");
            field.setAccessible(true);
            //利用反射设置mScroller域为自己定义的MScroller
            field.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static class MScroller extends Scroller {

        private static final Interpolator sInterpolator = new Interpolator() {
            public float getInterpolation(float t) {
                t -= 1.0f;
                return t * t * t * t * t + 1.0f;
            }
        };

        public boolean noDuration;

        public MScroller(Context context) {
            this(context, sInterpolator);
        }

        public void setNoDuration(boolean noDuration) {
            this.noDuration = noDuration;
        }

        public MScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            if (noDuration)
                //界面滑动不需要时间间隔
                super.startScroll(startX, startY, dx, dy, 0);
            else
                super.startScroll(startX, startY, dx, dy, duration);
        }
    }

}