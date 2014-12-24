package com.idp.security.usersmonitor.service;

import java.util.List;
import java.util.Map;

import com.idp.pub.entity.Pager;
import com.idp.security.usersmonitor.entity.UserEntity;

/**
 * 用户监控查询服务
 * 
 * @author panfei
 * 
 */
public interface IUserMonitorQueryService {

	/**
	 * 查询所有在线用户
	 * 
	 * @return
	 */
	List<UserEntity> queryAllOnlineUsersInfo();

	Pager<UserEntity> queryUnRepeatUsers(Pager<UserEntity> pager,
			Map<String, Object> params);
}
