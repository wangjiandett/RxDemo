package com.moa.rxdemo;

import android.app.Application;

import com.moa.rxdemo.base.ExceptionHandler;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/20 15:45
 */
public class MyApplication extends Application {
    
    private static MyApplication instance;
    
    
    @Override
    public void onCreate() {
        super.onCreate();
        // 捕获crash日志
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getApplicationContext()));
        instance = this;
    }
    
    public static MyApplication getInstance() {
        return instance;
    }
}
