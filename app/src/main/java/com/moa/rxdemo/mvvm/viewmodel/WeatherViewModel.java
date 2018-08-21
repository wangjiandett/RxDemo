package com.moa.rxdemo.mvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.moa.rxdemo.mvp.bean.Weather;
import com.moa.rxdemo.mvp.model.WeatherModelImpl;
import com.moa.rxdemo.mvvm.base.BaseViewModel;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class WeatherViewModel extends BaseViewModel<Weather>{
    
    public MutableLiveData<Weather> weatherMutableLiveData;
    
    public WeatherViewModel(){
        super();
        weatherMutableLiveData = getLiveData();
    }
    
    public void loadData(String city){
        new WeatherModelImpl().loadWeatherList(city, this);
    }
    
    @Override
    public void onSuccess(Weather value) {
        super.onSuccess(value);
        weatherMutableLiveData.setValue(value);
    }
    
    public MutableLiveData<Weather> getWeatherMutableLiveData() {
        return weatherMutableLiveData;
    }
}
