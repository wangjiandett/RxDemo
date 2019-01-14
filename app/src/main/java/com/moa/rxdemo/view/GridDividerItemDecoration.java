package com.moa.rxdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * RecyclerView网格分割线
 *
 * @author wangjian
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private boolean mHasHeader;
    private boolean mHasFooter;

    private Drawable mDivider;
    private int[] attrs = new int[]{android.R.attr.listDivider};

    public GridDividerItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        } else {
            this.mDivider = drawable;
        }
    }

    public boolean isHasHeader() {
        return mHasHeader;
    }

    public void setHasHeader(boolean mHasHeader) {
        this.mHasHeader = mHasHeader;
    }

    public boolean isHasFooter() {
        return mHasFooter;
    }

    public void setHasFooter(boolean mHasFooter) {
        this.mHasFooter = mHasFooter;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        //绘制垂直间隔线（垂直的矩形）
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int bottom = child.getBottom() + params.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        //绘制水平分割线
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        // 四个方向的偏移值
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();

        //RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        //int itemPosition = params.getViewAdapterPosition();
        int itemPosition = parent.getChildAdapterPosition(view);

        // LogUtils.d("itemPosition:"+itemPosition);

        int orientation = getOrientation(parent);

        // 是否有footer
        if (this.mHasFooter) {
            if (itemPosition == parent.getAdapter().getItemCount() - 1) {
                right = 0;
                bottom = 0;
            }
        }

        // 是否有header
        if (this.mHasHeader) {
            if (itemPosition == 0) {
                if (orientation == RecyclerView.VERTICAL) {
                    right = 0;
                } else {
                    bottom = 0;
                }
            } else {
                itemPosition = itemPosition - 1;
            }
        }

        if (orientation == RecyclerView.VERTICAL) {
            if (isLastColumn(itemPosition, parent)) {
                right = 0;
            }

            if (isLastRow(itemPosition, parent)) {
                bottom = 0;
            }
        } else {
            // 横向和纵向的列和行是颠倒的
            if (isLastColumn(itemPosition, parent)) {
                bottom = 0;
            }

            if (isLastRow(itemPosition, parent)) {
                right = 0;
            }
        }

        outRect.set(0, 0, right, bottom);
    }

    /**
     * 是否最后一行
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        if (spanCount != -1) {
            int childCount = parent.getAdapter().getItemCount();
            int lastRowCount = childCount % spanCount;
            //最后一行的数量小于spanCount
            if (lastRowCount == 0 || lastRowCount < spanCount) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否是最后一列
     */
    private boolean isLastColumn(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        if (spanCount != -1) {
            if ((itemPosition + 1) % spanCount == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据parent获取到列数
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            return lm.getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) layoutManager;
            return sglm.getSpanCount();
        }

        return -1;
    }

    private int getOrientation(RecyclerView parent) {
        int orientation = RecyclerView.VERTICAL;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
        }

        return orientation;
    }
}