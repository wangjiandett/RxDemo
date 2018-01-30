package com.moa.rxdemo.base.net.exception;

/**
 * the server error
 * <p>
 * Created by：wangjian on 2017/12/21 15:39
 */
public class ServerException extends RuntimeException {
    public ServerException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    public int code;
    public String message;
}