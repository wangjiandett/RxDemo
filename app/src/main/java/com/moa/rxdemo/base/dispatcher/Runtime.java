package com.moa.rxdemo.base.dispatcher;

import com.moa.rxdemo.base.dispatcher.thread.AndroidDispatcher;
import com.moa.rxdemo.base.dispatcher.thread.AndroidMainThreadDispatcher;
import com.moa.rxdemo.base.dispatcher.thread.ThreadPool;
import com.moa.rxdemo.base.dispatcher.thread.pool.ThreadPriority;

/**
 * Dispatcher event
 * <p>
 * Created by：wangjian on 2018/1/12 16:25
 */
public class Runtime {
    private static final String DISPATCHER_NAME = "android_dispatcher";
    
    private static final AndroidDispatcher ANDROID_DISPATCHER = new AndroidDispatcher(DISPATCHER_NAME,
        ThreadPriority.NORMAL);
    
    private static final AndroidMainThreadDispatcher MAIN_THREAD_DISPATCHER = new AndroidMainThreadDispatcher();
    
    private static final ThreadPool THREAD_POOL = new ThreadPool();
    
    /**
     * Post event async
     *
     * @param runnable
     */
    public static void dispatchNow(Runnable runnable) {
        ANDROID_DISPATCHER.dispatchNow(runnable);
    }
    
    /**
     * Post event to main thread
     *
     * @param runnable
     */
    public void postToMainThread(Runnable runnable) {
        MAIN_THREAD_DISPATCHER.postToMainThread(runnable);
    }
    
    /**
     * Is main thread
     *
     * @return
     */
    public boolean isMainThread() {
        return MAIN_THREAD_DISPATCHER.isMainThread();
    }
    
    /**
     * Post event to thread pool
     *
     * @param runnable
     */
    public void execute(Runnable runnable){
        THREAD_POOL.execute(runnable);
    }
    
}
