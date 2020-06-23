package com.moa.baselib.base.net;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moa.baselib.utils.GsonHelper;
import com.moa.baselib.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

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

    public ApiService(String baseUrl) {

        // 1 print log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    // 防止出现%号，解析异常
                    String text = URLDecoder.decode(message.replaceAll("%(?![0-9a-fA-F]{2})", "%25") ,"UTF-8");
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()//
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//
            .addInterceptor(interceptor)//
//            .addNetworkInterceptor(new HttpCacheInterceptor())//
//            .cache(cache)//
            .build();
        
        // 4 gson
        Gson gson = GsonHelper.gson;
        
        mRetrofit = new Retrofit.Builder()//
            .client(okHttpClient)//
            .addConverterFactory(GsonConverterFactory.create(gson))//
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
            .baseUrl(baseUrl)// base url
            .build();
    }

    /**
     * 获取接口封装类
     *
     * @param serviceClass 接口类
     * @param <T> 封装类型
     * @return 封装接口类型
     */
    public <T> T getApis(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
