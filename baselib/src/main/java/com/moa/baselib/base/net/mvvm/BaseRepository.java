package com.moa.baselib.base.net.mvvm;

import android.arch.lifecycle.MutableLiveData;

import com.moa.baselib.base.net.BaseResponse;

import io.reactivex.Observable;

/**
 * Repository request data
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class BaseRepository {

    protected  <Response> MutableLiveData<ResponseData<Response>> request(Observable<BaseResponse<Response>> observable) {
        NetRequestModel<Response> baseMode = new NetRequestModel<Response>();
        return baseMode.request(observable);
    }

    protected <Response> MutableLiveData<ResponseData<Response>> request(Object requestParams, Observable<BaseResponse<Response>> observable) {
        NetRequestModel<Response> baseMode = new NetRequestModel<Response>();
        return baseMode.request(requestParams, observable);
    }
}
