package com.moa.module_c;

import com.moa.baselib.lifecycle.ComponentApplication;
import com.moa.baselib.lifecycle.ComponentPriority;
import com.moa.baselib.utils.LogUtils;

public class CApplication extends ComponentApplication {

    private static final String TAG = "BApplication";

    @Override
    public int level() {
        return ComponentPriority.LEVEL_MID;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "CApplication onCreate");
    }
}
