package com.moa.baselib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.moa.baselib.R;


/**
 * 带分割线的gridview
 * <p>
 * Created by：wangjian on 2018/4/2 12:24
 */
public class ShowDividerGridView extends GridView {
    public ShowDividerGridView(Context context) {
        super(context);
    }
    
    public ShowDividerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public ShowDividerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int column = getNumColumns();//计算出一共有多少列
        int childCount = getChildCount();//子view的总数
        
        Paint localPaint = new Paint();//画笔
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(getContext().getResources().getColor(R.color.tt_list_divider));//设置画笔的颜色
        
        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);//获取子view
            // 最后一列，只画最右边竖线
            if ((i + 1) % column == 0) {
                //画子view底部横线
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
                    localPaint);
            }
            else {
                //如果view不是最后一列
                //画子view的右边竖线
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
                    localPaint);
                //画子view的底部横线
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
                    localPaint);
            }
        }
    }
}