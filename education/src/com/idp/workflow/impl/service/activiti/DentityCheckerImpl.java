package com.idp.workflow.impl.service.activiti;

import java.util.Arrays;

import org.activiti.engine.delegate.DelegateTask;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.IDentityChecker;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 
 * @author panfei
 * 
 */
public class DentityCheckerImpl implements IDentityChecker {

	private DelegateTask deltask;

	private TaskInfoVO taskInfo;

	public DentityCheckerImpl(DelegateTask task, TaskInfoVO taskInfo) {
		this.deltask = task;
		this.taskInfo = taskInfo;
	}

	@Override
	public void setAssigneedUserId(String... userId) throws WfBusinessException {
		if (userId.length == 1) {
			deltask.setAssignee(userId[0]);
			deltask.setOwner(userId[0]);
			this.taskInfo.setAssignee(userId[0]);
		} else if (userId.length > 1) {
			deltask.addCandidateUsers(Arrays.asList(userId));
			StringBuilder appender = new StringBuilder();
			for (int i = 0; i < userId.length; i++) {
				if (i > 0) {
					appender.append(",");
				}
				appender.append(userId[i]);
			}
			this.taskInfo.setAssignee(appender.toString());
		}
	}

}
