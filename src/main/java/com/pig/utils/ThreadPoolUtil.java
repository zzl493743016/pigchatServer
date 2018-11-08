package com.pig.utils;

/**
 * @author Arthas
 * @create 2018/11/8
 */

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类（便捷将任务放入线程池中执行）
 * @author Arthas
 * @create 2018/7/17
 */
public class ThreadPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);
    /**
     * 线程池维护线程的最少数量
     */
    private static final int SIZE_CORE_POOL = 3;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int SIZE_MAX_POOL = 10;
    /**
     * 禁止手动初始化
     */
    private ThreadPoolUtil(){}
    /**
     * 通过枚举创建单例对象
     */
    private enum Singleton {
        /**
         * 线程池单例
         */
        SINGLETON;
        private ThreadPoolExecutor threadPool;
        Singleton() {
            // 为线程池命名
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                    .setNameFormat("线程池工具类-pool-%d").build();
            // 创建线程池
            threadPool = new ThreadPoolExecutor(
                    SIZE_CORE_POOL,
                    SIZE_MAX_POOL,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    namedThreadFactory);
        }
        /**
         * 返回单例对象
         */
        public ThreadPoolExecutor getThreadPool() {
            return threadPool;
        }
    }
    /**
     * 向池中添加任务
     * @param task
     */
    public static void addExecuteTask(Runnable task) {
        if (task != null) {
            Singleton.SINGLETON.getThreadPool().execute(task);
        }
    }
}
