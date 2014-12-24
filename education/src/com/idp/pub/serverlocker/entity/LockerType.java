package com.idp.pub.serverlocker.entity;

import java.io.Serializable;

/**
 * 锁种类
 * 
 * @author panfei
 * 
 */
public class LockerType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1951760046935184259L;

	public static final String meElLockType = "meELocker";

	private byte lockMode = LockInfoEntity.lOCK_EXCLUSIVE;

	private byte saveMode = LockInfoEntity.SAVE_MEMORY;

	public LockerType() {
		super();
	}

	public LockerType(byte lockMode, byte saveMode) {
		this.lockMode = lockMode;
		this.saveMode = saveMode;
	}

	@Override
	public int hashCode() {
		String hashkey = Byte.toString(lockMode) + Byte.toString(saveMode);
		return hashkey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			LockerType compare = (LockerType) obj;
			if ((this.lockMode == compare.lockMode)
					&& (this.saveMode == compare.saveMode)) {
				return true;
			}
		}
		return false;
	}
}
