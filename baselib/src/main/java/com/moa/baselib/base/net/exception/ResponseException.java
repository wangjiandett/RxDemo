package com.moa.baselib.base.net.exception;

/**
 * http request exception
 * <p>
 * Created by：wangjian on 2017/12/21 15:39
 */
public class ResponseException extends Exception {
    // error code
    public int code;
    // error message
    public String message;
    
    public ResponseException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
