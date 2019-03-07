package com.moa.baselib.lifecycle;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 从Manifest文件中查找配置的LibraryApplication并获取到所有module应用中的ComponentApplication集合
 */
public class ComponentAppResolve {

    private static boolean inited = false;

    /**
     * 用作过滤mata-data的value为ComponentApplication的key
     */
    private static final String COMPONENT_META_NAME = "COMPONENT_APPLICATION";

    private static final List<ComponentApplication> APPLICATION_LIST = new ArrayList<>();


    /**
     * 获取到所有module应用中的ComponentApplication集合
     *
     * @param context
     * @return
     */
    static List<ComponentApplication> findAllAppLibrary(Application context) {
        if (!inited) {
            synchronized (ComponentAppResolve.class) {
                if (!inited) {
                    inited = true;
                    searchAllLibraryApplication(context);
                    Collections.sort(APPLICATION_LIST, new ComponentLevelComparator());
                }
            }
        }
        return APPLICATION_LIST;
    }

    /**
     * 从Manifest文件中查找配置的LibraryApplication
     *
     * @param context
     */
    private static void searchAllLibraryApplication(Application context) {
        ApplicationInfo ai;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                String value = bundle.getString(key);
                if (value != null && value.equals(COMPONENT_META_NAME)) {
                    try {
                        Class aClass = Class.forName(key);
                        addApplibrary(aClass, context);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addApplibrary(Class<? extends ComponentApplication> applibraryClass, Application application) {
        try {
            ComponentApplication app = applibraryClass.newInstance();
            app.setApplication(application);
            APPLICATION_LIST.add(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
