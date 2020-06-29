package com.moa.baselib.base.net;

public interface ProgressResultListener {

    /**
     * 进度变化回调
     *
     * @param percent 上传/下载文件进度
     */
    void onProgressChange(int percent);
}
