package com.idp.security.usersmonitor.dao;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.SmartBaseDao;
import com.idp.security.usersmonitor.entity.UserEntity;

/**
 * 
 * @author panfei
 * 
 */

@Repository("userMonitorDao")
public class UserMonitorDao extends SmartBaseDao<UserEntity> {

	private static final String KEY_USERID = "selectOnLineByUserId";

	private static final String KEY_LOGNAME = "selectOnLineByLogName";

	@Override
	public String getUserDefineNamespace() {
		return UserEntity.class.getName();
	}

	public int countUsersById(Long userId) {
		int count = this.getSqlSession().selectOne(KEY_USERID, userId);
		return count;
	}

	public int countUsersByLogName(String logName) {
		int count = this.getSqlSession().selectOne(KEY_LOGNAME, logName);
		return count;
	}
}
