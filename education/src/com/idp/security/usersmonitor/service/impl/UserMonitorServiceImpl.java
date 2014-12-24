package com.idp.security.usersmonitor.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.entity.IUser;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.pub.utils.StringUtils;
import com.idp.security.usersmonitor.dao.UserMonitorDao;
import com.idp.security.usersmonitor.entity.UserEntity;
import com.idp.security.usersmonitor.service.IUserMonitorManageService;

/**
 * 
 * @author panfei
 * 
 */
@Service("userMonitorService")
@Transactional
public class UserMonitorServiceImpl extends
		DefaultBaseService<UserEntity, String> implements
		IUserMonitorManageService {

	@Resource(name = "userMonitorDao")
	private UserMonitorDao userMonitorDao;

	@Resource(name = "userMonitorDao")
	public void setBaseDao(IBaseDao<UserEntity, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "userMonitorDao")
	public void setPagerDao(IPagerDao<UserEntity> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	@Override
	public boolean isOnline(IUser user) {
		if (user == null) {
			throw new BusinessException("用户信息不能为空！");
		}
		int count = 0;
		if (!StringUtils.isEmpty(user.getLogName())) {
			count = userMonitorDao.countUsersByLogName(user.getLogName());
		} else {
			count = userMonitorDao.countUsersById(user.getId());
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void addLoginUser(UserEntity user) {
		if (user == null) {
			throw new BusinessException("用户信息不能为空！");
		}
		this.userMonitorDao.insertBaseEntity(user);
	}

	@Override
	public void removeLoginUser(String sessionId) {
		UserEntity delvo = new UserEntity();
		delvo.setPrimaryKeys(sessionId);
		this.userMonitorDao.deleteBaseEntity(delvo);
	}

	@Override
	public void removeAllLoginUsers() {
		this.userMonitorDao.delete(null);
	}

}
