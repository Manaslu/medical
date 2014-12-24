package com.idp.security.usersmonitor.task;

import com.idp.pub.thread.task.TaskManager;

/**
 * session任务管理者
 * 
 * @author panfei
 * 
 */
public class SessionTaskManager extends TaskManager {

	private static SessionTaskManager instance = new SessionTaskManager();

	static {
		instance.addTask(new SessionReSetTask());
	}

	public static SessionTaskManager getInstance() {
		return instance;
	}
}
