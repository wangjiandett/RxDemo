package com.moa.module_b;

import com.moa.baselib.lifecycle.ComponentApplication;
import com.moa.baselib.lifecycle.ComponentPriority;
import com.moa.baselib.utils.LogUtils;

public class BApplication extends ComponentApplication {

    private static final String TAG = "BApplication";

    @Override
    public int level() {
        return ComponentPriority.LEVEL_MID;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "BApplication onCreate");
    }
}
