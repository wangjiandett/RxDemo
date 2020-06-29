package com.moa.baselib.base.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 下载文件拦截，监听下载进度
 */
public class DownloadInterceptor implements Interceptor {

    /**
     * 下载进度回调
     */
    private ProgressResultListener downloadListener;

    public DownloadInterceptor(ProgressResultListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new ProgressResponseBody(response.body(), downloadListener)).build();
    }
}
