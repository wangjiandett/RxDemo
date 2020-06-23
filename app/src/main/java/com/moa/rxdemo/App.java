package com.moa.rxdemo;

import android.content.Context;

import com.moa.baselib.BaseApp;
import com.moa.rxdemo.db.AppDatabase;
import com.moa.rxdemo.db.DataRepository;

/**
 * 项目地址 <a href="https://github.com/wangjiandett/RxDemo">https://github.com/wangjiandett/RxDemo</a>
 * 程序入口
 * <p>
 * Created by：wangjian on 2017/12/20 15:45
 */
public class App extends BaseApp {

    private static final String TAG = "App";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 初始化滴滴插件管理器
        //PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 以下2个方法获得对room数据库管理是实例
    public static DataRepository getDataRepository() {
        return DataRepository.getInstance(getAppDatabase());
    }

    public static AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(getAppContext());
    }

}
