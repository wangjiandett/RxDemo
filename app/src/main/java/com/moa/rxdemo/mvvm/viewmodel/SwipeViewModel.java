package com.moa.rxdemo.mvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.moa.rxdemo.mvp.bean.SwipeItem;
import com.moa.rxdemo.mvp.model.SwipeModelImpl;
import com.moa.rxdemo.mvvm.base.BaseViewModel;

import java.util.List;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class SwipeViewModel extends BaseViewModel<List<SwipeItem>>{
    
    public MutableLiveData<List<SwipeItem>> mutableLiveData;
    
    public SwipeViewModel(){
        super();
        mutableLiveData = getLiveData();
    }
    
    public void loadData(int page){
        new SwipeModelImpl().loadSwipeList(page, this);
    }
    
    @Override
    public void onSuccess(List<SwipeItem> value) {
        super.onSuccess(value);
        mutableLiveData.setValue(value);
    }
    
    public MutableLiveData<List<SwipeItem>> getMutableLiveData() {
        return mutableLiveData;
    }
}
