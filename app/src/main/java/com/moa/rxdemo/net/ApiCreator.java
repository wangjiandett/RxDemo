package com.moa.rxdemo.net;

import com.moa.baselib.base.net.ApiService;

/**
 * data creator helper
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class ApiCreator {

    /**
     * base url
     */
    private static final String BASE_URL = "http://t.weather.sojson.com/api/weather/city/";

    /**
     * success response code
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * net request config
     */
    private static final ApiService APISERVICE = new ApiService(BASE_URL);

    //
    //  swipe接口类型
    //
    public static SwipeApiInterfaces swipeInterfaces = APISERVICE.getApis(SwipeApiInterfaces.class);



}
