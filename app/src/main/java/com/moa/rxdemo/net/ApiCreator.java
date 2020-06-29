package com.moa.rxdemo.net;

import com.moa.baselib.base.net.ApiService;
import com.moa.baselib.base.net.DownloadInterceptor;
import com.moa.baselib.base.net.ProgressResultListener;
import com.moa.baselib.base.net.UploadInterceptor;

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
     * net request config
     */
    private static final ApiService APISERVICE = new ApiService.Builder().baseUrl(BASE_URL).build();

    //
    //  swipe接口类型
    //
    public static SwipeApiInterfaces swipeInterfaces = APISERVICE.getApis(SwipeApiInterfaces.class);

    /**
     * 下载文件接口
     *
     * @param listener 进度监听
     * @return
     */
    public static UploadApiInterfaces getDownloadFileInterface(ProgressResultListener listener){
        ApiService test =  new ApiService.Builder()
                .baseUrl("https://imtt.dd.qq.com/")
                // 监听下载进度，需要设置拦截器
                .interceptor(new DownloadInterceptor(listener))
                .build();
        return test.getApis(UploadApiInterfaces.class);
    }

    /**
     * 上传文件接口
     *
     * @param listener 进度监听
     * @return
     */
    public static UploadApiInterfaces getUploadFileInterface(ProgressResultListener listener){
        ApiService test =  new ApiService.Builder()
                .baseUrl("http://app.shylh1d3.com/lanhan-interface/")
                // 监听下载进度，需要设置拦截器
                .interceptor(new UploadInterceptor(listener))
                .build();
        return test.getApis(UploadApiInterfaces.class);
    }


}
