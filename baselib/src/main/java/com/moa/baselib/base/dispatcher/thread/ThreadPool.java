package com.moa.baselib.base.dispatcher.thread;


import com.moa.baselib.base.dispatcher.thread.pool.PriorityExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <a href="https://www.cnblogs.com/whoislcj/p/5610903.html">参考地址 https://www.cnblogs.com/whoislcj/p/5610903.html</a>
 * <p>
 * Created by：wangjian on 2018/1/16 14:06
 */
public class ThreadPool {
    
    private PriorityExecutor mExecutor;
    
    public ThreadPool() {
        
        //ThreadPoolExecutor对象初始化时，不创建任何执行线程，当有新任务进来时，才会创建执行线程。构造ThreadPoolExecutor对象时，需要配置该对象的核心线程池大小和最大线程池大小
        //1. 当目前执行线程的总数小于核心线程大小时，所有新加入的任务，都在新线程中处理。
        //2. 当目前执行线程的总数大于或等于核心线程时，所有新加入的任务，都放入任务缓存队列中。
        //3. 当目前执行线程的总数大于或等于核心线程，并且缓存队列已满，同时此时线程总数小于线程池的最大大小，那么创建新线程，加入线程池中，协助处理新的任务。
        //4. 当所有线程都在执行，线程池大小已经达到上限，并且缓存队列已满时，就rejectHandler拒绝新的任务。
        mExecutor = new PriorityExecutor();
        
        //1. AbortPolicy 直接丢弃新任务，并抛出RejectedExecutionException通知调用者，任务被丢弃
        //2. CallerRunsPolicy 用调用者的线程，执行新的任务，如果任务执行是有严格次序的，请不要使用此policy
        //3. DiscardPolicy 静默丢弃任务，不通知调用者，在处理网络报文时，可以使用此任务，静默丢弃没有几乎处理的报文
        //4. DiscardOldestPolicy 丢弃最旧的任务，处理网络报文时，可以使用此任务，因为报文处理是有时效的，超过时效的，都必须丢弃
        mExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
    }
    
    public void execute(Runnable runnable){
        mExecutor.execute(runnable);
    }
    
}
