package com.teamsun.thunderreport.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.thunderreport.core.bean.Page;

import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.Semaphore;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

/**
 * 与资源绑定的线程池。池中所有线程共享资源，资源数量为线程并发执行最大数量。
 * 
 * 
 * 
 */
public class ThreadResourcePool {

	private static final Log logger = LogFactory
			.getLog(ThreadResourcePool.class);

	public static final int DEFAULT_MAX_ACTIVE_SIZE = 10;

	/**
	 * 最大活动数量
	 * 
	 */
	private int maxActiveSize;

	/**
	 * 信号量
	 * 
	 */
	private Semaphore available;

	/**
	 * 资源
	 */
	private Integer[] resources;

	/**
	 * 是否在使用中
	 */
	private AtomicBoolean[] used;

	//private Lock lock = new ReentrantLock();

	private ExecutorService es = null;

	/**
	 * 构造方法，默认最大活动数量为10
	 * 
	 */
	public ThreadResourcePool() {
		this(ThreadResourcePool.DEFAULT_MAX_ACTIVE_SIZE);
	}

	/**
	 * 构造方法，线程最大活动数量等于线程数
	 * 
	 * @param maxActiveSize
	 *            资源数量，线程数量默认等于最大活动数量数量
	 * 
	 */
	public ThreadResourcePool(int maxActiveSize) {
		this(maxActiveSize, maxActiveSize);
	}

	/**
	 * 构造方法
	 * 
	 * 
	 * @param maxActiveSize
	 *            资源数量
	 * @param threadSize
	 *            线程数量
	 */
	public ThreadResourcePool(int maxActiveSize, int threadSize) {
		super();
		this.maxActiveSize = maxActiveSize;
		this.available = new Semaphore(maxActiveSize);
		this.resources = new Integer[maxActiveSize];
		this.used = new AtomicBoolean[maxActiveSize];

		for (int i = 0; i < this.resources.length; i++) {
			this.resources[i] = new Integer(i);
			used[i] = new AtomicBoolean(false);
		}

		this.es = Executors.newFixedThreadPool(threadSize);

		logger.info("初始化完毕，资源数量：" + threadSize);
	}

	public void doProcess(final Object param, final ProcessCallBack callBack) {

		this.es.execute(new Runnable() {

			public void run() {
				Integer flag = new Integer(-1);
				try {
					available.acquire();

					synchronized (used) {
						for (int i = 0; i < maxActiveSize; i++) {
							if (used[i].get() == false) {
								used[i].set(true);
								flag = resources[i];
								break;
							}
						}
					}
					// lock.lock();
					// try {
					//						
					// } finally {
					// lock.unlock();
					// }

					if (callBack != null) {
						callBack.doProcess(param);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("线程执行出错");
				} finally {
					used[flag.intValue()].set(false);
					available.release();
					((FileCounter) param).decrease();
				}
			}

		});

	}

	/**
	 * 停止线程。如果线程未执行完则等待。
	 * 
	 * 
	 */
	public void shutdown() {
		this.es.shutdown();
	}

	/**
	 * 立刻停止所有线程
	 * 
	 * 
	 */
	public void shutdownNow() {
		this.es.shutdownNow();
	}

	public int getMaxActiveSize() {
		return maxActiveSize;
	}

}
