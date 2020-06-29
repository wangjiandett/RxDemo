package com.moa.baselib.base.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 带进度条的RequestBody封装
 */
public class ProgressRequestBody<T> extends RequestBody {

    private RequestBody mRequestBody;

    /**
     * 上传结果回调监听
     */
    private ProgressResultListener mListener;

    public ProgressRequestBody(RequestBody requestBody, final ProgressResultListener listener) {
        mRequestBody = requestBody;
        mListener = listener;
    }

    @Override
    public boolean isOneShot() {
        // 此处返回true，防止添加了日志拦截器导致，上传文件时writeTo函数2次调用（拦截器里面也调用了一次）
        return true;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink  mCountingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(mCountingSink);
        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;
        private long contentLength = 0L;
        public CountingSink(Sink delegate) {
            super(delegate);
        }
        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength == 0) {
                //获得contentLength的值，后续不再调用
                contentLength = contentLength();
            }
            bytesWritten += byteCount;

            int progress = (int) (100 * bytesWritten / contentLength);

            // 发送更新进度到啊主线程
            Message message =  Message.obtain();
            message.what = 1001;
            message.arg1 = progress;
            handler.sendMessage(message);
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1001){
                mListener.onProgressChange(msg.arg1);
            }
        }
    };
}


