package com.moa.baselib.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * show popwindow
 *
 * @author wangjian
 * @date 2015/11/11.
 */
public class PopWindowUtils {

    /**
     * show the popwindow on view's bottom
     *
     * @param context
     * @param clickView   the view click to show popwidow
     * @param contentView the layout view be shown
     */
    public static PopupWindow showPopWindowBottom(Context context, View clickView, View contentView) {
        return showPopWindow(context, clickView, contentView, 0, 0);
    }

    /**
     * show the popwindow on view's bottom
     *
     * @param context
     * @param clickView the view click to show popwidow
     * @param layoutId  the layout resid be shown
     */
    public static PopupWindow showPopWindowBottom(Context context, View clickView, int layoutId) {
        return showPopWindow(context, clickView, null, layoutId, 0);
    }

    /**
     * show the popwindow on view's top
     *
     * @param context
     * @param clickView   the view click to show popwidow
     * @param contentView the layout view be shown
     */
    public static PopupWindow showPopWindowTop(Context context, View clickView, View contentView) {
        return showPopWindow(context, clickView, contentView, 0, 1);
    }

    /**
     * show the popwindow on view's top
     *
     * @param context
     * @param clickView the view click to show popwidow
     * @param layoutId  the layout resid be shown
     */
    public static PopupWindow showPopWindowTop(Context context, View clickView, int layoutId) {
        return showPopWindow(context, clickView, null, layoutId, 1);
    }

    /**
     * show the popwindow on view's left
     *
     * @param context
     * @param clickView   the view click to show popwidow
     * @param contentView the layout view be shown
     */
    public static PopupWindow showPopWindowLeft(Context context, View clickView, View contentView) {
        return showPopWindow(context, clickView, contentView, 0, 2);
    }

    /**
     * show the popwindow on view's left
     *
     * @param context
     * @param clickView the view click to show popwidow
     * @param layoutId  the layout resid be shown
     */
    public static PopupWindow showPopWindowLeft(Context context, View clickView, int layoutId) {
        return showPopWindow(context, clickView, null, layoutId, 2);
    }

    /**
     * show the popwindow on view's right
     *
     * @param context
     * @param clickView   the view click to show popwidow
     * @param contentView the layout view be shown
     */
    public static PopupWindow showPopWindowRight(Context context, View clickView, View contentView) {
        return showPopWindow(context, clickView, contentView, 0, 3);
    }

    /**
     * show the popwindow on view's right
     *
     * @param context
     * @param clickView the view click to show popwidow
     * @param layoutId  the layout resid be shown
     */
    public static PopupWindow showPopWindowRight(Context context, View clickView, int layoutId) {
        return showPopWindow(context, clickView, null, layoutId, 3);
    }


    /**
     * show popwindow on clickview top, bottom ,right or left
     *
     * @param context
     * @param clickView   the view click to show popwidow
     * @param contentView the layout view be shown
     * @param layoutId    the layout resid be shown
     * @param location    0.bottom
     *                    1.top
     *                    2.left
     *                    3.right
     * @return
     */
    private static PopupWindow showPopWindow(Context context, View clickView, View contentView, int layoutId,
                                             int location) {
        PopupWindow mPopupWindows = new PopupWindow(context);
        View layout = null;
        if (contentView != null) {
            layout = contentView;
        }
        else if (layoutId > 0) {
            layout = LayoutInflater.from(context).inflate(layoutId, null);
        }
        else {
            throw new RuntimeException("the contentView should not be null or the layoutId > 0 is request");
        }
        mPopupWindows.setContentView(layout);
        // 在layout中如果view是设置为WRAP_CONTENT
        // 在popview弹出时无法获取宽度(getWidth() =-2)
        // 此时需要重新测量view
        measureView(layout);
        mPopupWindows.setTouchable(true);
        mPopupWindows.setFocusable(true);
        mPopupWindows.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindows.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindows.setBackgroundDrawable(new BitmapDrawable());

        if (location == 0) {
            // bottom
            mPopupWindows.showAsDropDown(clickView);
        }
        else if (location == 1) {
            // top
            int[] local = new int[2];
            clickView.getLocationOnScreen(local);
            mPopupWindows.showAtLocation(clickView, Gravity.NO_GRAVITY, local[0],
                local[1] - mPopupWindows.getContentView().getMeasuredHeight());
        }
        else if (location == 2) {
            // left
            int[] local = new int[2];
            clickView.getLocationOnScreen(local);
            mPopupWindows.showAtLocation(clickView, Gravity.NO_GRAVITY,
                local[0] - mPopupWindows.getContentView().getMeasuredWidth(), local[1]);
        }
        else if (location == 3) {
            // right
            int[] local = new int[2];
            clickView.getLocationOnScreen(local);
            mPopupWindows.showAtLocation(clickView, Gravity.NO_GRAVITY, local[0] + clickView.getMeasuredWidth(),
                local[1]);
        }
        return mPopupWindows;
    }

    public static PopupWindow showPopWindowClickPosition(Context context, final View clickView, int layoutId) {
        PopupWindow mPopupWindows = new PopupWindow(context);
        View layout = null;
        if (layoutId > 0) {
            layout = LayoutInflater.from(context).inflate(layoutId, null);
        }
        else {
            throw new RuntimeException("the contentView should not be null or the layoutId > 0 is request");
        }
        mPopupWindows.setContentView(layout);
        // 在layout中如果view是设置为WRAP_CONTENT
        // 在popview弹出时无法获取宽度(getWidth() =-2)
        // 此时需要重新测量view
        measureView(layout);
        mPopupWindows.setTouchable(true);
        mPopupWindows.setFocusable(true);
        mPopupWindows.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindows.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindows.setBackgroundDrawable(new BitmapDrawable());

        clickView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果是按下操作
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickView.setTag(event);
                }
                return false;
            }
        });

        int x;
        int y;

        int halfX = mPopupWindows.getContentView().getMeasuredWidth() / 2;
        int halfY = mPopupWindows.getContentView().getMeasuredHeight();


        if (clickView.getTag() != null) {
            MotionEvent event = (MotionEvent) clickView.getTag();
            x = (int) event.getX() - halfX;
            y = (int) event.getY() - halfY;
        }
        else {
            // top
            int[] local = new int[2];
            clickView.getLocationOnScreen(local);

            x = local[0] - halfX;
            y = local[1] - halfY;
        }

        mPopupWindows.showAtLocation(clickView, Gravity.NO_GRAVITY, x, y);
        return mPopupWindows;
    }


    /**
     * 测量这个view获取宽度和高度.
     *
     * @param view 要测量的view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        }
        else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }
}
