package com.example.order.developtools.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工具类
 * @author business
 */
public class ThreadUtils {

    /** Tag and debug flag for trace. */
    private static final String TAG = ThreadUtils.class.getSimpleName();
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Executor sBackgroundThreadPool;

    /**
     * 在主线程中调用
     *
     * @param runnable runnable
     */
    public static void runOnMainUI(@NonNull final Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            sHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public static void postOnMainUI(Runnable runnable) {
        sHandler.post(runnable);
    }

    /**
     * 将 runnable post到主线程调用
     *
     * @param runnable runnable
     * @param delay    延迟时间
     */
    public static void runOnMainUI(@NonNull final Runnable runnable, long delay) {
        sHandler.postDelayed(runnable, delay);
    }

    /**
     * 放到队列前面，优先执行
     *
     * @param runnable runnable
     */
    public static void runOnMainUIAtFront(@NonNull final Runnable runnable) {
        sHandler.postAtFrontOfQueue(runnable);
    }

    /**
     * 在指定时间执行
     *
     * @param runnable     runnable
     * @param uptimeMillis 指定执行时间
     */
    public static void runOnMainUIAtTime(@NonNull Runnable runnable, long uptimeMillis) {
        sHandler.postAtTime(runnable, uptimeMillis);
    }

    /**
     * 移除主线程中的runnable
     *
     * @param runnable 待移除的runnable
     */
    public static void removeMainUIRunnable(@NonNull Runnable runnable) {
        sHandler.removeCallbacks(runnable);
    }

    /**
     * 移除主线程中的所有runnable和message
     */
    public static void removeMainUICallbacksAndMessages() {
        sHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 在 Idle Handler 里面执行, 会在主线程空闲的时候再执行，执行时间不可控
     * 仅用于比较不重要的后台处理
     * @param idleHandler
     */
    public static void runOnMainThreadIdleHandler(final MessageQueue.IdleHandler idleHandler){
        if(Looper.getMainLooper() == Looper.myLooper()){
            Looper.myQueue().addIdleHandler(idleHandler);
        }else{
            runOnMainUI(new Runnable() {
                @Override
                public void run() {
                    Looper.myQueue().addIdleHandler(idleHandler);
                }
            });
        }
    }

//
//    /**
//     * 使用单线程发起查询
//     * @param runnable
//     */
//    public static void runOnAsyncSingleThread(@NonNull Runnable runnable){
//        sSingleThreadPool.execute(runnable);
//    }

    /**
     * 设置后台执行的线程池
     * @param backgroundExecutor
     */
    public static void setBackgroundExecutor(Executor backgroundExecutor){
        if (sBackgroundThreadPool != null){
            if(sBackgroundThreadPool instanceof ExecutorService){
                ((ExecutorService) sBackgroundThreadPool).shutdown();
            }
        }
        sBackgroundThreadPool = backgroundExecutor;
    }

    /**
     * 使用后台线程发起查询
     * @param runnable
     */
    public static void runOnAsyncBackgroundThread(@NonNull Runnable runnable){
        if(sBackgroundThreadPool == null){
            sBackgroundThreadPool = new ThreadPoolExecutor(1, 4,
                20L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                new ThreadFactory() {
                    private static final String THREAD_PRR_FIX = "ipstore_background_single";
                    //使用默认的ThreadGroup
                    private final ThreadGroup mThreadGroup = (System.getSecurityManager() != null) ?
                            System.getSecurityManager().getThreadGroup() : Thread.currentThread().getThreadGroup();
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        Thread t = new Thread(mThreadGroup, r,
                                THREAD_PRR_FIX + threadNumber.getAndIncrement(),
                                0);
                        if (t.isDaemon()) {
                            t.setDaemon(false);
                        }
                        if (t.getPriority() != Thread.NORM_PRIORITY - 1) {
                            t.setPriority(Thread.NORM_PRIORITY - 1);
                        }
                        return t;
                    }
                }, new DiscardOldestPolicyAndReport());
        }
        sBackgroundThreadPool.execute(runnable);
    }

    /**
     * 获取当前的线程
     * @return 略
     */
    public static Thread getCurrentThread() {
        return Thread.currentThread();
    }

    /**
     * 获取当前的线程名
     * @return 略
     */
    public static String getCurrentThreadName() {
        return getCurrentThread().getName();
    }

    /**
     * 设置当前线程名
     * @param name 线程名称
     */
    public static void setCurrentThreadName(String name) {
        getCurrentThread().setName(name);
    }

    /**
     * 是否在主线程
     * @return 略
     */
    public static boolean isOnMainThread() {
        return Looper.getMainLooper().getThread() == getCurrentThread();
    }

    /**
     * 当前线程是否被打断
     * @return 略
     */
    public static boolean isCurrentThreadInterrupted() {
        return getCurrentThread().isInterrupted();
    }


    private static class DiscardOldestPolicyAndReport implements RejectedExecutionHandler {

        /**
         * Obtains and ignores the next task that the executor
         * would otherwise execute, if one is immediately available,
         * and then retries execution of task r, unless the executor
         * is shut down, in which case task r is instead discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                Runnable oldWork = e.getQueue().poll();
                if(oldWork instanceof DiscardRunnable){
                    ((DiscardRunnable) oldWork).onDiscard();
                }
                e.execute(r);
            }
        }
    }

    /**
     * thraed pool 丢弃回调函数，主要是用在 ThreadUtils 的两个 ThreadPool 里面
     */
    public interface DiscardRunnable extends Runnable {

        /**
         * 任务被抛弃的回调
         */
        void onDiscard();
    }
}
