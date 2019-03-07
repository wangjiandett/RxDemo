package com.moa.baselib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.alibaba.android.arouter.launcher.ARouter;
import com.moa.baselib.base.ExceptionHandler;
import com.moa.baselib.lifecycle.ComponentLifeHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * 1.配置UncaughtException处理器<br/>
 * 2.初始化ARouter<br/>
 * 3.初始化组件application<br/>
 * 4.缓存和移除当前界面<br/>
 *
 * 项目地址 <a href="https://github.com/wangjiandett/RxDemo">程序入口</a>
 *
 * <p>
 * Created by：wangjian on 2017/12/20 15:45
 */
public class BaseApp extends Application {

    // 用于缓存当前所有的activity
    private static final ArrayList<WeakReference<Activity>> activityList = new ArrayList<>();
    // 是否正在结束所有activity
    private static boolean isFinishingAll;

    private boolean isDebug = true;
    private static BaseApp mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        // 捕获crash日志
        ExceptionHandler.handUncaughtException(getApplicationContext());
        initARouter();
        ComponentLifeHelper.onCreate();
    }

    /**
     * 初始化ARouter,供组件之间交互使用
     */
    protected void initARouter() {
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    public static Application getAppContext() {
        return mAppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ComponentLifeHelper.init(this);
        ComponentLifeHelper.attachBaseContext(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ComponentLifeHelper.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ComponentLifeHelper.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ComponentLifeHelper.onConfigurationChanged(newConfig);
    }


    public static void addActivity(Activity activity) {
        activityList.add(new WeakReference<>(activity));
    }

    public static void removeActivity(Activity activity) {
        // 防止调用finishAllActivity时activity销毁时调用此处，导致activityList数据不一致异常
        if(!isFinishingAll){
            ListIterator<WeakReference<Activity>> iterator = activityList.listIterator();
            while (iterator.hasNext()) {
                WeakReference<Activity> item = iterator.next();
                if (item.get() == activity) {
                    iterator.remove();
                    return;
                }
            }

//            for (WeakReference<Activity> reference : activityList) {
//                if (reference != null) {
//                    if (reference.get() == activity) {
//                        activityList.remove(reference);
//                        // 此处必须跳出，不然报错
//                       // break;
//                    }
//                }
//            }
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
}
