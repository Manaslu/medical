package com.idp.pub.serverlocker.utils;

import com.idp.pub.serverlocker.entity.LockInfoEntity;

/**
 * 
 * @author panfei
 * 
 */
public class LockInfoEntityUtil {

	/**
	 * 创建 内存排它锁vo
	 * 
	 * @return
	 */
	public static LockInfoEntity createMeLockInfoEntity() {
		LockInfoEntity retvo = new LockInfoEntity();
		retvo.setLockMode(LockInfoEntity.lOCK_EXCLUSIVE);
		retvo.setSaveMode(LockInfoEntity.SAVE_MEMORY);
		retvo.setThreadId(Thread.currentThread().getId());
		return retvo;
	}
}
