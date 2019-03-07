package com.moa.baselib.view.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * RecyclerView网格分割线
 *
 * @author wangjian
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private boolean enableEmptyView = false;
    private boolean hasHeader = false;
    private boolean hasFooter = false;

    public GridDividerItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        } else {
            this.mDivider = drawable;
        }
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    /**
     * 特殊处理当只显示empty view的时候分割线的显示
     *
     * @param enableEmptyView
     */
    public void setEnableEmptyView(boolean enableEmptyView) {
        this.enableEmptyView = enableEmptyView;
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private int getOrientation(RecyclerView.LayoutManager layoutManager) {
        int orientation = LinearLayoutManager.VERTICAL;
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
        }

        return orientation;

    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = getOrientation(layoutManager);
            if (orientation == GridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            }

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = getOrientation(layoutManager);
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            int orientation = getOrientation(layoutManager);
            if (orientation == GridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            } else {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int itemAdapterPosition = parent.getChildAdapterPosition(view);
        int itemPosition = parent.getChildLayoutPosition(view);

        // 有header的时候
        if (hasHeader) {
            itemPosition = itemPosition - 1;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int orientation = getOrientation(layoutManager);
        // 竖直和水平  最后一列和最后一行是颠倒的
        if (orientation == LinearLayoutManager.VERTICAL) {
            // 如果是最后一行，则不需要绘制底部
            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
            // 如果是最后一列，则不需要绘制右边
            else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                        mDivider.getIntrinsicHeight());
            }
        } else {
            // 如果是最后一行，则不需要绘制底部
            if (isLastColum(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
            // 如果是最后一列，则不需要绘制右边
            else if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                        mDivider.getIntrinsicHeight());
            }
        }

        // 处理显示empty的情况
        if(enableEmptyView){
            if((hasHeader && childCount == 2 && itemAdapterPosition == 1)// header 和 empty 同时显示
               || !hasHeader && childCount == 1 && itemAdapterPosition == 0){// 只显示empty
                outRect.set(0, 0, 0, 0);
            }
        }

        // 可能被上面的覆盖，所以放到最后
        // 是否有footer
        boolean drawFooter = (hasFooter && itemAdapterPosition == childCount - 1);

        // footer不用绘制底部和右边
        if (drawFooter) {
            outRect.set(0, 0, 0, 0);
        }
    }
}