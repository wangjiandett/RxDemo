package com.moa.rxdemo.mvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.moa.rxdemo.mvp.bean.ForecastBean;
import com.moa.baselib.base.net.mvvm.BaseViewModel;
import com.moa.baselib.base.net.mvvm.ResponseData;
import com.moa.rxdemo.mvvm.repository.SwipeRepository;

/**
 * 加载swipe data的viewModel
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class SwipeModel extends BaseViewModel<SwipeRepository> {

    public MutableLiveData<ResponseData<ForecastBean.Data>> getSwipeList(int cityId){
       return mRepository.requestSwipeList(cityId);
   }

    
}
