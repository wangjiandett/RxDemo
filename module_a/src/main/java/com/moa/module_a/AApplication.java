package com.moa.module_a;

import com.moa.baselib.lifecycle.ComponentApplication;
import com.moa.baselib.lifecycle.ComponentPriority;
import com.moa.baselib.utils.LogUtils;

public class AApplication extends ComponentApplication {

    private static final String TAG = "AApplication";

    @Override
    public int level() {
        return ComponentPriority.LEVEL_MID;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "AApplication onCreate");
    }
}
