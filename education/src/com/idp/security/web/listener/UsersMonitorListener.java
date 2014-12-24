package com.idp.security.web.listener;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.idp.pub.service.entity.IUser;
import com.idp.security.usersmonitor.entity.UserEntity;
import com.idp.security.usersmonitor.task.SessionCreateTask;
import com.idp.security.usersmonitor.task.SessionDestroyTask;
import com.idp.security.usersmonitor.task.SessionTaskManager;

/**
 * 在线人数统计监听类
 * 
 * @author panfei
 * 
 */
public class UsersMonitorListener implements HttpSessionBindingListener,
		Serializable {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 127002537816125159L;

	private IUser loginUser;

	private String sessionid;

	public UsersMonitorListener(IUser loguser, String sid) {
		loginUser = loguser;
		sessionid = sid;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		SessionCreateTask createTask = new SessionCreateTask();
		createTask.setParams(UserEntity.createNewOne(loginUser, sessionid));
		SessionTaskManager.getInstance().addTask(createTask);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		SessionDestroyTask destorytask = new SessionDestroyTask();
		destorytask.setParams(sessionid);
		SessionTaskManager.getInstance().addTask(destorytask);
	}

	public String getSessionid() {
		return sessionid;
	}

	public IUser getLoginUser() {
		return loginUser;
	}
}
