package com.moa.rxdemo.mvvm.repository;

import android.arch.lifecycle.MutableLiveData;

import com.moa.rxdemo.mvp.bean.ForecastBean;
import com.moa.baselib.base.net.mvvm.BaseRepository;
import com.moa.baselib.base.net.mvvm.ResponseData;
import com.moa.rxdemo.net.ApiCreator;

public class SwipeRepository extends BaseRepository {

    public MutableLiveData<ResponseData<ForecastBean.Data>> requestSwipeList(int cityId){
       return request(ApiCreator.swipeInterfaces.getSwipeList(cityId));
    }

}
