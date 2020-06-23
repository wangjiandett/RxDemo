package com.moa.rxdemo.mvp.presenter;

import com.moa.baselib.base.net.mvp.SimpleValueCallback;
import com.moa.rxdemo.mvp.bean.ForecastBean;
import com.moa.rxdemo.mvp.contract.SwipeContract;
import com.moa.rxdemo.mvp.model.ISwipeModel;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 15:45
 */
public class SwipePresenter implements SwipeContract.ISwipePresenter {
    
    private SwipeContract.ISwipeView iSwipeView;
    private ISwipeModel iSwipeModel;
    
    
    public SwipePresenter(SwipeContract.ISwipeView iSwipeView, ISwipeModel iSwipeModel) {
        this.iSwipeView = iSwipeView;
        this.iSwipeModel = iSwipeModel;
    }
    
    @Override
    public void getSwipeList(int cityId) {
        iSwipeModel.loadSwipeList(cityId, new SimpleValueCallback<ForecastBean.Data>(){
            @Override
            public void onSuccess(ForecastBean.Data value) {
                super.onSuccess(value);
                iSwipeView.onSuccess(value.forecast);
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                iSwipeView.onFail(msg);
            }
        });
    }
}
