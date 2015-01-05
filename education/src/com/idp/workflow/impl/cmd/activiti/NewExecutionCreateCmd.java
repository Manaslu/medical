package com.idp.workflow.impl.cmd.activiti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cmd.AddIdentityLinkCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.ClockUtil;
import org.activiti.engine.task.IdentityLinkType;

import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
 
/**
 * 新环节创建命令
 * 
 * @author panfei
 * 
 */
public class NewExecutionCreateCmd implements Command<Void>, Serializable {

	/**
			 * 
			 */
	private static final long serialVersionUID = -4454939969476392784L;

	private String changeToActId;
	private String proInstanceID;
	private String[] delactList;

	public NewExecutionCreateCmd(String changeToActId, String proInstanceID,
			String[] delactList) {
		this.changeToActId = changeToActId;
		this.proInstanceID = proInstanceID;
		this.delactList = delactList;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		// 初始化
		HistoricActivityInstanceEntity hisactentity = commandContext
				.getHistoricActivityInstanceEntityManager()
				.findHistoricActivityInstance(changeToActId, proInstanceID);
		// update for 5.14 by panfei
		ProcessDefinitionEntity procesDefEntity = Context
				.getProcessEngineConfiguration().getDeploymentManager()
				.getProcessDefinitionCache()
				.get(hisactentity.getProcessDefinitionId());
		if (procesDefEntity == null) {
			procesDefEntity = Context
					.getProcessEngineConfiguration()
					.getDeploymentManager()
					.findDeployedProcessDefinitionById(
							hisactentity.getProcessDefinitionId());
		}
		ActivityImpl changeTo = procesDefEntity.findActivity(hisactentity
				.getActivityId());
		// 查询历史工作流实例
		HistoricProcessInstanceEntity hisentity = commandContext
				.getHistoricProcessInstanceEntityManager()
				.findHistoricProcessInstance(proInstanceID);
		// 判断是否需要重新创建ExecutionEntity
		ExecutionEntity queryExecutionEntity = commandContext
				.getExecutionEntityManager().findExecutionById(
						hisactentity.getExecutionId());
		// 创建新的excetuion
		ExecutionEntity repalceOne = null;
		if (queryExecutionEntity == null) {
			repalceOne = VOUtil.convertExecutionEntity(hisentity);
			repalceOne.setId(hisactentity.getExecutionId());
			repalceOne.setProcessInstance(repalceOne);
			repalceOne.setActivity(changeTo);
			repalceOne.insert();
		} else {
			repalceOne = queryExecutionEntity;
			repalceOne.setActivity(changeTo);
			repalceOne.forceUpdate();
		}
		// 如果待办任务存在，则删除待办任务
		List<TaskEntity> needdelTaskList = commandContext
				.getTaskEntityManager().findTasksByProcessInstanceId(
						proInstanceID);
		if (needdelTaskList != null && needdelTaskList.size() > 0) {
			commandContext.getTaskEntityManager()
					.deleteTasksByProcessInstanceId(proInstanceID, null, true);
		}
		// 删除已存在历史任务记录
		HistoricTaskInstanceQueryImpl queryImpl = new HistoricTaskInstanceQueryImpl();
		queryImpl.processDefinitionId(hisentity.getProcessDefinitionId())
				.processInstanceId(proInstanceID).finished()
				.orderByHistoricTaskInstanceStartTime().desc().orderByTaskId()
				.desc();

		List<HistoricTaskInstance> needtaskdelList = commandContext
				.getHistoricTaskInstanceEntityManager()
				.findHistoricTaskInstancesByQueryCriteria(queryImpl);
		List<HistoricTaskInstance> needDelTaskList = new ArrayList<HistoricTaskInstance>();

		for (HistoricTaskInstance one : needtaskdelList) {
			needDelTaskList.add(one);
			if (hisactentity.getActivityId().equals(one.getTaskDefinitionKey())) {
				break;
			}
		}

		for (HistoricTaskInstance temp : needDelTaskList) {
			commandContext.getHistoricTaskInstanceEntityManager()
					.deleteHistoricTaskInstanceById(temp.getId());
		}
		// 创建新的待办任务
		TaskEntity task = TaskEntity.create();
		task.setNameWithoutCascade(hisactentity.getActivityName());
		Expression description = procesDefEntity.getTaskDefinitions()
				.get(hisactentity.getActivityId()).getDescriptionExpression();
		if (description != null) {
			task.setDescriptionWithoutCascade(description.getExpressionText());
		}
		task.setExecution(repalceOne);
		task.setCreateTime(new Date());
		task.setExecutionId(repalceOne.getId());
		task.setProcessInstanceId(proInstanceID);
		task.setProcessDefinitionId(hisentity.getProcessDefinitionId());
		task.setProcessInstance(repalceOne);
		task.setTaskDefinitionKeyWithoutCascade(procesDefEntity
				.getTaskDefinitions().get(hisactentity.getActivityId())
				.getKey());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IWorkFlowTypes.signalStatus,
				IWorkFlowTypes.SignalStatus.NotRunning);
		task.setExecutionVariables(parameters);
		// task.setAssignee(hisactentity.getAssignee());
		task.insert(task.getExecution());
		task.fireEvent(TaskListener.EVENTNAME_CREATE);
		// commandContext.getDbSqlSession().flush();
		// 新增一个历史活动记录
		IdGenerator idGenerator = Context.getProcessEngineConfiguration()
				.getIdGenerator();
		HistoricActivityInstanceEntity historicActivityInstance = new HistoricActivityInstanceEntity();
		historicActivityInstance.setId(idGenerator.getNextId());
		historicActivityInstance.setProcessDefinitionId(hisentity
				.getProcessDefinitionId());
		historicActivityInstance.setProcessInstanceId(proInstanceID);
		historicActivityInstance.setExecutionId(repalceOne.getId());
		historicActivityInstance.setActivityId(hisactentity.getActivityId());
		historicActivityInstance.setActivityName((String) changeTo
				.getProperty("name"));
		historicActivityInstance.setActivityType((String) changeTo
				.getProperty("type"));
		historicActivityInstance.setStartTime(ClockUtil.getCurrentTime());
		commandContext.getDbSqlSession().insert(historicActivityInstance);
		commandContext.getDbSqlSession().flush();
		// 设置任务分配权限
		new AddIdentityLinkCmd(task.getId(), hisactentity.getAssignee(), null,
				IdentityLinkType.ASSIGNEE).execute(commandContext);
		// 批量删除无用的历史活动记录
		new HisActivityDeleteCmd(delactList).execute(commandContext);
		return null;
	}
}
