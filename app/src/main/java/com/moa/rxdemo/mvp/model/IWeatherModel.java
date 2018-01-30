package com.moa.rxdemo.mvp.model;

import com.moa.rxdemo.base.net.ValueCallback;
import com.moa.rxdemo.mvp.bean.Weather;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 10:59
 */
public interface IWeatherModel {
    
    void loadWeatherList(String city, ValueCallback<Weather> callback);
}
