package com.moa.rxdemo.mvp.bean;

import com.moa.baselib.base.net.BaseResponse;
import com.moa.baselib.base.net.BaseUploadFileModel;
import com.moa.baselib.base.net.mvp.ValueCallback;
import com.moa.rxdemo.net.ApiCreator;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UploadModel extends BaseUploadFileModel<ForecastBean.Data> {

    ValueCallback<ForecastBean.Data> valueCallback;

    public UploadModel(ValueCallback<ForecastBean.Data> valueCallback) {
        this.valueCallback = valueCallback;
    }

    @Override
    public Observable<BaseResponse<ForecastBean.Data>> getUploadObservable(RequestBody body) {
        return ApiCreator.getUploadFileInterface(this).uploadFile(body);
    }

    @Override
    public void onProgressChange(int percent) {
        valueCallback.onProgressChange(percent);
    }

    @Override
    protected void onSuccess(ForecastBean.Data value) {
        valueCallback.onSuccess(value);
    }

    @Override
    protected void onFail(String msg) {
        valueCallback.onFail(msg);
    }
}
