package com.idp.security.usersmonitor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.entity.Pager;
import com.idp.security.usersmonitor.dao.UserMonitorDao;
import com.idp.security.usersmonitor.entity.UserEntity;
import com.idp.security.usersmonitor.service.IUserMonitorQueryService;

/**
 * 
 * @author panfei
 * 
 */
@Service("userMonitorQueryService")
@Transactional(readOnly = true)
public class UserMonitorQueryServiceImpl implements IUserMonitorQueryService {

	@Resource(name = "userMonitorDao")
	private UserMonitorDao userMonitorDao;

	@Override
	public List<UserEntity> queryAllOnlineUsersInfo() {
		return userMonitorDao.selectBaseEntityByCondition(UserEntity.class,
				null, null);
	}

	@Override
	public Pager<UserEntity> queryUnRepeatUsers(Pager<UserEntity> pager,
			Map<String, Object> params) {
		return userMonitorDao.findByPager(pager, params);
	}

}
