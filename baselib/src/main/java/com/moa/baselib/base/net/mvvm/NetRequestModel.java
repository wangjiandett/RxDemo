/*
 * Copyright (c) 2018.  For more infomation visit https://github.com/wangjiandett/RxDemo
 */
package com.moa.baselib.base.net.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
 * Net request base model
 *
 * @param <Response> response
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class NetRequestModel<Response> {

    /**
     * the success code
     */
    private int SUCCESS_CODE = 200;

    public NetRequestModel() {
    }

    public NetRequestModel(int SUCCESS_CODE) {
        this.SUCCESS_CODE = SUCCESS_CODE;
    }

    /**
     * send request
     *
     * @param observable request observable
     */
    public MutableLiveData<ResponseData<Response>> request(@NonNull Observable<BaseResponse<Response>> observable) {
        return request(null, observable);
    }

    /**
     * send request
     *
     * @param requestParams request params
     * @param observable    request observable
     */
    public MutableLiveData<ResponseData<Response>> request(@Nullable Object requestParams,
                                                           @NonNull Observable<BaseResponse<Response>> observable) {

        // response data
        final MutableLiveData<ResponseData<Response>> dataMutableLiveData = new MutableLiveData<>();

        final ResponseData<Response> responseData = new ResponseData<Response>();
        // add request params to responses value
        responseData.request = requestParams;

        observable.subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .map(new ResultFilter<Response>(SUCCESS_CODE))//
                .subscribe(new DefaultObserver<Response>() {

                    @Override
                    protected void onStart() {
                        LogUtils.d("onStart");
                        responseData.loadStatus = LoadState.LOADING;
                        dataMutableLiveData.postValue(responseData);
                    }

                    @Override
                    public void onNext(Response value) {
                        LogUtils.d("onNext");
                        responseData.loadStatus = LoadState.SUCCESS;
                        responseData.response = value;
                        dataMutableLiveData.postValue(responseData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError:" + e.getMessage());
                        ResponseException throwable = ExceptionHandle.handleException(e);
                        responseData.loadStatus = LoadState.FAIL.setTipMsg(throwable.message);
                        dataMutableLiveData.postValue(responseData);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("onComplete");
                    }
                });

        return dataMutableLiveData;
    }

    private static class ResultFilter<T> implements Function<BaseResponse<T>, T> {

        private int SUCCESS_CODE;

        public ResultFilter(int SUCCESS_CODE) {
            this.SUCCESS_CODE = SUCCESS_CODE;
        }

        @Override
        public T apply(BaseResponse<T> baseResponse) {
            // when status equals success code
            if (baseResponse.code == SUCCESS_CODE) {
                return baseResponse.data;
            }
            // throw server response exception
            throw new ServerException(baseResponse.code, baseResponse.msg);
        }
    }
}
