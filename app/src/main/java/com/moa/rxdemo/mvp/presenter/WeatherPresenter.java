package com.moa.rxdemo.mvp.presenter;

import com.moa.rxdemo.base.net.SimpleValueCallback;
import com.moa.rxdemo.mvp.bean.Weather;
import com.moa.rxdemo.mvp.contract.WeatherContract;
import com.moa.rxdemo.mvp.model.IWeatherModel;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 15:45
 */
public class WeatherPresenter implements WeatherContract.IWeatherPresenter {
    
    private WeatherContract.IWeatherView iWeatherView;
    private IWeatherModel iWeatherModel;
    
    public WeatherPresenter(WeatherContract.IWeatherView iWeatherView, IWeatherModel iWeatherModel) {
        this.iWeatherView = iWeatherView;
        this.iWeatherModel = iWeatherModel;
    }
    
    @Override
    public void getWeatherList(String city) {
        iWeatherModel.loadWeatherList(city, new SimpleValueCallback<Weather>() {
            @Override
            public void onSuccess(Weather value) {
                iWeatherView.onSuccess(value);
            }
            
            @Override
            public void onFail(String msg) {
                iWeatherView.onFail(msg);
            }
        });
    }
}
