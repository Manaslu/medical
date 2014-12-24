package com.idp.workflow.runtime.activiti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.runtime.IWorkFlowRunTime;
import com.idp.workflow.util.loader.activiti.ExtReflectUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 自动服务
 * 
 * @author panfei
 * 
 */
public class ClassDelegteRunTime extends AbstractWfRunTimeDelegater {

	@Override
	public TaskInfoVO createTaskInfoVO(ActivityImpl actimpl,
			String proInstanceID, Map<String, ?> params)
			throws WfBusinessException {
		return VOUtil.convertTaskInfoVO(actimpl, proInstanceID);
	}

	@Override
	public List<IWorkFlowRunTime> createIWorkFlowRunTime(ActivityImpl actimpl,
			Map<String, ?> params) throws WfBusinessException {
		List<IWorkFlowRunTime> retlist = new ArrayList<IWorkFlowRunTime>();
		ActivityBehavior actbeanvoir = actimpl.getActivityBehavior();
		if (actbeanvoir instanceof ClassDelegate) {
			ClassDelegate classdel = (ClassDelegate) actbeanvoir;
			TaskListener temptask = (TaskListener) ExtReflectUtil
					.invokeMethoad(classdel, "getTaskListenerInstance", null);
			if (temptask instanceof IWorkFlowRunTime) {
				retlist.add(((IWorkFlowRunTime) temptask));
			}
		}
		return retlist;
	}

}
