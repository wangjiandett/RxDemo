package com.moa.rxdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.moa.rxdemo.base.ExceptionHandler;
import com.moa.rxdemo.base.db.AppDatabase;
import com.moa.rxdemo.base.db.DataRepository;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 项目地址 <a href="https://github.com/wangjiandett/RxDemo">https://github.com/wangjiandett/RxDemo</a>
 * 程序入口
 * <p>
 * Created by：wangjian on 2017/12/20 15:45
 */
public class App extends Application {
    
    // 用于缓存当前所有的activity
    private static final ArrayList<WeakReference<Activity>> activityList = new ArrayList<>();
    // 是否正在结束所有activity
    private static boolean isFinishingAll;
    private static App instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        // 捕获crash日志
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getApplicationContext()));
        instance = this;
    }
    
    private static App getInstance() {
        return instance;
    }
    
    public static Context getContext() {
        return getInstance();
    }
    
    public static void addActivity(Activity activity) {
        activityList.add(new WeakReference<>(activity));
    }
    
    public static void removeActivity(Activity activity) {
        // 防止调用finishAllActivity时调用此处，导致activityList数据不一致异常
        if(!isFinishingAll){
            for (WeakReference<Activity> reference : activityList) {
                if (reference != null) {
                    if (reference.get() == activity) {
                        activityList.remove(reference);
                        // 此处必须跳出，不然报错
                        break;
                    }
                }
            }
        }
    }
    
    public static void finishAllActivity() {
        isFinishingAll = true;
        for (WeakReference<Activity> reference : activityList) {
            if (reference != null) {
                Activity activity = reference.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    
        activityList.clear();
        isFinishingAll = false;
    }
    
    // 以下2个方法获得对room数据库管理是实例
    public static DataRepository getDataRepository() {
        return DataRepository.getInstance(getAppDatabase());
    }
    
    public static AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(getContext());
    }
}
