package com.moa.rxdemo.mvp.model;

import com.moa.baselib.base.net.mvp.ValueCallback;
import com.moa.rxdemo.mvp.bean.ForecastBean;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 10:59
 */
public interface ISwipeModel {
    
    void loadSwipeList(int cityId, ValueCallback<ForecastBean.Data> callback);
}
