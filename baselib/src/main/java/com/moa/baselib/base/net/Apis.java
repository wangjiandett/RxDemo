package com.moa.baselib.base.net;

/**
 * 封装请求api接口
 *
 * @param <T> api接口类型
 */
public interface Apis<T> {

    /**
     * api请求基地址
     *
     * @return url
     */
    String getUrl();

    /**
     * api请求类型的class
     *
     * @return
     */
    Class<T> getApiClass();
}
