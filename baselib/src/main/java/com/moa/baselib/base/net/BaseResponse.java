package com.moa.baselib.base.net;

import com.google.gson.annotations.SerializedName;

/**
 * Net response
 * <p>
 * Created by：wangjian on 2017/12/20 16:12
 */
public class BaseResponse<T> {
    
    public T data;
    @SerializedName("status")
    public int code;
    @SerializedName("message")
    public String msg;
    
}
