package com.idp.pub.serverlocker.entity;

import java.io.Serializable;

/**
 * 业务锁VO
 * 
 * 业务锁使用策略分为“排它锁”或“等待锁”，保存持久化策略为“内存”或“数据库”。
 * 
 * @author panfei
 * 
 */
public class LockInfoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2675405087362846603L;

	/**
	 * 排它锁\互斥锁
	 */
	public static final byte lOCK_EXCLUSIVE = 0;

	/**
	 * 等待锁
	 */
	public static final byte LOCK_WAIT = 1;

	/**
	 * 内存加锁
	 */
	public static final byte SAVE_MEMORY = 0;

	/**
	 * 数据库加锁
	 */
	public static final byte SAVE_DATABASE = 1;

	private byte lockMode = 0;

	private byte saveMode = 0;

	private long threadId = -1;

	public byte getLockMode() {
		return lockMode;
	}

	public void setLockMode(byte lockMode) {
		this.lockMode = lockMode;
	}

	public byte getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(byte saveMode) {
		this.saveMode = saveMode;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}
}
