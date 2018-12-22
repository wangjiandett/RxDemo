package com.moa.rxdemo.mvp.presenter;

import com.moa.rxdemo.base.net.SimpleValueCallback;
import com.moa.rxdemo.mvp.bean.SwipeItem;
import com.moa.rxdemo.mvp.contract.SwipeContract;
import com.moa.rxdemo.mvp.model.ISwipeModel;

import java.util.List;

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
    public void getSwipeList(int page) {
        iSwipeModel.loadSwipeList(page, new SimpleValueCallback<List<SwipeItem>>(){
            @Override
            public void onSuccess(List<SwipeItem> value) {
                super.onSuccess(value);
                iSwipeView.onSuccess(value);
            }
    
            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                iSwipeView.onFail(msg);
            }
        });
    }
}
