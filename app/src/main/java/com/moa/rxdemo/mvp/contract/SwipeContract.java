package com.moa.rxdemo.mvp.contract;


import com.moa.rxdemo.mvp.bean.SwipeItem;

import java.util.List;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 10:44
 */
public interface SwipeContract {
    
    interface ISwipePresenter{
        void getSwipeList(int page);
    }
    
    interface ISwipeView{
        void onSuccess(List<SwipeItem> itemList);
        void onFail(String msg);
    }
    
}
