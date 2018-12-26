package com.moa.rxdemo.base.net;


import com.moa.rxdemo.mvp.bean.SwipeItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 接口列表
 * <p>
 * Created by：wangjian on 2017/12/20 15:47
 */
public interface Apis {
    
    // 博客地址很多接口
    // https://www.jianshu.com/p/e6f072839282
    
    String API_SERVER_URL = "https://www.apiopen.top/";
    
    //String API_SERVER_URL = "http://cache.video.iqiyi.com/jp/avlist/202861101/1/?callback=jsonp9";
    
    
    @GET("meituApi")
    Observable<BaseResponse<List<SwipeItem>>> getSwipeList(@Query("page") int page);
    
    @GET("meituApi?page=1")
    Observable<BaseResponse<List<SwipeItem>>> getMeituList();
    
    
}
