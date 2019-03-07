package com.moa.rxdemo;

import android.content.Context;

import com.didi.virtualapk.PluginManager;
import com.moa.baselib.BaseApp;
import com.moa.baselib.base.net.ApiService;
import com.moa.baselib.base.net.Apis;
import com.moa.rxdemo.db.AppDatabase;
import com.moa.rxdemo.db.DataRepository;
import com.moa.rxdemo.mvp.ApiInterfaces;

/**
 * 项目地址 <a href="https://github.com/wangjiandett/RxDemo">https://github.com/wangjiandett/RxDemo</a>
 * 程序入口
 * <p>
 * Created by：wangjian on 2017/12/20 15:45
 */
public class App extends BaseApp {

    private static final String TAG = "App";

    private static ApiInterfaces interfaces;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 初始化滴滴插件管理器
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRequestApi();
    }

    /**
     * 初始化网络请求的api
     */
    private void initRequestApi() {
        ApiService<ApiInterfaces> apiService = new ApiService<ApiInterfaces>(new Apis<ApiInterfaces>() {

            @Override
            public String getUrl() {
                return ApiInterfaces.BASE_URL;
            }

            @Override
            public Class<ApiInterfaces> getApiClass() {
                return ApiInterfaces.class;
            }
        });

        interfaces = apiService.getApis();
    }

    /**
     * 获取网络请求接口
     */
    public static ApiInterfaces getInterfaces() {
        return interfaces;
    }


    // 以下2个方法获得对room数据库管理是实例
    public static DataRepository getDataRepository() {
        return DataRepository.getInstance(getAppDatabase());
    }

    public static AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(getAppContext());
    }

}
