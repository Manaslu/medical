package com.idp.pub.serverlocker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.idp.pub.dao.AbstractDaoStrategyFactory;
import com.idp.pub.entity.BaseEntity;
import com.idp.pub.entity.utils.SuperEntityUtils;
import com.idp.pub.serverlocker.dao.ILockerDaoStrategy;
import com.idp.pub.serverlocker.dao.LockerDaoStrategyFactory;
import com.idp.pub.serverlocker.entity.LockerType;
import com.idp.pub.serverlocker.entity.Paramters;
import com.idp.pub.serverlocker.exception.LockFailedException;
import com.idp.pub.serverlocker.service.IExclusiveLockerServices;

/**
 * 加锁服务实现
 * 
 * @author panfei
 * 
 */
@Service("excLockerService")
public class ExclusiveLockerServicesImpl implements IExclusiveLockerServices,
		InitializingBean {

	// @Resource(name = "excLockerKeyDaoFactory")
	@Autowired(required = false)
	@Qualifier("excLockerKeyDaoFactory")
	public AbstractDaoStrategyFactory<LockerType, ILockerDaoStrategy> LockerDaoFactory;

	private ILockerDaoStrategy excLockerDaoStrategy;

	@Override
	public void acquireLockBaseEntity(BaseEntity... lockvos)
			throws LockFailedException {
		if (lockvos == null || lockvos.length == 0) {
			throw new LockFailedException("加锁vo不能为空!");
		}
		boolean mark = this.lock(SuperEntityUtils.convertBaseExtVO(lockvos));
		if (!mark) {
			throw new LockFailedException("加锁失败！其它人员正在操作中！");
		}
	}

	@Override
	public void releaseLockBaseEntity(BaseEntity... lockvos)
			throws LockFailedException {
		if (lockvos == null || lockvos.length == 0) {
			throw new LockFailedException("解锁vo不能为空!");
		}
		this.unlock(SuperEntityUtils.convertBaseExtVO(lockvos));
	}

	private boolean unlock(BaseEntity... vos) throws LockFailedException {
		if (vos != null) {
			if (vos.length == 1) {
				return unLockOne(vos[0]);
			} else {
				return unLocks(vos);
			}
		}
		return false;
	}

	private boolean unLockOne(BaseEntity vo) throws LockFailedException {
		if (vo.getPrimaryKeys() == null || vo.getPrimaryKeys().length == 0) {
			throw new LockFailedException("加锁vo主键不能为空！");
		}
		StringBuilder key = new StringBuilder();
		for (String keystr : vo.getPrimaryKeys()) {
			key.append(keystr);
		}
		return excLockerDaoStrategy.unlock(key.toString(),
				new Paramters(vo.getEntityName()));
	}

	private boolean unLocks(BaseEntity[] vos) throws LockFailedException {
		List<String> list = new ArrayList<String>();
		for (BaseEntity tempvo : vos) {
			if (tempvo == null || tempvo.getPrimaryKeys() == null
					|| tempvo.getPrimaryKeys().length == 0) {
				throw new LockFailedException("批量加锁vo不能为空！");
			}
			StringBuilder key = new StringBuilder();
			for (String keystr : tempvo.getPrimaryKeys()) {
				key.append(keystr);
			}
			list.add(key.toString());
		}
		return excLockerDaoStrategy.unlock(
				list.toArray(new String[list.size()]),
				new Paramters(vos[0].getEntityName()));
	}

	private boolean lock(BaseEntity... vos) throws LockFailedException {
		if (vos != null) {
			if (vos.length == 1) {
				return lockOne(vos[0]);
			} else {
				return locks(vos);
			}
		}
		return false;
	}

	private boolean lockOne(BaseEntity vo) throws LockFailedException {
		if (vo.getPrimaryKeys() == null || vo.getPrimaryKeys().length == 0) {
			throw new LockFailedException("加锁vo主键不能为空！");
		}
		StringBuilder key = new StringBuilder();
		for (String keystr : vo.getPrimaryKeys()) {
			key.append(keystr);
		}
		return excLockerDaoStrategy.lock(key.toString(),
				new Paramters(vo.getEntityName()));
	}

	private boolean locks(BaseEntity[] vos) throws LockFailedException {
		List<String> list = new ArrayList<String>();
		for (BaseEntity tempvo : vos) {
			if (tempvo == null || tempvo.getPrimaryKeys() == null
					|| tempvo.getPrimaryKeys().length == 0) {
				throw new LockFailedException("批量加锁vo不能为空！");
			}
			StringBuilder key = new StringBuilder();
			for (String keystr : tempvo.getPrimaryKeys()) {
				key.append(keystr);
			}
			list.add(key.toString());
		}
		return excLockerDaoStrategy.lock(list.toArray(new String[list.size()]),
				new Paramters(vos[0].getEntityName()));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.LockerDaoFactory == null) {
			this.excLockerDaoStrategy = LockerDaoStrategyFactory
					.getLockerDaoStrategyFactory().createDaoStrategy(
							LockerType.meElLockType);
		} else {
			this.excLockerDaoStrategy = this.LockerDaoFactory
					.createDaoStrategy();
		}
	}
}
