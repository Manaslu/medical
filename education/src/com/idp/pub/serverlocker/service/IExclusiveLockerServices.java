package com.idp.pub.serverlocker.service;

import com.idp.pub.entity.BaseEntity;
import com.idp.pub.serverlocker.exception.LockFailedException;

/**
 * 业务排它加锁服务,后续会提供动态加锁服务 不用主动释放
 * 
 * @author panfei
 * 
 */
public interface IExclusiveLockerServices {

	/**
	 * 对业务VO进行批量加锁，默认为采取内存锁模式 <br>
	 * 必须继承BaseEntity，VO主键必须有值<br>
	 * 申请成功后，必须主动释放，调用releaseLockSuperVO
	 * 
	 * @param lockvos
	 *            需要加锁的业务VO
	 * @throws LockFailedException
	 *             加锁失败异常
	 */
	void acquireLockBaseEntity(BaseEntity... lockvos)
			throws LockFailedException;

	/**
	 * 对业务VO进行批量解锁 ，默认为“排它锁”策略，采取内存锁模式<br>
	 * 必须继承BaseEntity，VO主键必须有值<br>
	 * 
	 * @param lockvos
	 *            需要解锁的业务VO
	 * @throws LockFailedException
	 *             解锁失败异常
	 */
	void releaseLockBaseEntity(BaseEntity... lockvos)
			throws LockFailedException;

}
