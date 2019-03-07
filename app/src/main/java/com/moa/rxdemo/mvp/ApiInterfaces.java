package com.moa.rxdemo.mvp;

import com.moa.baselib.base.net.BaseResponse;
import com.moa.rxdemo.mvp.bean.SwipeItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 统一管理网络请求接口
 */
public interface ApiInterfaces {

    // 博客地址很多接口
    // https://www.jianshu.com/p/e6f072839282
    String BASE_URL = "https://www.apiopen.top/";

    //String API_SERVER_URL = "http://cache.video.iqiyi.com/jp/avlist/202861101/1/?callback=jsonp9";


    @GET("meituApi")
    Observable<BaseResponse<List<SwipeItem>>> getSwipeList(@Query("page") int page);

    @GET("meituApi?page=1")
    Observable<BaseResponse<List<SwipeItem>>> getMeituList();

}
