package com.moa.rxdemo.mvvm.base;

/**
 * 响应体
 * <p>
 * Created by：wangjian on 2018/12/26 11:18
 */
public class ResponseData<Resp> {
    
    /**
     * 请求状态
     */
    public LoadState loadStatus;
    /**
     * 请求参数，如：同一个ViewModel中多个请求，可在响应回调中做response区分
     */
    public Object request;
    /**
     * 响应结果
     */
    public Resp response;
}
