package com.guo.goldenpenguin.util.task;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 线程池管理工具
 * @Description:
 * @author:huangyx2  
 * @see:   
 * @since:      
 * @copyright © 35.com
 * @Date:2013-7-26
 */
public class ThreadUtil {
	// UI handler
	private static Handler uiHandler = null;
	// 普通线程池
	private static ExecutorService pool = null;
	// 高优先级线程池
	private static ExecutorService poolHigh = null;
	/**
	 * 检查是否初始化
	 * @Description:
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	private static void checkInited() {
		if(uiHandler == null) {
			throw new IllegalStateException("ThreadUtil.init NOT inited, you must call ThreadUtil.init first");
		}
	}
	/**
	 * get ui handler
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public static Handler getUIHandler() {
		checkInited();
		return uiHandler;
	}
	/**
	 * 获取普通线程池
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public static ExecutorService getPool() {
		checkInited();
		return pool;
	}
	/**
	 * 获取高优先级线程池
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public static ExecutorService getPoolHigh() {
		checkInited();
		return poolHigh;
	}
	/**
	 * 关闭线程池
	 * @Description:
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public static synchronized void shutdown() {
		uiHandler = null;
		if (pool != null) {
			pool.shutdown();
		}
		if (poolHigh != null) {
			poolHigh.shutdown();
		}
	}
	/**
	 * 关闭线程池
	 * @Description:
	 * @see: 
	 * @since: 
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public static synchronized void shutdownNow() {
		uiHandler = null;
		if (pool != null) {
			pool.shutdownNow();
		}
		if (poolHigh != null) {
			poolHigh.shutdownNow();
		}
	}

	/**
	 * 初始化, 必须在主线程调用
	 */
	public static synchronized void init(int poolSize, int highPoolSize) {
		if (uiHandler != null) {
			throw new IllegalStateException("ThreadUtil.init already inited");
		}
		// 初始化UI handler
		uiHandler = new Handler(Looper.getMainLooper());
		if (poolSize > 0) { // 初始化普通线程池
			pool = Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setPriority(Thread.MIN_PRIORITY);
					t.setDaemon(true);
					return t;
				}
			});
		}
		if (highPoolSize > 0) { // 初始化高优先级线程池
			poolHigh = Executors.newFixedThreadPool(highPoolSize, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setPriority(Thread.NORM_PRIORITY - 1);
					t.setDaemon(true);
					return t;
				}
			});
		}
	}

}
