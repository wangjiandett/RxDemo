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
        loadStatus.setValue(LoadState.STATE_LOADING_SUCCESS());
    }
    
    @Override
    public void onFail(String msg) {
        loadStatus.setValue(LoadState.STATE_LOADING_FAIL(msg));
    }
    
    private MutableLiveData<LoadState> loadStatus;
    
    public BaseViewModel() {
        this.loadStatus = getLiveData();
    }
    
    public MutableLiveData<LoadState> getLoadStatus() {
        return loadStatus;
    }
    
    protected <D> MutableLiveData<D> getLiveData() {
        return new MutableLiveData<D>();
    }
}
