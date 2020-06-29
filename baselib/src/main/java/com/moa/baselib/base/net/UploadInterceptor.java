package com.moa.baselib.base.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 上传文件拦截监听上传进度
 */
public class UploadInterceptor implements Interceptor {
    private ProgressResultListener callBack;

    public UploadInterceptor(ProgressResultListener callBack) {
        this.callBack = callBack;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //包装响应体
        request.newBuilder().method(request.method(),
                new ProgressRequestBody(request.body(), callBack)).build();
        return chain.proceed(request);
    }

}
