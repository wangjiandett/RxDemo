package com.moa.baselib.base.net.mvvm;

/**
 * Data response
 * <p>
 * Created by：wangjian on 2018/12/26 11:18
 */
public class ResponseData<Response> {

    /**
     * request status code
     */
    public LoadState loadStatus;

    /**
     * request params
     */
    public Object request;

    /**
     * response
     */
    public Response response;
}
