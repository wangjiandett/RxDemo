package com.moa.rxdemo.mvp.model;


import com.moa.baselib.base.net.mvp.BaseModel;
import com.moa.baselib.base.net.mvp.ValueCallback;
import com.moa.rxdemo.mvp.bean.ForecastBean;
import com.moa.rxdemo.net.ApiCreator;

/**
 * load swipe list
 * <p>
 * <p>
 * Created by：wangjian on 2017/12/21 11:00
 */
public class SwipeModelImpl extends BaseModel<ForecastBean.Data> implements ISwipeModel {
    
    @Override
    public void loadSwipeList(int cityId, ValueCallback<ForecastBean.Data> callback) {
        this.mCallback = callback;
        request(ApiCreator.swipeInterfaces.getSwipeList(cityId));
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
    protected void onSuccess(ForecastBean.Data value) {
        mCallback.onSuccess(value);
    }
    
    @Override
    protected void onFail(String msg) {
        mCallback.onFail(msg);
    }
    
    
}
