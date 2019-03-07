package com.moa.baselib.lifecycle;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;


/**
 * 组件生命周期基类，组件module里面如果有需要在app启动时进行一些初始化工作
 * 需要继承该类，并将其添加到module对应的AndroidManifest.xml中，
 * <p>
 * 像下面这样
 * <br/>
 * <p>
 * &#x3C;application&#x3E;
 * ...
 * <br/>
 * &#x3C;meta-data
 * android:name="bamboo.sample.tasks.component.TasksComponentApp"
 * android:value="COMPONENT_APPLICATION" /&#x3E;
 * <br/>
 * &#x3C;/application&#x3E;
 * <br/>
 * 其中COMPONENT_APPLICATION作为value,且是固定值，你的自定义的类TasksComponentApp作为name
 * {@link ComponentAppResolve#COMPONENT_META_NAME}
 */
public class ComponentApplication implements IComponentLifeCycle {

    private Application application;

    public ComponentApplication() {
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public int level() {
        return ComponentPriority.LEVEL_LOW;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void attachBaseContext(Context baseContext) {

    }

    @Override
    public final Application getApplication() {
        return application;
    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

}
