package com.moa.baselib.view.auto;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 1.重写dispatchTouchEvent实现处理ViewPager嵌套ViewPager滑动冲突
 * 2.重写onMeasure实现ViewPage高度自适应
 *
 * @author wangjian
 * @version 2016/08/15
 */
public class AutoHeightViewPager extends ViewPager {
    
    public AutoHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 防止父view拦截touch事件
        int index = getCurrentItem();
        int count = getAdapter().getCount();
        // 控制在第一个item和最后一个item时，触发父类的滑动
        // 否则子类自己消费滑动事件
        if (index == 0 || index == count - 1) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // find the current child view
        // and you must cache all the child view
        // use setOffscreenPageLimit(adapter.getCount())
        View view = getChildAt(getCurrentItem());
        if (view != null) {
            // measure the current child view with the specified measure spec
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
        
        setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
    }
    
    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view        the base view with already measured height
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    
    /**
     * 单独测量view获取尺寸
     *
     * @param view
     */
    public void measeureView(View view) {
        
        int intw = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int inth = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        // 重新测量view
        view.measure(intw, inth);
        
        // 以上3句可简写成下面一句
        //view.measure(0,0);
        
        // 获取测量后的view尺寸
        int intwidth = view.getMeasuredWidth();
        int intheight = view.getMeasuredHeight();
    }
}