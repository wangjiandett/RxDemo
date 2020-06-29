package com.moa.baselib.base.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moa.baselib.utils.GsonHelper;
import com.moa.baselib.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 初始化网络请求类
 * <p>
 * Created by：wangjian on 2017/12/20 15:42
 */
public class ApiService {


    private static final String TAG = "ApiService";
    // 请求超时时间20s
    private static final int DEFAULT_TIMEOUT = 20 * 1000;
    private Retrofit mRetrofit;

    private ApiService(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    /**
     * 获取接口封装类
     *
     * @param serviceClass 接口类
     * @param <T>          封装类型
     * @return 封装接口类型
     */
    public <T> T getApis(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

    public static class Builder {

        private String baseUrl;
        private OkHttpClient.Builder okHttpBuilder;
        private Interceptor[] mInterceptors;
        private Gson mGson;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder okHttpBuilder(OkHttpClient.Builder okHttpBuilder) {
            this.okHttpBuilder = okHttpBuilder;
            return this;
        }

        public Builder interceptor(Interceptor... mInterceptors) {
            this.mInterceptors = mInterceptors;
            return this;
        }

        public Builder gson(Gson mGson) {
            this.mGson = mGson;
            return this;
        }

        public ApiService build() {
            if (TextUtils.isEmpty(this.baseUrl)) {
                throw new NullPointerException("baseUrl must not be null or empty");
            }
            if (this.mGson == null) {
                mGson = GsonHelper.gson;
            }
            if (okHttpBuilder == null) {
                okHttpBuilder = getDefaultBuilder();
            }

            if (mInterceptors != null) {
                for (Interceptor interceptor : mInterceptors){
                    okHttpBuilder.addInterceptor(interceptor);
                }
            }

            Retrofit mRetrofit = new Retrofit.Builder()//
                    .client(okHttpBuilder.build())//
                    .addConverterFactory(GsonConverterFactory.create(mGson))//
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                    .baseUrl(baseUrl)// base url
                    .build();

            return new ApiService(mRetrofit);
        }
    }


    public static OkHttpClient.Builder getDefaultBuilder() {
        // 1 print log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    // 防止出现%号，解析异常
                    String text = URLDecoder.decode(message.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
                    LogUtils.d(TAG, text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.e(TAG, message);
                }
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 2 catch data
        //File cacheFile = new File(BaseApp.getInstance().getCacheDir(), "cache");
        //Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        // 3 okhttp client

        return new OkHttpClient.Builder()//
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)//
                //            .addNetworkInterceptor(new HttpCacheInterceptor())//
                //            .cache(cache)//
                 ;

    }

}
