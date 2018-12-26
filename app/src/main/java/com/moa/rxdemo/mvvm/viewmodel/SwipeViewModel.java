package com.moa.rxdemo.mvvm.viewmodel;

import com.moa.rxdemo.base.net.BaseResponse;
import com.moa.rxdemo.mvp.bean.SwipeItem;
import com.moa.rxdemo.mvvm.base.BaseViewModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * 加载swipe data的viewModel
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class SwipeViewModel extends BaseViewModel<List<SwipeItem>> {
    
    @Override
    protected Observable<BaseResponse<List<SwipeItem>>> getRequest(Object request) {
        // 根据请求参数区分请求
        if (request == null) {
            return apis.getMeituList();
        }
        else {
            return apis.getSwipeList(Integer.parseInt(request.toString()));
        }
    }
}
