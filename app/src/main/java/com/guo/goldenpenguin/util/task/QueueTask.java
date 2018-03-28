package com.guo.goldenpenguin.util.task;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * 队列线程
 * 
 * @Description:
 * @author:huangyx2
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-7-26
 */
public abstract class QueueTask {

	// 任务队列
	private static LinkedBlockingQueue<QueueTask> quees = new LinkedBlockingQueue<QueueTask>(200);

	private static Thread thread = null;
	// 当前任务
	private static QueueTask currTask = null;

	public QueueTask() {
		if (thread == null) {
			thread = new Thread(runnable);
			thread.start();
		}
		try {
			// 加入队列中
			quees.put(this);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 
	 */
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				while (true) { // 一个无限循环，遍历任务队列
					if (!quees.isEmpty()) { // 有要执行的任务
						// 取出第一个任务
							currTask = quees.take();
						if (currTask != null) {
							// 执行后台方法
							currTask.onBack();
							// 切换到主线程执行前台方法
							new ForeTask(true) {
	
								@Override
								public void onFore() {
									currTask.onFore();
									currTask = null;
								}
							};
						}
					}
				}
			} catch (InterruptedException e) {
			}
		}
	};

	/**
	 * 需要执行的内容
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public abstract void onBack();

	/**
	 * 执行后的回调，会主动切换到主线程
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: huangyx2
	 * @date:2013-7-26
	 */
	public abstract void onFore();
}
