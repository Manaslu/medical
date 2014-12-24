package com.idp.workflow.runtime.activiti;

import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;

import com.idp.workflow.event.action.ActionEvent;
import com.idp.workflow.event.action.ActionEventDispatcher;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.service.activiti.DentityCheckerImpl;
import com.idp.workflow.itf.service.identity.AuthenticationHolder;
import com.idp.workflow.itf.service.identity.IDentityChecker;
import com.idp.workflow.itf.service.runtime.IWorkFlowRunTime;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;
import com.idp.workflow.vo.pub.IWorkFlowTypes.SignalStatus;
import com.idp.workflow.vo.service.WfRunTimeContext;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 工作流驱动<br>
 * 实现自动服务任务继承此类<br>
 * 实现用户任务继承此类
 * 
 * @author panfei
 * 
 */
public abstract class CommonTaskGadget implements IWorkFlowRunTime,
		TaskListener, JavaDelegate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4660287255904178324L;
	private org.activiti.engine.impl.el.FixedValue configParams;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Object statusobj = execution.getVariable(IWorkFlowTypes.signalStatus);

		SignalStatus statusNow = convertStatus(statusobj,
				execution.getEventName());

		ExecutionEntity ecentity = (ExecutionEntity) execution;
		WfRunTimeContext context = new WfRunTimeContext();
		context.setContext(execution.getVariables());
		context.putVariables(IWorkFlowTypes.signalStatus, statusobj);

		TaskInfoVO taskinfo = new TaskInfoVO();
		taskinfo.setName(ecentity.getActivityId());
		taskinfo.setProcessInstanceId(ecentity.getProcessInstanceId());
		taskinfo.setTaskDefinitionKey(ecentity.getActivityId());
		// 可以通过ActivityImpl.getProerty判断是否为servicestask，目前没有必要
		taskinfo.setAutoRun(true);
		if (ExecutionListener.EVENTNAME_END.equals(execution.getEventName())
				&& IWorkFlowTypes.activityType_endEvent.equals(ecentity
						.getActivity().getProperty("type"))) {
			taskinfo.setProcessEnd(true);
		}

		if (getConfigParams() != null) {
			taskinfo.setConfigParams(StringUtil
					.convertConfigParamsVO(getConfigParams()
							.getExpressionText()));
		}
		context.putVariables(IWorkFlowTypes.taskInfo, taskinfo);
		this.doWork(statusNow, context);
		ecentity.setVariables(context.getContext());
	}

	@Override
	public void notify(DelegateTask delegateTask) {
		Object statusobj = delegateTask
				.getVariable(IWorkFlowTypes.signalStatus);

		SignalStatus statusNow = convertStatus(statusobj,
				delegateTask.getEventName());

		WfRunTimeContext context = new WfRunTimeContext();
		context.setContext(delegateTask.getVariables());
		context.putVariables(IWorkFlowTypes.signalStatus, statusobj);

		TaskInfoVO taskinfo = new TaskInfoVO();
		taskinfo.setName(delegateTask.getName());
		taskinfo.setProcessInstanceId(delegateTask.getProcessInstanceId());
		taskinfo.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
		taskinfo.setAutoRun(false);
		taskinfo.setConfigParams(StringUtil.convertConfigParamsVO(delegateTask
				.getDescription()));
		taskinfo.setProcessEnd(false);
		taskinfo.setAssignee(delegateTask.getAssignee());
		taskinfo.setReMark(context.getReMark());
		taskinfo.setSignature(context.getSignature());
		taskinfo.setCandidateGroup(this.getCandidateGroup(delegateTask));
		context.putVariables(IWorkFlowTypes.taskInfo, taskinfo);

		IDentityChecker denchecker = new DentityCheckerImpl(delegateTask,
				taskinfo);
		context.putVariables(IWorkFlowTypes.identityChecker, denchecker);

		try {
			this.doWork(statusNow, context);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		context.removeVariables(IWorkFlowTypes.identityChecker);
		delegateTask.setVariables(context.getContext());
	}

	private String getCandidateGroup(DelegateTask delegateTask) {
		TaskEntity task = (TaskEntity) delegateTask;
		Set<IdentityLink> sets = task.getCandidates();
		for (IdentityLink tmp : sets) {
			if (IdentityLinkType.CANDIDATE.equals(tmp.getType())) {
				return tmp.getGroupId();
			}
		}
		return null;
	}

	@Override
	public void getBackForWard(WfRunTimeContext context)
			throws WfBusinessException {
		try {
			this.doWork(context.getSignalStatus(), context);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 向后退回方法
	 * 
	 * @param context
	 *            工作流上下文
	 * @throws WfBusinessException
	 */
	public abstract void backForWard(WfRunTimeContext context)
			throws WfBusinessException;

	private void doWork(SignalStatus statusNow, WfRunTimeContext context)
			throws Exception {
		if (IWorkFlowTypes.SignalStatus.ForWardRunning == statusNow) {
			this.fireEvent(ActionEvent.ACTIONCODE_FORWARDBEFORE, context);
			this.goForWard(context);
			this.fireEvent(ActionEvent.ACTIONCODE_FORWARDAFTER, context);
		} else if (IWorkFlowTypes.SignalStatus.BeforeActiveRunning == statusNow) {
			this.fireEvent(ActionEvent.ACTIONCODE_ACTIVEBEFORE, context);
			this.beforeActivite(context);
			this.fireEvent(ActionEvent.ACTIONCODE_ACTIVEAFTER, context);
		} else if (IWorkFlowTypes.SignalStatus.NotRunning == statusNow) {
			// nothing to do!
		} else if (IWorkFlowTypes.SignalStatus.BackWardRunning == statusNow) {
			this.fireEvent(ActionEvent.ACTIONCODE_BACKWARDBEFORE, context);
			this.backForWard(context);
			this.fireEvent(ActionEvent.ACTIONCODE_BACKWARDAFTER, context);
		}
	}

	private void fireEvent(String eventType, WfRunTimeContext context_params)
			throws WfBusinessException {

		if (CheckType.SuperiorBack.equals(AuthenticationHolder
				.getCurrentAuthentiUsers().getPermissionType())) {
			if (eventType.equals(ActionEvent.ACTIONCODE_BACKWARDBEFORE)) {
				eventType = ActionEvent.ACTIONCODE_REJECTBEFORE;
			}

			if (eventType.equals(ActionEvent.ACTIONCODE_BACKWARDAFTER)) {
				eventType = ActionEvent.ACTIONCODE_REJECTAFTER;
			}
		}

		ActionEventDispatcher.getInstance().dispatchEvent(
				new ActionEvent(this, eventType, context_params.getContext()));
	}

	private SignalStatus convertStatus(Object statusobj, String eventName) {
		SignalStatus statusNow = null;
		if (statusobj == null) {
			statusNow = IWorkFlowTypes.SignalStatus.ForWardRunning;
		} else {
			statusNow = (SignalStatus) statusobj;
		}
		// 判断事件类型
		if ((TaskListener.EVENTNAME_CREATE.equals(eventName) || ExecutionListener.EVENTNAME_START
				.equals(eventName))
				&& IWorkFlowTypes.SignalStatus.NotRunning != statusNow) {
			statusNow = IWorkFlowTypes.SignalStatus.BeforeActiveRunning;
		}

		return statusNow;
	}

	public org.activiti.engine.impl.el.FixedValue getConfigParams() {
		return configParams;
	}

	public void setConfigParams(
			org.activiti.engine.impl.el.FixedValue configParams) {
		this.configParams = configParams;
	}
}
