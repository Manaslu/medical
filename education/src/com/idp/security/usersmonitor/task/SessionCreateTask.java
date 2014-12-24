package com.idp.security.usersmonitor.task;

import com.idp.security.usersmonitor.entity.UserEntity;

/**
 * session登陆任务
 * 
 * @author panfei
 * 
 */
public class SessionCreateTask extends BaseTask<UserEntity> {

	@Override
	public void execute() {
		getIUserMonitorManageService().addLoginUser(this.getParams());
	}
}
