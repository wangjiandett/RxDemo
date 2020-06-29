package com.moa.baselib.base.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 重写responsebody 设置下载进度监听
 */
public class ProgressResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;
    private ProgressResultListener progressResultListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressResultListener progressResultListener) {
        this.mResponseBody = responseBody;
        this.progressResultListener = progressResultListener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {

        return new ForwardingSource(source) {

            long totalBytesRead;
            float progressTimes = 0;
            long contentLength = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                //这个的进度应该是读取response每次内容的进度，在写文件进度之前 所以暂时以写完文件的进度为准
                long bytesRead = super.read(sink, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }

                totalBytesRead += ((bytesRead != -1) ? bytesRead : 0);

                if (progressResultListener != null) {
                    int progress = (int) (100 * totalBytesRead / contentLength);
                    // 只回调主线程100次
                    if(progress - progressTimes >= 1){
                        progressTimes = progress;

                        // 发送更新进度到啊主线程
                        Message message =  Message.obtain();
                        message.what = 1002;
                        message.arg1 = progress;
                        handler.sendMessage(message);
                    }
                }
                return bytesRead;
            }
        };
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1002){
                progressResultListener.onProgressChange(msg.arg1);
            }
        }
    };
}
