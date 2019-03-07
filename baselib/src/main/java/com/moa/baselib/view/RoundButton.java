/*
 *  Copyright (C) 2016-2017 浙江海宁山顶动力网络科技有限公司
 * 文件说明：
 *  <p>
 *  更新说明:
 *
 *  @author wangjian@xiaokebang.com
 *  @version 1.0.0
 *  @create 17-7-17 下午3:25
 *  @see <a href="http://www.top4s.net/">http://www.top4s.net/</a>
 */
package com.moa.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

import com.moa.baselib.R;
import com.moa.baselib.utils.ColorUtils;


/**
 * 圆角button
 */
public class RoundButton extends android.support.v7.widget.AppCompatButton {
    
    
    public RoundButton(Context context) {
        super(context);
        init(context, null);
    }
    
    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private RoundedColorDrawable mRoundBg;
    private ColorDrawable mColorDrawable;
    private int mRadius;
    private int borderWidth;
    private int borderColor;
    
    private void init(Context context, AttributeSet attrs) {
        Drawable bg = getBackground();
        if (bg == null) {
            bg = new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent));
        }
        
        if (bg instanceof StateListDrawable) {
            // TODO support StateListDrawable
            // mRoundBg = new RoundedColorDrawable(radius, color);
//            StateListDrawable out = new StateListDrawable();
//            StateListDrawable sld = (StateListDrawable) bg;
//            int[] st = sld.getState();
//            for (int i = 0; i < st.length; i++) {
//                sld.selectDrawable(i);
//                sld.getCurrent();
//                
//            }
        }
        else if (bg instanceof ColorDrawable) {
            mColorDrawable = (ColorDrawable) bg;
            mRoundBg = RoundedColorDrawable.fromColorDrawable(mColorDrawable);
        }
        if (attrs != null) {
            int[] attr = R.styleable.RoundButton;
            TypedArray a = context.obtainStyledAttributes(attrs, attr);
            mRadius = a.getDimensionPixelOffset(R.styleable.RoundButton_android_radius, mRadius);
            borderColor = a.getColor(R.styleable.RoundButton_bordersColor, Color.TRANSPARENT);
            borderWidth = a.getDimensionPixelOffset(R.styleable.RoundButton_bordersWidth, borderWidth);
            setRadius(mRadius);
            setBorder(borderColor, borderWidth);
//            if (a.hasValue(R.styleable.RoundButton_pressedBgColor)) {
//                setStateBgColor(new int[]{android.R.attr.state_pressed},
//                    a.getColor(R.styleable.RoundButton_pressedBgColor, Color.TRANSPARENT), borderColor);
//            }
//            if (a.hasValue(R.styleable.RoundButton_checkedBgColor)) {
//                setStateBgColor(new int[]{android.R.attr.state_checked},
//                    a.getColor(R.styleable.RoundButton_checkedBgColor, Color.TRANSPARENT), borderColor);
//            }
//            if (a.hasValue(R.styleable.RoundButton_selectedBgColor)) {
//                setStateBgColor(new int[]{android.R.attr.state_selected},
//                    a.getColor(R.styleable.RoundButton_selectedBgColor, Color.TRANSPARENT), borderColor);
//            }
            a.recycle();
        }
        
        if (mColorDrawable != null) {
            setDefaultPressColor();
        }
        else {
            ViewCompat.setBackground(this, mRoundBg);
        }
    }
    
    private void setDefaultPressColor() {
        int darkcolor = ColorUtils.getDarkerColor(mColorDrawable.getColor());
        setPressedBgColor(darkcolor);
        apply();
    }
    
    public void setRadius(int radius) {
        if (mRoundBg != null) {
            mRoundBg.setRadius(radius);
        }
    }
    
    public void setBorder(int color, int width) {
        if (mRoundBg != null) {
            mRoundBg.setBorder(color, width);
        }
    }
    
    public void setRoundBackground(Drawable background) {
        if (background instanceof ColorDrawable) {
            mRoundBg = RoundedColorDrawable.fromColorDrawable((ColorDrawable) background);
            mRoundBg.setRadius(mRadius);
            ViewCompat.setBackground(this, mRoundBg);
        }
    }
    
    public void setRoundBackgroundColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.setColor(color);
        }
        else {
            setRoundBackground(new ColorDrawable(color));
        }
    }
    
    public void addStateBgColor(int[] stateSet, int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(stateSet, color);
        }
    }
    
    public void setPressedBgColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(android.R.attr.state_pressed, color);
        }
    }
    
    public void setCheckedBgColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(android.R.attr.state_checked, color);
        }
    }
    
    public void setSelectedBgColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(android.R.attr.state_selected, color);
        }
    }
    
    public void apply() {
        if (mRoundBg != null) {
            mRoundBg.applyTo(this);
        }
    }
}
