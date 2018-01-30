package com.moa.rxdemo.base.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moa.rxdemo.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Init the http request
 * <p>
 * Created by：wangjian on 2017/12/20 15:42
 */
public class ApiService {
    
    
    private static final String TAG = "ApiService";
    // 20s
    private static final int DEFAULT_TIMEOUT = 20 * 1000;
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static ApiService apiService;
    private Apis apis;
    
    private ApiService() {
        
        // 1 print log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    LogUtils.d(TAG, text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.e(TAG, message);
                }
            }
        });
        
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 2 catch data
        //File cacheFile = new File(MyApplication.getInstance().getCacheDir(), "cache");
        //Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        
        // 3 okhttp client
        OkHttpClient okHttpClient = new OkHttpClient.Builder()//
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//
            .addInterceptor(interceptor)//
            //.addNetworkInterceptor(new HttpCacheInterceptor())//
            //.cache(cache)//
            .build();
        
        // 4 gson
        Gson gson = new GsonBuilder().setDateFormat(DATA_FORMAT).serializeNulls().create();
        
        Retrofit retrofit = new Retrofit.Builder()//
            .client(okHttpClient)//
            .addConverterFactory(GsonConverterFactory.create(gson))//
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
            .baseUrl(Apis.API_SERVER_URL)//
            .build();
        apis = retrofit.create(Apis.class);
    }
    
    public static Apis getApis() {
        if (apiService == null) {
            apiService = new ApiService();
        }
        
        return apiService.apis;
    }
}
