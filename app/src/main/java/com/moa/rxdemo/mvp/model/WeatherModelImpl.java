package com.moa.rxdemo.mvp.model;


import com.moa.rxdemo.base.net.BaseModel;
import com.moa.rxdemo.base.net.ValueCallback;
import com.moa.rxdemo.mvp.bean.Weather;

/**
 * load weather
 * <p>
 * <p>
 * Created by：wangjian on 2017/12/21 11:00
 */
public class WeatherModelImpl extends BaseModel<Weather> implements IWeatherModel {
    
    @Override
    public void loadWeatherList(String city, ValueCallback<Weather> callback) {
        this.mCallback = callback;
        requestValue(apis.getWeatherInfo(city));
    }
    
    @Override
    protected void onSuccess(Weather value) {
        mCallback.onSuccess(value);
    }
    
    @Override
    protected void onFail(String msg) {
        mCallback.onFail(msg);
    }
}
