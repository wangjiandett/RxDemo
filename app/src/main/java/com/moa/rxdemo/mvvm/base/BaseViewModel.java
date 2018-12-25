package com.moa.rxdemo.mvvm.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.moa.rxdemo.base.net.ValueCallback;

/**
 * The base View Model
 * <p>
 * Created by：wangjian on 2018/8/21 15:06
 */
public class BaseViewModel<T> extends ViewModel implements ValueCallback<T> {
    
    protected MutableLiveData<LoadState> loadStatus;
    protected MutableLiveData<T> mDataLiveData;
    
    public BaseViewModel() {
        this.loadStatus = new MutableLiveData<>();
        this.mDataLiveData = new MutableLiveData<>();
    }
    
    /**
     * 获得加载状态的live data
     *
     * @return
     */
    public MutableLiveData<LoadState> getLoadStatus() {
        return loadStatus;
    }
    
    /**
     * 获取保存数据的live date
     *
     * @return
     */
    public MutableLiveData<T> getLiveData() {
        return mDataLiveData;
    }
    
    
    
    @Override
    public void onShowProgress() {
        loadStatus.setValue(LoadState.STATE_LOADING());
    }
    
    @Override
    public void onHideProgress() {
        loadStatus.setValue(LoadState.STATE_LOADED());
    }
    
    @Override
    public void onSuccess(T value) {
        loadStatus.setValue(LoadState.STATE_SUCCESS());
    }
    
    @Override
    public void onFail(String msg) {
        loadStatus.setValue(LoadState.STATE_FAIL(msg));
    }
}
