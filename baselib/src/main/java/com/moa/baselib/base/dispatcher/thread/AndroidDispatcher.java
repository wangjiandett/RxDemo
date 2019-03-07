package com.moa.baselib.base.dispatcher.thread;

import android.os.Handler;
import android.os.HandlerThread;

import com.moa.baselib.base.dispatcher.thread.pool.ThreadPriority;


public class AndroidDispatcher {
    
    private Handler handler;
    
    public AndroidDispatcher(String name, ThreadPriority priority) {
    
        HandlerThread handlerThread = new HandlerThread(name);
        switch (priority) {
            case HIGH:
                handlerThread.setPriority(Thread.MAX_PRIORITY);
                break;
            case LOW:
                handlerThread.setPriority(Thread.MIN_PRIORITY);
                break;
            default:
            case NORMAL:
                handlerThread.setPriority(Thread.NORM_PRIORITY);
                break;
        }
        handlerThread.start();
        
        // Wait for Looper ready
        while (handlerThread.getLooper() == null) {
        
        }
        
        handler = new Handler(handlerThread.getLooper());
    }
    
    public void dispatchNow(Runnable runnable) {
        handler.post(runnable);
    }
    
    public void dispatchDelay(Runnable runnable, long delay){
        handler.postDelayed(runnable, delay);
    }
}
