package com.idp.workflow.runtime.activiti;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.runtime.IWorkFlowRunTime;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.service.WfRunTimeContext;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 
 * @author panfei
 * 
 */
public abstract class AbstractWfRunTimeDelegater implements
		IWorkFlowRunTimeDelegater {

	@Override
	public void goForWard(ActivityImpl actimpl, String proInstanceID,
			Map<String, Object> params) throws WfBusinessException {
		TaskInfoVO taskvo = this.createTaskInfoVO(actimpl, proInstanceID,
				params);
		List<IWorkFlowRunTime> workers = this.createIWorkFlowRunTime(actimpl,
				params);
		WfRunTimeContext context = new WfRunTimeContext();
		context.setContext(params);
		context.putVariables(IWorkFlowTypes.signalStatus,
				IWorkFlowTypes.SignalStatus.ForWardRunning);
		context.putVariables(IWorkFlowTypes.taskInfo, taskvo);
		if (workers != null)
			for (IWorkFlowRunTime worker : workers) {
				worker.goForWard(context);
			}
	}

	@Override
	public void backForWard(ActivityImpl actimpl, String proInstanceID,
			Map<String, Object> params) throws WfBusinessException {
		TaskInfoVO taskvo = this.createTaskInfoVO(actimpl, proInstanceID,
				params);
		List<IWorkFlowRunTime> workers = this.createIWorkFlowRunTime(actimpl,
				params);
		WfRunTimeContext context = new WfRunTimeContext();
		context.setContext(params);
		context.putVariables(IWorkFlowTypes.signalStatus,
				IWorkFlowTypes.SignalStatus.BackWardRunning);
		context.putVariables(IWorkFlowTypes.taskInfo, taskvo);
		if (workers != null)
			for (IWorkFlowRunTime worker : workers) {
				worker.getBackForWard(context);
			}
	}

	/**
	 * 创建任务节点信息VO
	 * 
	 * @param actimpl
	 * @param proInstanceID
	 * @param params
	 * @return
	 * @throws WfBusinessException
	 */
	public abstract TaskInfoVO createTaskInfoVO(ActivityImpl actimpl,
			String proInstanceID, Map<String, ?> params)
			throws WfBusinessException;

	/**
	 * 创建工作流执行单元
	 * 
	 * @param actimpl
	 * @param params
	 * @return
	 * @throws WfBusinessException
	 */
	public abstract List<IWorkFlowRunTime> createIWorkFlowRunTime(
			ActivityImpl actimpl, Map<String, ?> params)
			throws WfBusinessException;
}
