package com.moa.rxdemo.mvp.model;


import com.moa.rxdemo.base.net.BaseModel;
import com.moa.rxdemo.base.net.ValueCallback;
import com.moa.rxdemo.mvp.bean.SwipeItem;

import java.util.List;

/**
 * load swipe list
 * <p>
 * <p>
 * Created by：wangjian on 2017/12/21 11:00
 */
public class SwipeModelImpl extends BaseModel<List<SwipeItem>> implements ISwipeModel {
    
    @Override
    public void loadSwipeList(int page, ValueCallback<List<SwipeItem>> callback) {
        this.mCallback = callback;
        request(apis.getSwipeList(page));
    }
    @Override
    protected void onShowProgress() {// optional
        mCallback.onShowProgress();
    }
    
    @Override
    protected void onHideProgress() {// optional
        mCallback.onHideProgress();
    }
    
    @Override
    protected void onSuccess(List<SwipeItem> value) {
        mCallback.onSuccess(value);
    }
    
    @Override
    protected void onFail(String msg) {
        mCallback.onFail(msg);
    }
    
    
}
