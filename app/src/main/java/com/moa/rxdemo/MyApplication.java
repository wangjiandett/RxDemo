package com.moa.rxdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.moa.rxdemo.base.ExceptionHandler;

import java.util.ArrayList;

/**
 * 项目地址 <a href="https://github.com/wangjiandett/RxDemo">https://github.com/wangjiandett/RxDemo</a>
 * 程序入口
 * <p>
 * Created by：wangjian on 2017/12/20 15:45
 */
public class MyApplication extends Application {
    
    // 用于缓存当前所有的activity
    private static final ArrayList<Activity> activityList = new ArrayList<>();
    
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
    
    public static Context getContext(){
        return getInstance();
    }
    
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }
    
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    
    public static void finishAllActivity(){
        for (Activity activity : activityList){
            if(activity != null && !activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
