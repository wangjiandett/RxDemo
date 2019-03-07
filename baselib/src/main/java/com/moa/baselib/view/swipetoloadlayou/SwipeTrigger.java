package com.moa.baselib.view.swipetoloadlayou;

/**
 * @author xujing
 * @version 2016/8/17
 */
public interface SwipeTrigger {
    void onPrepare();

    void onSwipe(int y, boolean isComplete);

    void onRelease();

    void complete();

    void onReset();
}
