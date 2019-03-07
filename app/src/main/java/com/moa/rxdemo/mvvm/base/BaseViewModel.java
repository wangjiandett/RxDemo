/*
 * Copyright (c) 2018.  For more infomation visit https://github.com/wangjiandett/RxDemo
 */
package com.moa.rxdemo.mvvm.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.moa.baselib.base.net.BaseResponse;
import com.moa.baselib.base.net.exception.ExceptionHandle;
import com.moa.baselib.base.net.exception.ResponeException;
import com.moa.baselib.base.net.exception.ServerException;
import com.moa.baselib.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

import static com.moa.rxdemo.mvvm.base.LoadState.STATE_FAIL;
import static com.moa.rxdemo.mvvm.base.LoadState.STATE_LOADING;
import static com.moa.rxdemo.mvvm.base.LoadState.STATE_SUCCESS;


/**
 * viewModel基类,封装网络请求
 *
 * @param <Response> 响应参数泛型
 */
public abstract class BaseViewModel<Response> extends ViewModel {
    
    /**
     * 自定义请求成功返回码
     */
    private static final int SUCCESS_CODE = 200;
    
    // 响应数据
    private LiveData<ResponseData<Response>> mResponseLiveData;
    // 请求数据
    private MutableLiveData<Object> mRequestLiveData;
    
    public BaseViewModel() {
        // 设置请求参数
        this.mRequestLiveData = new MutableLiveData<>();
        
        // 设置响应
        this.mResponseLiveData = Transformations.switchMap(mRequestLiveData, new android.arch.core.util.Function<Object, LiveData<ResponseData<Response>>>() {
            @Override
            public LiveData<ResponseData<Response>> apply(Object requestParams) {
                if (getRequest(requestParams) != null) {
                    return request(requestParams, getRequest(requestParams));
                }
                else {
                    return new MutableLiveData<>();
                }
            }
        });
    }
    
    /**
     * 发送请求
     *
     * @param requestParams 请求参数
     */
    public void sendRequest(Object requestParams) {
        this.mRequestLiveData.postValue(requestParams);
    }
    
    /**
     * 获取响应结果
     *
     * @return 响应体
     */
    public LiveData<ResponseData<Response>> getResponse() {
        return mResponseLiveData;
    }
    
    /**
     * 获取请求体
     *
     * @param request 请求参数
     * @return 请求体
     */
    protected abstract Observable<BaseResponse<Response>> getRequest(Object request);
    
    /**
     * 发送请求并解析数据
     *
     * @param request    请求参数
     * @param observable 请求体
     */
    protected MutableLiveData<ResponseData<Response>> request(Object request,
                                                              Observable<BaseResponse<Response>> observable) {
        
        // 封装响应数据
        final MutableLiveData<ResponseData<Response>> dataMutableLiveData = new MutableLiveData<>();
        
        final ResponseData<Response> responseData = new ResponseData<Response>();
        // 带回请求参数，如同一个ViewModel中多个请求，可在响应中做response区分
        responseData.request = request;
        
        observable.subscribeOn(Schedulers.io())//
            .observeOn(AndroidSchedulers.mainThread())//
            .map(new ResultFilter<Response>())//
            .subscribe(new DefaultObserver<Response>() {
                
                @Override
                protected void onStart() {
                    LogUtils.d("onStart");
                    responseData.loadStatus = STATE_LOADING();
                    dataMutableLiveData.postValue(responseData);
                }
                
                @Override
                public void onNext(Response value) {
                    LogUtils.d("onNext");
                    responseData.loadStatus = STATE_SUCCESS();
                    responseData.response = value;
                    dataMutableLiveData.postValue(responseData);
                }
                
                @Override
                public void onError(Throwable e) {
                    LogUtils.e("onError:" + e.getMessage());
                    ResponeException throwable = ExceptionHandle.handleException(e);
                    responseData.loadStatus = STATE_FAIL(throwable.message);
                    dataMutableLiveData.postValue(responseData);
                }
                
                @Override
                public void onComplete() {
                    LogUtils.d("onComplete");
                    // responseData.loadStatus = STATE_LOADED();
                    // dataMutableLiveData.postValue(responseData);
                }
            });
        
        return dataMutableLiveData;
    }
    
    private class ResultFilter<T> implements Function<BaseResponse<T>, T> {
        
        @Override
        public T apply(BaseResponse<T> baseResponse) {
            // when status equals success code
            if (baseResponse.code == SUCCESS_CODE) {
                return baseResponse.data;
            }
            // throw 服务端自定义的异常
            throw new ServerException(baseResponse.code, baseResponse.msg);
        }
    }
}
