/*
 * Copyright (C) 2015 Actor LLC. <https://actor.im>
 */

package com.moa.baselib.base.dispatcher.thread;

import android.os.Handler;
import android.os.Looper;

public class AndroidMainThreadDispatcher {
    
    private Handler handler = new Handler(Looper.getMainLooper());
    
    public void postToMainThread(Runnable runnable) {
        handler.post(runnable);
    }
    
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
    
}
