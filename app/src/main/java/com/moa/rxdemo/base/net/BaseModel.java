/*
 * Copyright (c) 2018.  For more infomation visit https://github.com/wangjiandett/RxDemo
 */
package com.moa.rxdemo.base.net;

import com.moa.rxdemo.base.net.exception.ExceptionHandle;
import com.moa.rxdemo.base.net.exception.ResponeException;
import com.moa.rxdemo.base.net.exception.ServerException;
import com.moa.rxdemo.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Base model for send request
 * <p>
 * Created by：wangjian on 2017/12/21 16:41
 *
 * @param <T> the back value mode
 */
public abstract class BaseModel<T> {
    
    /**
     * the success code, need diy
     */
    private static final int SUCCESS_CODE = 200;
    
    protected Apis apis;
    protected ValueCallback<T> mCallback;
    
    public BaseModel() {
        apis = ApiService.getApis();
    }
    
    /**
     * send request and parse data
     *
     * @param observable
     */
    protected void request(Observable<BaseResponse<T>> observable) {
        observable.subscribeOn(Schedulers.io())//
            .observeOn(AndroidSchedulers.mainThread())//
            .map(new ResultFilter<T>())//
            .subscribe(new DefaultObserver<T>() {
                
                @Override
                protected void onStart() {
                    LogUtils.d("onStart");
                    onShowProgress();
                }
                
                @Override
                public void onNext(T value) {
                    LogUtils.d("onNext");
                    onHideProgress();
                    onSuccess(value);
                }
                
                @Override
                public void onError(Throwable e) {
                    LogUtils.e("onError:" + e.getMessage());
                    onHideProgress();
                    dealError(e);
                }
                
                @Override
                public void onComplete() {
                    LogUtils.d("onComplete");
                    // onHideProgress();
                }
            });
    }
    
    private class ResultFilter<T> implements Function<BaseResponse<T>, T> {
        
        @Override
        public T apply(BaseResponse<T> baseResponse) throws Exception {
            // when status equals success code
            if (baseResponse.code == SUCCESS_CODE) {
                return baseResponse.data;
            }
            // throw data error exception
            throw new ServerException(baseResponse.code, baseResponse.msg);
        }
    }
    
    /**
     * deal the exception
     *
     * @param e the exception
     */
    protected void dealError(Throwable e) {
        ResponeException throwable = ExceptionHandle.handleException(e);
        onFail(throwable.message);
    }
    
    /**
     * show loading progress
     */
    protected void onShowProgress() {
    }
    
    /**
     * hide loading progress
     */
    protected void onHideProgress() {
    }
    
    /**
     * the success callback
     *
     * @param value the success value
     */
    protected abstract void onSuccess(T value);
    
    /**
     * the fail callback
     *
     * @param msg the fail message
     */
    protected abstract void onFail(String msg);
    
    
}
