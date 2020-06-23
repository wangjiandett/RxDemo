package com.moa.rxdemo.net;

import com.moa.baselib.base.net.BaseResponse;
import com.moa.rxdemo.mvp.bean.ForecastBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 统一管理网络请求接口
 */
public interface SwipeApiInterfaces {

    @GET("{cityId}")
    Observable<BaseResponse<ForecastBean.Data>> getSwipeList(@Path("cityId") int page);


}
