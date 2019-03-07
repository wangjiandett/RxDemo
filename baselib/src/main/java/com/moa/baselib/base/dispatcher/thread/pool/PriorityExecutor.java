package com.moa.baselib.base.dispatcher.thread.pool;

import android.os.Looper;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Priority thread Executor
 * <p>
 * Created by：wangjian on 2018/1/16 15:38
 */
public class PriorityExecutor extends ThreadPoolExecutor {
    
    // Core pool size
    private static final int CORE_POOL_SIZE = 3;
    // Max pool size
    private static final int MAXIMUM_POOL_SIZE = 5;
    // Queue size
    private static final int MAX_QUEUE_SIZE = 30;
    // 保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
    private static final long KEEP_ALIVE = 3L;
    // 主要获取添加任务
    private static final AtomicLong ID = new AtomicLong(0);
    
    public PriorityExecutor() {
        this(CORE_POOL_SIZE, true);
    }
    
    /**
     * default core pool size is 3
     *
     * @param fifo Is the queue first in first out
     */
    public PriorityExecutor(boolean fifo) {
        this(CORE_POOL_SIZE, fifo);
    }
    
    /**
     * @param corePoolSize core pool size
     * @param fifo Is the queue first in first out
     */
    public PriorityExecutor(int corePoolSize, boolean fifo) {
        this(corePoolSize, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new PriorityBlockingQueue<Runnable>(MAX_QUEUE_SIZE, fifo ? FIFO : LIFO), THREAD_FACTORY);
    }
    
    public PriorityExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }
    
    @Override
    public void execute(Runnable runnable) {
        if (runnable instanceof PriorityRunnable) {
            ((PriorityRunnable) runnable).id = ID.getAndIncrement();
        }
        super.execute(runnable);
    }
    
    /**
     * The thread factory
     */
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        AtomicInteger mCount = new AtomicInteger(1);
        
        @Override
        public Thread newThread(final Runnable r) {
            
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    r.run();
                }
            };
            return new Thread(runnable, "ConnectTask #" + this.mCount.getAndIncrement());
        }
    };
    
    /**
     * First in first out
     */
    private static final Comparator<Runnable> FIFO = new Comparator<Runnable>() {
        @Override
        public int compare(Runnable lhs, Runnable rhs) {
            if (lhs instanceof PriorityRunnable && rhs instanceof PriorityRunnable) {
                PriorityRunnable lpr = ((PriorityRunnable) lhs);
                PriorityRunnable rpr = ((PriorityRunnable) rhs);
                int result = lpr.priority.ordinal() - rpr.priority.ordinal();
                return result == 0 ? (int) (lpr.id - rpr.id) : result;
            }
            else {
                return 0;
            }
        }
    };
    
    /**
     * Last in first out
     */
    private static final Comparator<Runnable> LIFO = new Comparator<Runnable>() {
        @Override
        public int compare(Runnable lhs, Runnable rhs) {
            if (lhs instanceof PriorityRunnable && rhs instanceof PriorityRunnable) {
                PriorityRunnable lpr = ((PriorityRunnable) lhs);
                PriorityRunnable rpr = ((PriorityRunnable) rhs);
                int result = lpr.priority.ordinal() - rpr.priority.ordinal();
                return result == 0 ? (int) (rpr.id - lpr.id) : result;
            }
            else {
                return 0;
            }
        }
    };
}
