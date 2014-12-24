package com.idp.security.usersmonitor.task;

import java.util.List;

import com.idp.pub.exception.BusinessException;
import com.idp.pub.thread.task.ITask;

/**
 * quartz任务执行者
 * 
 * @author panfei
 * 
 */
public class SessionTaskJobExecutor {

	public void execute() {
		try {
			List<ITask> tasklist = SessionTaskManager.getInstance()
					.getNextTasks();
			if (tasklist != null) {
				for (ITask task : tasklist) {
					task.execute();
				}
			}
		} catch (Exception e) {
			throw new BusinessException("可能没有创建T02_USER_ONLINE", e);
		}
	}
}
