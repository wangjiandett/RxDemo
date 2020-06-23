package com.moa.rxdemo.mvp.contract;


import com.moa.rxdemo.mvp.bean.ForecastBean;

import java.util.List;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 10:44
 */
public interface SwipeContract {
    
    interface ISwipePresenter{
        void getSwipeList(int cityId);
    }
    
    interface ISwipeView{
        void onSuccess(List<ForecastBean.Forecast> itemList);
        void onFail(String msg);
    }
    
}
