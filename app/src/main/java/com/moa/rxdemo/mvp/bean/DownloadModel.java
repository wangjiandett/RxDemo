package com.moa.rxdemo.mvp.bean;

import com.moa.baselib.base.net.BaseDownloadFileModel;
import com.moa.baselib.base.net.ProgressResultListener;
import com.moa.baselib.base.net.mvp.ValueCallback;
import com.moa.baselib.utils.LogUtils;
import com.moa.rxdemo.net.ApiCreator;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class DownloadModel extends BaseDownloadFileModel {

    ValueCallback<File> valueCallback;

    public DownloadModel(ValueCallback<File> valueCallback) {
        this.valueCallback = valueCallback;
    }

    @Override
    protected Observable<ResponseBody> getDownloadObservable(ProgressResultListener listener) {
        return ApiCreator.getDownloadFileInterface(this).downloadFile();
    }

    @Override
    protected void onSuccess(File value) {
        LogUtils.d(value.getAbsoluteFile());
        valueCallback.onSuccess(value);
    }

    @Override
    protected void onFail(String msg) {
        LogUtils.d(msg);
        valueCallback.onFail(msg);
    }

    @Override
    public void onProgressChange(int percent) {
        // LogUtils.d(percent);
        valueCallback.onProgressChange(percent);
    }
}
