package com.moa.baselib.lifecycle;


import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Collection;

/**
 * 控制回调组件的生命周期函数
 *
 * <a href='https://github.com/bambootang/stitch/tree/0.1'>周期函数注册控制，参考自</a>
 */
public final class ComponentLifeHelper {


    private static ComponentLifeRegistry componentLifeRegistry = new ComponentLifeRegistry();

    private ComponentLifeHelper() {
    }

    /**
     * 初始化框架，查询所有注册的组件，并自动调用regiestComponentLifeCycle注入组件生命周期回调
     *
     * @param application
     */
    public static void init(Application application) {
        componentLifeRegistry.registerFromManifest(application);
    }

    /**
     * 获取给定组件类型已注册的实例
     *
     * @param clasz
     * @return
     */
    public static ComponentApplication getComponentApplication(Class clasz) {
        return componentLifeRegistry.search(clasz);
    }

    /**
     * 调用所有组件的onCreate生命周期方法
     *
     * @see IComponentLifeCycle#onCreate()
     */
    public static void onCreate() {
        Collection<ComponentApplication> componentApplications = componentLifeRegistry.getAll();
        for (ComponentApplication componentApplication : componentApplications) {
            componentApplication.onCreate();
        }
    }

    /**
     * 调用所有组件的attachBaseContext生命周期方法
     *
     * @see IComponentLifeCycle#attachBaseContext(Context)
     */
    public static void attachBaseContext(Context baseContext) {
        Collection<ComponentApplication> componentApplications = componentLifeRegistry.getAll();
        for (ComponentApplication componentApplication : componentApplications) {
            componentApplication.attachBaseContext(baseContext);
        }
    }

    /**
     * 调用所有组件的onTrimMemory生命周期方法
     *
     * @see IComponentLifeCycle#onTrimMemory(int)
     */
    public static void onTrimMemory(int level) {
        Collection<ComponentApplication> componentApplications = componentLifeRegistry.getAll();
        for (ComponentApplication componentApplication : componentApplications) {
            componentApplication.onTrimMemory(level);
        }
    }

    /**
     * 调用所有组件的onLowMemory()生命周期方法
     *
     * @see IComponentLifeCycle#onLowMemory()
     */
    public static void onLowMemory() {
        Collection<ComponentApplication> componentApplications = componentLifeRegistry.getAll();
        for (ComponentApplication componentApplication : componentApplications) {
            componentApplication.onLowMemory();
        }
    }

    /**
     * 调用所有组件的onConfigurationChanged生命周期方法
     *
     * @see IComponentLifeCycle#onConfigurationChanged(Configuration)
     */
    public static void onConfigurationChanged(Configuration newConfig) {
        Collection<ComponentApplication> componentApplications = componentLifeRegistry.getAll();
        for (ComponentApplication componentApplication : componentApplications) {
            componentApplication.onConfigurationChanged(newConfig);
        }
    }
}
