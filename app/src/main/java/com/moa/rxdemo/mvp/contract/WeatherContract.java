package com.moa.rxdemo.mvp.contract;


import com.moa.rxdemo.mvp.bean.Weather;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 10:44
 */
public interface WeatherContract {
    
    interface IWeatherPresenter{
        void getWeatherList(String city);
    }
    
    interface IWeatherView{
        void onSuccess(Weather weather);
        void onFail(String msg);
    }
    
}
