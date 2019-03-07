package com.moa.baselib.base.net.exception;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.moa.baselib.BaseApp;
import com.moa.baselib.R;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * Deal all the exception
 * <p>
 * Created by：wangjian on 2017/12/21 14:57
 */
public class ExceptionHandle {
    
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    
    public static ResponeException handleException(Throwable e) {
        ResponeException ex;
        Context context = BaseApp.getAppContext();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeException(e, Error.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = context.getString(R.string.tt_error_net);
                    break;
            }
            return ex;
        }
        else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeException(resultException, resultException.code);
            ex.message = resultException.getMessage();
            return ex;
        }
        else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ex = new ResponeException(e, Error.PARSE_ERROR);
            ex.message = context.getString(R.string.tt_error_parse);
            return ex;
        }
        else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            ex = new ResponeException(e, Error.NETWORK_ERROR);
            ex.message = context.getString(R.string.tt_error_connect);
            return ex;
        }
        else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeException(e, Error.SSL_ERROR);
            ex.message = context.getString(R.string.tt_error_ssl);
            return ex;
        }
        else if (e instanceof IllegalArgumentException) {
            ex = new ResponeException(e, Error.DATA_ERROR);
            ex.message = context.getString(R.string.tt_error_data_error);
            return ex;
        }
        else {
            ex = new ResponeException(e, Error.UNKNOWN);
            ex.message = context.getString(R.string.tt_error_unknow);
            return ex;
        }
    }
    
    
    /**
     * 约定异常
     */
    class Error {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 响应数据出错，无法解析
         */
        public static final int DATA_ERROR = 1004;
        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
    }
    
}
