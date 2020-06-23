/*
 * Copyright (c) 2018.  For more infomation visit https://github.com/wangjiandett/RxDemo
 */
package com.moa.baselib.base.net.mvp;

import com.moa.baselib.base.net.BaseResponse;
import com.moa.baselib.base.net.exception.ExceptionHandle;
import com.moa.baselib.base.net.exception.ResponseException;
import com.moa.baselib.base.net.exception.ServerException;
import com.moa.baselib.utils.LogUtils;
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
     * the success code
     */
    protected int SUCCESS_CODE = 200;
    
    protected ValueCallback<T> mCallback;
    
    public BaseModel() {
    }

    public BaseModel(int SUCCESS_CODE) {
        this.SUCCESS_CODE = SUCCESS_CODE;
    }

    /**
     * send request and parse data
     *
     * @param observable
     */
    protected void request(Observable<BaseResponse<T>> observable) {
        observable.subscribeOn(Schedulers.io())//
            .observeOn(AndroidSchedulers.mainThread())//
            .map(new ResultFilter<T>(SUCCESS_CODE))//
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
    
    private static class ResultFilter<T> implements Function<BaseResponse<T>, T> {

        private int SUCCESS_CODE;

        public ResultFilter(int SUCCESS_CODE) {
            this.SUCCESS_CODE = SUCCESS_CODE;
        }

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
        ResponseException throwable = ExceptionHandle.handleException(e);
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
