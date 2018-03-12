package com.moa.rxdemo.base.net;


import com.moa.rxdemo.mvp.bean.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 接口列表
 * <p>
 * Created by：wangjian on 2017/12/20 15:47
 */
public interface Apis {
    
    String API_SERVER_URL = "http://wthrcdn.etouch.cn/";
    
    
    // 接口列表
    String api = "https://www.bejson.com/knownjson/webInterface/";
    
    //String API_SERVER_URL = "http://cache.video.iqiyi.com/jp/avlist/202861101/1/?callback=jsonp9";
    
    
    @GET("weather_mini")
    Observable<BaseResponse<Weather>> getWeatherInfo(@Query("citykey") String citykey);
    
    
}
