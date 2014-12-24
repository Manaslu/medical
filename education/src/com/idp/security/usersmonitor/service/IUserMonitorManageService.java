package com.idp.security.usersmonitor.service;

import com.idp.pub.service.entity.IUser;
import com.idp.security.usersmonitor.entity.UserEntity;

/**
 * 在线人员监控服务
 * 
 * @author panfei
 * 
 */
public interface IUserMonitorManageService {

	/**
	 * 判断用户是否在线
	 * 
	 * @param userId
	 *            用户信息
	 * @return
	 */
	boolean isOnline(IUser user);

	/**
	 * 添加在线用户记录
	 * 
	 * @param user
	 */
	void addLoginUser(UserEntity user);

	/**
	 * 删除用户在线记录
	 * 
	 * @param sessionId
	 */
	void removeLoginUser(String sessionId);

	/**
	 * 清除所有在线用户记录
	 */
	void removeAllLoginUsers();
}
