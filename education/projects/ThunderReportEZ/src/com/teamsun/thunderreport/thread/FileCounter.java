package com.teamsun.thunderreport.thread;

import edu.emory.mathcs.backport.java.util.concurrent.Semaphore;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

/**
 * 记录完成的文件数量
 * 
 */
public class FileCounter {

	private AtomicInteger fileCount;

	private Semaphore semaphore;

	// private boolean[] bools = new boolean[] { false };

	public int decrease() {
		int res = this.fileCount.decrementAndGet();
		if (res == 0) {
			this.semaphore.release();
		}
		return res;
	}

	public int getValue() {
		return this.fileCount.intValue();
	}

	public FileCounter() {
		this(0);
	}

	public FileCounter(int value) {
		this.fileCount = new AtomicInteger(value);
		this.semaphore = new Semaphore(1);
		try {
			this.semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doCountWith0(FinallyAction finallyAction, final Object param) {

		try {
			System.out.println("开始等待");
			this.semaphore.acquire();
			System.out.println("等待结束");
			finallyAction.doAction(param);
			System.out.println("删除表结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			this.semaphore.release();
		}

	}

	public static interface FinallyAction {
		public void doAction(final Object param);
	}

	public static void main(String[] args) {
		final FileCounter fc = new FileCounter(5);

		Thread th = new Thread(new Runnable() {

			public void run() {
				fc.doCountWith0(new FinallyAction() {

					public void doAction(Object param) {
						System.out.println("come on");
					}

				}, null);
			}

		});

		th.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		fc.decrease();
		System.out.println(fc.getValue());
		fc.decrease();
		fc.decrease();
		fc.decrease();
		System.out.println(fc.getValue());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		fc.decrease();
		System.out.println(fc.getValue());
	}
}
