package com.idp.security.usersmonitor.task;

/**
 * 重制
 * 
 * @author panfei
 * 
 */
public class SessionReSetTask extends BaseTask<Object> {

	@Override
	public void execute() {
		getIUserMonitorManageService().removeAllLoginUsers();
	}

}
