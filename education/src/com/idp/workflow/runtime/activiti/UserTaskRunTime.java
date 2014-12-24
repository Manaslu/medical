package com.idp.workflow.runtime.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.runtime.IWorkFlowRunTime;
import com.idp.workflow.util.loader.activiti.ExtReflectUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 用户服务
 * 
 * @author panfei
 * 
 */
public class UserTaskRunTime extends AbstractWfRunTimeDelegater {

	@Override
	public TaskInfoVO createTaskInfoVO(ActivityImpl actimpl,
			String proInstanceID, Map<String, ?> params)
			throws WfBusinessException {
		return VOUtil.convertTaskInfoVO4User(actimpl, proInstanceID, params);
	}

	@Override
	public List<IWorkFlowRunTime> createIWorkFlowRunTime(ActivityImpl actimpl,
			Map<String, ?> params) throws WfBusinessException {
		UserTaskActivityBehavior useract = (UserTaskActivityBehavior) actimpl
				.getActivityBehavior();
		List<TaskListener> tasklist = useract.getTaskDefinition()
				.getTaskListener(TaskListener.EVENTNAME_COMPLETE);
		List<IWorkFlowRunTime> retlist = new ArrayList<IWorkFlowRunTime>();

		if (tasklist != null) {
			for (TaskListener temp : tasklist) {
				if (temp instanceof ClassDelegate) {
					ClassDelegate classdel = (ClassDelegate) temp;
					TaskListener temptask = (TaskListener) ExtReflectUtil
							.invokeMethoad(classdel, "getTaskListenerInstance",
									null);
					if (temptask instanceof IWorkFlowRunTime) {
						retlist.add((IWorkFlowRunTime) temptask);
					}
				}
			}
		}
		return retlist;
	}

}
