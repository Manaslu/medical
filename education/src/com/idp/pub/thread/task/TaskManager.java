package com.idp.pub.thread.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 任务管理者
 * 
 * @author panfei
 * 
 */
public abstract class TaskManager {

	protected Queue<ITask> preTaskQueue = null;

	private boolean block = false;

	private int batchSize = 200;

	public TaskManager() {
		this.initialize();
	}

	protected void initialize() {
		if (block) {
			preTaskQueue = new LinkedBlockingQueue<ITask>();
		} else {
			preTaskQueue = new ConcurrentLinkedQueue<ITask>();
		}
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void addTask(ITask task) {
		this.preTaskQueue.add(task);
	}

	public ITask getNextTask() {
		return this.preTaskQueue.poll();
	}

	public List<ITask> getNextTasks() {
		List<ITask> tasklist = new ArrayList<ITask>();
		for (int i = 0; i < batchSize; i++) {
			ITask next = this.preTaskQueue.poll();
			if (next == null) {
				break;
			}
			tasklist.add(next);
		}
		return tasklist;
	}

	public void clear() {
		this.preTaskQueue.clear();
	}
}
