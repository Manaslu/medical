package com.idp.security.usersmonitor.task;

import com.idp.pub.context.AppContext;
import com.idp.pub.thread.task.ITask;
import com.idp.security.usersmonitor.service.IUserMonitorManageService;

/**
 * 任务基础类
 * 
 * @author panfei
 * 
 */
public abstract class BaseTask<T> implements ITask {

	private static final String MSG_BEAN_NAME = "userMonitorService";

	private T params;

	public static IUserMonitorManageService getIUserMonitorManageService() {
		return (IUserMonitorManageService) AppContext
				.getSpringApplicationContext().getBean(MSG_BEAN_NAME);
	}

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}

}
