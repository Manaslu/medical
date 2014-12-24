package com.idp.security.usersmonitor.task;

/**
 * 销毁
 * 
 * @author panfei
 * 
 */
public class SessionDestroyTask extends BaseTask<String> {

	@Override
	public void execute() {
		getIUserMonitorManageService().removeLoginUser(this.getParams());
	}
}
