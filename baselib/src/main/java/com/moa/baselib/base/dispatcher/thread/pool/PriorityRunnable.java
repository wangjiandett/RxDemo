package com.moa.baselib.base.dispatcher.thread.pool;

/**
 * 带有优先级的Runnable类型
 * <p>
 * Created by：wangjian on 2018/1/16 15:39
 */
public class PriorityRunnable implements Runnable {
    //任务优先级
    public final ThreadPriority priority;
    //任务真正执行者
    private final Runnable runnable;
    //任务唯一标示
    long id;
    
    public PriorityRunnable(ThreadPriority priority, Runnable runnable) {
        this.priority = priority == null ? ThreadPriority.NORMAL : priority;
        this.runnable = runnable;
    }
    
    @Override
    public final void run() {
        this.runnable.run();
    }
}
