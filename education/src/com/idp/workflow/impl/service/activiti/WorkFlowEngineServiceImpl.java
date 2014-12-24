package com.idp.workflow.impl.service.activiti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.cmd.AddIdentityLinkCmd;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.idp.workflow.event.action.ActionEventParamsHolder;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.cmd.activiti.HisActivityDeleteCmd;
import com.idp.workflow.impl.cmd.activiti.NewExecutionCreateCmd;
import com.idp.workflow.itf.service.IWorkFlowEngineService;
import com.idp.workflow.itf.service.identity.AuthenticationHolder;
import com.idp.workflow.itf.service.identity.IAccessController;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;
import com.idp.workflow.itf.service.runtime.IWorkFlowRunTime;
import com.idp.workflow.runtime.factory.activiti.IWorkRunTimeFactory;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.loader.activiti.ExtReflectUtil;
import com.idp.workflow.vo.identity.UserVO;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;
import com.idp.workflow.vo.pub.IWorkFlowTypes.SignalStatus;
import com.idp.workflow.vo.service.WfParametersVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;

/**
 * 工作流引擎服务实现
 * 
 * @author panfei
 * 
 */
@Transactional
public class WorkFlowEngineServiceImpl extends BaseServiceImpl implements
		IWorkFlowEngineService {

	/**
	 * 日志打印类
	 */
	private static final Log loger = LogFactory
			.getLog(WorkFlowEngineServiceImpl.class);

	public WorkFlowEngineServiceImpl() {
		super();
	}

	public WorkFlowEngineServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public ProcessInstanceVO start(String userId, String processDefinitionCode,
			String businessCode, WfParametersVO params)
			throws WfBusinessException {
		params = this.checkUserLegally(userId, params);
		IProInstanceMangeService tempProInstService = getProInstanceMangeService();
		return tempProInstService.startProcessInstanceByCode(
				processDefinitionCode, businessCode, params);
	}

	@Override
	public ProcessInstanceVO start(String userId, String processDefinitionId,
			WfParametersVO params) throws WfBusinessException {
		params = this.checkUserLegally(userId, params);
		IProInstanceMangeService tempProInstService = getProInstanceMangeService();
		return tempProInstService.startProcessInstanceById(processDefinitionId,
				params);
	}

	@Override
	public void signal(String userId, String proInstanceID,
			WfParametersVO params) throws WfBusinessException {
		this.checkProcessInstanceLegally(proInstanceID);
		params = this.checkUserLegally(userId, params);
		TaskQuery taskQuery = null;
		if (CheckType.AutoClaim == params
				.getTaskCheckType(SignalStatus.ForWardRunning)) {
			taskQuery = this.getEngine().getTaskService().createTaskQuery();
			// 检查是否有未分派的任务，进行认领
			List<Task> nedasstasklist = taskQuery
					.processInstanceId(proInstanceID).taskCandidateUser(userId)
					.list();
			if (nedasstasklist != null && nedasstasklist.size() != 0) {
				for (Task nedd : nedasstasklist) {
					this.getEngine().getTaskService()
							.claim(nedd.getId(), userId);
				}
			}
		}
		// 获取分派的任务，进行完成操作
		taskQuery = this.getEngine().getTaskService().createTaskQuery();
		List<Task> tasklist = taskQuery.processInstanceId(proInstanceID)
				.taskAssignee(userId).list();
		if (tasklist != null && tasklist.size() != 0) {
			for (Task nedd : tasklist) {
				params.putVariables(IWorkFlowTypes.signalStatus,
						IWorkFlowTypes.SignalStatus.ForWardRunning);
				if (!StringUtil.isEmpty(params.getReMark())) {
					this.getTaskManageService().addComment(userId,
							nedd.getId(), params.getReMark());
				}
				this.checkUserAllowedToSignal((TaskEntity) nedd, userId,
						params.getTaskCheckType(SignalStatus.ForWardRunning));
				this.getEngine().getTaskService()
						.complete(nedd.getId(), params.getContext());
			}
		} else {
			throw new WfBusinessException("未找到当前用户：" + userId + "对应的任务!");
		}
	}

	@Override
	public void rollBack(String userId, String proInstanceID,
			WfParametersVO params) throws WfBusinessException {
		this.checkProcessInstanceRollBackLegally(proInstanceID);
		params = this.checkUserLegally(userId, params);
		IProInstanceQueryService tempqueryServices = getProInstanceQueryService();
		ProcessInstanceVO retvo = tempqueryServices
				.queryProcessInstanceById(proInstanceID);
		// 判断流程是否已经结束
		if (retvo.isEnd()) {
			this.rollBackToLastTask(userId, proInstanceID, params);
		} else {
			if (IWorkFlowTypes.RollBackType.FirstTask.equals(params
					.getRollBackType())) {
				this.rollBackToFirstTask(userId, proInstanceID, params);
			} else {
				this.drawLineRollBack(userId, proInstanceID, params);
			}
		}
	}

	@Override
	public void termination(String userId, String proInstanceID,
			boolean isChecked) throws WfBusinessException {
		if (isChecked && StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("当前操作用户不能为空！");
		}
		WfParametersVO params = new WfParametersVO();
		if (isChecked) {
			params.putTaskCheckType(CheckType.SuperiorBack);
		} else {
			params.putTaskCheckType(CheckType.AnonymousBack);
		}
		IProInstanceMangeService tempProInstService = getProInstanceMangeService();
		// 挂起工作流
		tempProInstService.suspendProcessInstance(proInstanceID);
		// 回退所有任务，执行逆操作
		int count = this.getHistoricTaskQueryService()
				.queryNeedBackHistoricTaskVOCounts(proInstanceID);
		for (int i = 0; i < count; i++) {
			this.rollBack(userId, proInstanceID, params);
		}
		// 关闭工作流实例
	}

	/**
	 * 任务回退到最开始的发起任务节点
	 * 
	 * @param userId
	 * @param proInstanceID
	 * @param params
	 * @throws WfBusinessException
	 */
	private void rollBackToFirstTask(String userId, String proInstanceID,
			WfParametersVO params) throws WfBusinessException {
		// 判断当前用户是否有权限回退任务
		HistoricTaskInstance beforeHisTask = this
				.findLastDoneHistoricUserTaskInstance(proInstanceID);
		TaskEntity afterTask = this.findPrePareDoneTaskInstance(proInstanceID);

		this.checkUserAllowedToRollBack(beforeHisTask, afterTask, userId,
				params);
		// 查询流程实例和定义
		ProcessInstanceVO proinstvo = this.getProInstanceQueryService()
				.queryProcessInstanceById(proInstanceID);
		ProcessDefinitionEntity prodefentity = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
				.getEngine().getRepositoryService())
				.getDeployedProcessDefinition(proinstvo
						.getProcessDefinitionId()));
		// 查询当前活动节点和开始节点
		ActivityImpl lastact = this.findLastActivity(proInstanceID,
				prodefentity);
		ActivityImpl firstact = this.findFirstUserTaskActivity(proInstanceID,
				prodefentity);
		HistoricTaskInstance firsttask = this.findFirstUserTask(proInstanceID,
				prodefentity);
		// 设置跳级任务标识
		ActionEventParamsHolder.setCurrentActionEventParams(firsttask
				.getTaskDefinitionKey());
		// 查询待回滚任务
		List<ActivityImpl> tasksBackList = this.findBackTaskSerives(
				firstact.getId(), lastact.getId(), proInstanceID, prodefentity,
				proinstvo.getProcessDefinitionId(),
				IWorkFlowTypes.searechType_All);
		// 进行所有历史任务回滚操作
		this.rollBackListeners(tasksBackList, proInstanceID, params);
		// 回退到该用户起始任务节点
		this.replaceExecution(firstact, proInstanceID);
	}

	/**
	 * 任务回退到最近的一次任务操作
	 * 
	 * @param userId
	 *            当前操作用户
	 * @param proInstanceID
	 *            工作流实例
	 * @param params
	 *            参数
	 * @throws WfBusinessException
	 */
	private void rollBackToLastTask(String userId, String proInstanceID,
			WfParametersVO params) throws WfBusinessException {
		// 但流程结束时，判断之前的轨迹上是否存在一个用户手工任务，如果存在则回退到此任务节点，否则需要重新开始新的工作流实例
		ProcessInstanceVO proinstvo = this.getProInstanceQueryService()
				.queryProcessInstanceById(proInstanceID);
		List<ActivityImpl> needbackList = this.findEnableToBackTasks(
				proInstanceID, proinstvo.getProcessDefinitionId());
		if (needbackList.size() > 0) {
			// 获取最后一个用户任务
			ActivityImpl checkType = needbackList.get(needbackList.size() - 1);
			if (IWorkFlowTypes.activityType_UserTask.equals(checkType
					.getProperty("type"))) {
				// 判断是否有权限回退
				HistoricTaskInstance userTask = this.getEngine()
						.getHistoryService().createHistoricTaskInstanceQuery()
						.processInstanceId(proInstanceID)
						.taskDefinitionKey(checkType.getId()).singleResult();
				this.checkUserAllowedToRollBack(userTask, null, userId, params);
				// 进行所有任务回滚操作
				this.rollBackListeners(needbackList, proInstanceID, params);
				// 回退到该用户任务节点
				this.replaceExecution(checkType, proInstanceID);
			} else {
				// 通过终止和重新开始来实现
				unAbleToRollBackMustRestart();
			}
		} else {
			throw new WfBusinessException("无任何可以回退任务！");
		}
	}

	/**
	 * 不能回退必须重新开始
	 * 
	 * @throws WfBusinessException
	 */
	private static void unAbleToRollBackMustRestart()
			throws WfBusinessException {
		throw new WfBusinessException("请终止该工作流实例，重新开始一个新的工作流实例！");
	}

	/**
	 * 置换为传入的指定任务活动环节
	 * 
	 * @param changeTo
	 * @throws WfBusinessException
	 */
	private void replaceExecution(ActivityImpl changeTo, String proInstanceID)
			throws WfBusinessException {
		List<String> needDeleteList = new ArrayList<String>();
		this.findUnableHisActivties(needDeleteList, changeTo, proInstanceID);
		ServiceImpl executionServices = (ServiceImpl) this.getEngine()
				.getRuntimeService();
		executionServices.getCommandExecutor()
				.execute(
						new NewExecutionCreateCmd(changeTo.getId(),
								proInstanceID, needDeleteList
										.toArray(new String[needDeleteList
												.size()])));
		// this.deleteUnableHisActivties(needDeleteList);
	}

	/**
	 * 
	 * @param needDeleteList
	 * @return
	 */
	private String findUnableHisActivties(List<String> needDeleteList,
			ActivityImpl changeTo, String proInstanceID) {
		String retId = null;
		// 删除无用的历史活动记录
		List<HistoricActivityInstance> retlist = this.getEngine()
				.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(proInstanceID)
				.orderByHistoricActivityInstanceStartTime().desc()
				.orderByHistoricActivityInstanceId().desc().list();
		if (retlist != null && retlist.size() > 0) {
			for (HistoricActivityInstance delactOne : retlist) {
				needDeleteList.add(delactOne.getId());
				if (changeTo.getId().equals(delactOne.getActivityId())) {
					retId = delactOne.getId();
					break;
				}
			}
		}
		return retId;
	}

	/**
	 * 删除不用的活动历史记录，删除置换活动之后（含置换的活动）所有活动记录
	 * 
	 * @param changeTo
	 * @param proInstanceID
	 * @throws WfBusinessException
	 */
	@SuppressWarnings("unused")
	private void deleteUnableHisActivties(List<String> needDeleteList)
			throws WfBusinessException {
		// 删除无用的历史活动记录（因为画回退虚线产生的）
		ServiceImpl activitiservice = (ServiceImpl) this.getEngine()
				.getHistoryService();
		activitiservice.getCommandExecutor().execute(
				new HisActivityDeleteCmd(needDeleteList
						.toArray(new String[needDeleteList.size()])));
	}

	/**
	 * 返回所有需要回退的任务队列，如果遇见第一个用户任务节点，中断返回，否则返回全部是自动服务节点
	 * 
	 * @param proInstanceID
	 * @param processDefinitionId
	 * @return
	 * @throws WfBusinessException
	 */
	private List<ActivityImpl> findEnableToBackTasks(String proInstanceID,
			String processDefinitionId) throws WfBusinessException {
		List<ActivityImpl> TasksBackList = this.findBackTaskSerives(null, null,
				proInstanceID, null, processDefinitionId,
				IWorkFlowTypes.searechType_All);
		List<ActivityImpl> backToDoList = new ArrayList<ActivityImpl>();
		if (TasksBackList != null && TasksBackList.size() > 0) {
			for (ActivityImpl temp : TasksBackList) {
				backToDoList.add(temp);
				if (IWorkFlowTypes.activityType_UserTask.equals(temp
						.getProperty("type"))) {
					break;
				}
			}
		}
		return backToDoList;
	}

	/**
	 * 根据一个活动队列，逐个调用回滚方法，是一个比较公用的方法
	 * 
	 * @param list
	 * @throws WfBusinessException
	 */
	private void rollBackListeners(List<ActivityImpl> list,
			String proInstanceID, WfParametersVO params)
			throws WfBusinessException {
		if (list != null) {
			for (ActivityImpl temp : list) {
				if (IWorkFlowTypes.activityType_UserTask.equals(temp
						.getProperty("type"))
						|| IWorkFlowTypes.activityType_ServicesTask.equals(temp
								.getProperty("type"))) {
					IWorkRunTimeFactory.createWorkFlowRunTimeDelegater(temp)
							.backForWard(temp, proInstanceID,
									params.getContext());
				}
			}
		}
	}

	/**
	 * 通过画虚线回退任务
	 * 
	 * @param userVo
	 *            当前操作用户
	 * @param proInstanceID
	 *            工作流实例
	 * @param params
	 *            参数
	 * @throws WfBusinessException
	 */
	private void drawLineRollBack(String userId, String proInstanceID,
			WfParametersVO params) throws WfBusinessException {
		// 上一个手工驱动活动
		ActivityImpl Bef_activity = null;
		// 需要回退的自动服务队列
		List<ActivityImpl> autoServiesBackList = null;
		// 获取待办的任务实体
		TaskEntity After_Task = this.findPrePareDoneTaskInstance(proInstanceID);
		if (After_Task == null) {
			throw new WfBusinessException("回滚任务失败，该工作流程实例已经没有待办事务！");
		}
		// 工作流定义实体
		ProcessDefinitionEntity prodefentity = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
				.getEngine().getRepositoryService())
				.getDeployedProcessDefinition(After_Task
						.getProcessDefinitionId()));
		// 获取历史任务记录最后一个完成任务
		HistoricTaskInstance lastHisTask = this
				.findLastDoneHistoricUserTaskInstance(proInstanceID);
		if (lastHisTask != null) {
			// 检查回滚人权限
			this.checkUserAllowedToRollBack(lastHisTask, After_Task, userId,
					params);
			// 上一个环节活动id
			String Bef_actId = lastHisTask.getTaskDefinitionKey();
			// 获取上一个活动
			Bef_activity = findActivity(prodefentity,
					lastHisTask.getProcessDefinitionId(), Bef_actId);
			// 查找是否存在自动服务
			autoServiesBackList = this.findBackTaskSerives(Bef_actId,
					After_Task.getTaskDefinitionKey(), proInstanceID,
					prodefentity, lastHisTask.getProcessDefinitionId(),
					IWorkFlowTypes.searchType_OnlyServiceTask);
		} else {
			// 查找是否存在自动服务
			autoServiesBackList = this.findBackTaskSerives(null,
					After_Task.getTaskDefinitionKey(), proInstanceID,
					prodefentity, After_Task.getProcessDefinitionId(),
					IWorkFlowTypes.searchType_OnlyServiceTask);
			if (autoServiesBackList == null || autoServiesBackList.size() == 0) {
				throw new WfBusinessException("工作流任务尚未执行，不能回退任务！");
			} else {
				unAbleToRollBackMustRestart();
			}
		}

		if (lastHisTask != null) {
			String rollbackName = "rollbackto:" + After_Task.getId();
			// 取得待办活动环节
			ProcessInstance tempProcessInstance = this.getEngine()
					.getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(After_Task.getProcessInstanceId())
					.singleResult();
			ActivityImpl Afer_Activity = ((ProcessDefinitionImpl) prodefentity)
					.findActivity(After_Task.getTaskDefinitionKey());
			// 构建画回虚线
			List<PvmTransition> backTempTransition = new ArrayList<PvmTransition>();
			TransitionImpl backTran = this.createLineAndBind(Bef_activity,
					Afer_Activity, rollbackName, backTempTransition);
			// 关闭待办任务
			params.putVariables(rollbackName, 1);
			params.putVariables(IWorkFlowTypes.signalStatus,
					IWorkFlowTypes.SignalStatus.NotRunning);
			this.getEngine().getTaskService()
					.complete(After_Task.getId(), params.getContext());
			// 删除任务
			this.getEngine().getTaskService()
					.deleteTask(After_Task.getId(), true);
			this.getEngine().getTaskService()
					.deleteTask(lastHisTask.getId(), true);
			// 存在需要回退的自动任务
			if (autoServiesBackList != null) {
				for (ActivityImpl temback : autoServiesBackList) {
					IWorkRunTimeFactory.createWorkFlowRunTimeDelegater(temback)
							.backForWard(temback, proInstanceID,
									params.getContext());
				}
			}
			// 回退用户任务方法
			IWorkRunTimeFactory.createWorkFlowRunTimeDelegater(Bef_activity)
					.backForWard(Bef_activity, proInstanceID,
							params.getContext());
			// 删除无用的待办任务，历史任务记录跟着删除了
			this.deleteUnUseableTasks(lastHisTask, proInstanceID);
			// 删除活动历史
			this.deleteUnUseableActivitis(lastHisTask, proInstanceID);
			// 删除所画虚线
			this.dropLineAndClean(Bef_activity, After_Task, backTran,
					tempProcessInstance.getProcessDefinitionId(),
					backTempTransition);
			// 重新设置待办人权限
			this.reSetLastTaskAssigner(AuthenticationHolder
					.getCurrentAuthentiUsers().getTaskOwnerId(), proInstanceID);
		}
	}

	/**
	 * 根据回退人，重新设置任务的待办人
	 * 
	 * @param userid
	 */
	private void reSetLastTaskAssigner(String userId, String proInstanceID) {
		List<Task> tasks = this.getEngine().getTaskService().createTaskQuery()
				.processInstanceId(proInstanceID).orderByTaskCreateTime()
				.desc().list();
		ServiceImpl executionServices = (ServiceImpl) this.getEngine()
				.getTaskService();
		if (tasks != null) {
			for (Task temp : tasks) {
				executionServices.getCommandExecutor().execute(
						new AddIdentityLinkCmd(temp.getId(), userId, null,
								IdentityLinkType.ASSIGNEE));
			}
		}
	}

	/**
	 * 创建连接线，并设置目标活动
	 * 
	 * @param Bef_activity
	 * @param Afer_Activity
	 * @param After_Task
	 */
	private TransitionImpl createLineAndBind(ActivityImpl Bef_activity,
			ActivityImpl Afer_Activity, String rollbackName,
			List<PvmTransition> tempbacks) {
		// 备份之前的流程线
		if (Afer_Activity.getOutgoingTransitions() != null) {
			tempbacks.addAll(Afer_Activity.getOutgoingTransitions());
			Afer_Activity.getOutgoingTransitions().clear();
		}
		// 构建画回虚线
		TransitionImpl backTran = Afer_Activity
				.createOutgoingTransition("flowid:" + rollbackName);
		// TransitionImpl backTran = Afer_Activity
		// .createOutgoingTransition(rollbackName);
		backTran.setProperty(rollbackName, 1);
		backTran.setDestination(Bef_activity);
		// Afer_Activity.setProperty(IWorkFlowTypes.transition_Def,
		// rollbackName);
		return backTran;
	}

	/**
	 * 删除卸载连接线
	 * 
	 * @param Bef_activity
	 * @param processDefineId
	 */
	private void dropLineAndClean(ActivityImpl Bef_activity,
			TaskEntity After_Task, TransitionImpl backTran,
			String processDefineId, List<PvmTransition> backTempTransition) {
		ProcessDefinitionEntity prodefentity = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
				.getEngine().getRepositoryService())
				.getDeployedProcessDefinition(processDefineId));
		ActivityImpl AfterActivity = ((ProcessDefinitionImpl) prodefentity)
				.findActivity(After_Task.getTaskDefinitionKey());
		// 清空
		List<PvmTransition> removelist = AfterActivity.getOutgoingTransitions();
		List<PvmTransition> removelist2 = Bef_activity.getIncomingTransitions();
		removelist2.remove(backTran);
		// 直接全部清空也可以clear
		removelist.remove(backTran);
		// Bef_activity.setProperty(IWorkFlowTypes.transition_Def, null);
		if (backTempTransition != null && backTempTransition.size() > 0) {
			removelist.addAll(backTempTransition);
		}
	}

	/**
	 * 删除活动历史表重复出现的数据记录
	 * 
	 * @param lastHisTask
	 * @param proInstanceID
	 */
	private void deleteUnUseableActivitis(HistoricTaskInstance lastHisTask,
			String proInstanceID) {
		// 删除无用的历史活动记录
		List<HistoricActivityInstance> retlist = this.getEngine()
				.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(proInstanceID)
				.orderByHistoricActivityInstanceStartTime().desc()
				.orderByHistoricActivityInstanceId().desc().list();
		List<String> delids = new ArrayList<String>();

		if (retlist != null && retlist.size() > 0) {
			for (HistoricActivityInstance delactone : retlist) {

				if (lastHisTask.getTaskDefinitionKey().equals(
						delactone.getActivityId())
						&& delactone.getEndTime() == null) {
					continue;
				}

				if (lastHisTask.getTaskDefinitionKey().equals(
						delactone.getActivityId())
						&& delactone.getEndTime() != null
						&& lastHisTask.getExecutionId().equals(
								delactone.getExecutionId())) {
					delids.add(delactone.getId());
					break;
				}

				delids.add(delactone.getId());
			}
		}
		// 删除无用的历史活动记录（因为画回退虚线产生的）
		ServiceImpl activitiservice = (ServiceImpl) this.getEngine()
				.getHistoryService();
		activitiservice.getCommandExecutor().execute(
				new HisActivityDeleteCmd(delids.toArray(new String[delids
						.size()])));
	}

	/**
	 * 删除无用的待办任务，历史任务记录跟着删除了<br>
	 * 删除关闭事务可能引起的其它待办事务
	 * 
	 * @param lastHisTask
	 * @param proInstanceID
	 */
	private void deleteUnUseableTasks(HistoricTaskInstance lastHisTask,
			String proInstanceID) {
		List<Task> After_TaskList = this.getEngine().getTaskService()
				.createTaskQuery().processInstanceId(proInstanceID)
				.orderByTaskCreateTime().desc().orderByTaskId().desc().list();
		if (After_TaskList != null) {
			for (Task deltask : After_TaskList) {
				if (!deltask.getTaskDefinitionKey().equals(
						lastHisTask.getTaskDefinitionKey())) {
					this.getEngine().getTaskService()
							.deleteTask(deltask.getId(), true);
				}
			}
		}
	}

	/**
	 * 检查当前用户是否可以回滚此流程<br>
	 * 只有之前委托人才可以回退
	 * 
	 * @param lastHisTask
	 *            待回滚任务
	 * @param userVo
	 *            当前用户vo
	 * @throws WfBusinessException
	 */
	private void checkUserAllowedToRollBack(HistoricTaskInstance beforeHisTask,
			TaskEntity afterTask, String userId, WfParametersVO params)
			throws WfBusinessException {
		CheckType cktype = params
				.getTaskCheckType(SignalStatus.BackWardRunning);
		loger.info("用户回退模式为：" + cktype.name());
		IAccessController accessController = AccessControllerFactory
				.getInstance().createAccessController(cktype);
		UserVO beforeUser = new UserVO(beforeHisTask == null ? null
				: beforeHisTask.getAssignee(), null);
		UserVO afterUser = new UserVO(afterTask == null ? null
				: afterTask.getAssignee(), null);
		String realOperUserid = accessController.canPass(new UserVO(userId,
				null), beforeUser, afterUser, this);
		this.getEngine().getIdentityService()
				.setAuthenticatedUserId(realOperUserid);
		AuthenticationHolder.setCurrentAuthentiUsers(userId, realOperUserid,
				cktype);
		// 设置初始化参数
		String passTaskCodeName = null;
		String taskStageCode = null;
		String taskStageName = null;
		ActionEventParamsHolder.clear();

		if (CheckType.SuperiorBack.equals(cktype) && afterTask != null) {
			taskStageCode = afterTask.getTaskDefinitionKey();
			taskStageName = afterTask.getName();
		} else {
			taskStageCode = beforeHisTask.getTaskDefinitionKey();
			taskStageName = beforeHisTask.getName();
		}

		ActionEventParamsHolder.setCurrentActionEventParams(taskStageCode,
				taskStageName, passTaskCodeName);
	}

	/**
	 * 检查当前用户是否可以驱动此流程<br>
	 * 
	 * @param needToDoTask
	 *            待办任务
	 * @param userId
	 *            当前操作用户
	 * @param params
	 *            参数
	 * @throws WfBusinessException
	 */
	private void checkUserAllowedToSignal(TaskEntity needToDoTask,
			String userId, CheckType checkType) throws WfBusinessException {

		if (!needToDoTask.getAssignee().equals(userId)) {
			throw new WfBusinessException("当前登陆用户无权限处理该待办任务！");
		}

		this.getEngine().getIdentityService().setAuthenticatedUserId(userId);
		AuthenticationHolder.setCurrentAuthentiUsers(
				needToDoTask.getOwner() == null ? userId : needToDoTask
						.getOwner(), userId, checkType);
		// 设置参数
		String taskStageCode = needToDoTask.getTaskDefinitionKey();
		String taskStageName = needToDoTask.getName();
		ActionEventParamsHolder.clear();

		ActionEventParamsHolder.setCurrentActionEventParams(taskStageCode,
				taskStageName, null);
	}

	/**
	 * 根据最后一次完成任务id和下一个待办任务id查询出回退自动任务列表
	 * 
	 * @param Bef_actId
	 * @param After_actId
	 * @param proInstanceID
	 * @param prodefentity
	 * @param processDefinitionId
	 * @return
	 * @throws WfBusinessException
	 */
	private List<ActivityImpl> findBackTaskSerives(String Bef_actId,
			String After_actId, String proInstanceID,
			ProcessDefinitionEntity prodefentity, String processDefinitionId,
			String searchType) throws WfBusinessException {
		List<HistoricActivityInstance> retlist = this.getEngine()
				.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(proInstanceID)
				.orderByHistoricActivityInstanceStartTime().asc()
				.orderByHistoricActivityInstanceId().asc().list();
		return this.findBackTaskSerives(Bef_actId, After_actId, retlist,
				prodefentity, processDefinitionId, searchType);
	}

	/**
	 * 根据最后一次完成任务id和下一个待办任务id查询出回退自动任务列表
	 * 
	 * @param Bef_actId
	 * @param After_actId
	 * @param hislist
	 * @param prodefentity
	 * @param processDefinitionId
	 * @return
	 * @throws WfBusinessException
	 */
	private List<ActivityImpl> findBackTaskSerives(String Bef_actId,
			String After_actId, List<HistoricActivityInstance> hislist,
			ProcessDefinitionEntity prodefentity, String processDefinitionId,
			String searchType) throws WfBusinessException {
		Map<String, Integer> hisactivitis = new HashMap<String, Integer>();
		if (hislist != null) {
			for (HistoricActivityInstance temphis : hislist) {
				if (hisactivitis.containsKey(temphis.getActivityId())) {
					int count = hisactivitis.get(temphis.getActivityId())
							.intValue() + 1;
					hisactivitis.put(temphis.getActivityId(), count);
				} else {
					hisactivitis.put(temphis.getActivityId(),
							Integer.valueOf(1));
				}
			}
		}
		return this.findBackTaskSerives(Bef_actId, After_actId, hisactivitis,
				prodefentity, processDefinitionId, searchType);
	}

	/**
	 * 根据最后一次完成任务id和下一个待办任务id查询出回退自动任务列表
	 * 
	 * @param Bef_actId
	 * @param After_actId
	 * @return
	 * @throws WfBusinessException
	 */
	private List<ActivityImpl> findBackTaskSerives(String Bef_actId,
			String After_actId, Map<String, Integer> hisactivitis,
			ProcessDefinitionEntity prodefentity, String processDefinitionId,
			String searchType) throws WfBusinessException {

		if (prodefentity == null) {
			prodefentity = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
					.getEngine().getRepositoryService())
					.getDeployedProcessDefinition(processDefinitionId));
		}

		Map<Integer, NodePath> resultMap = new HashMap<Integer, NodePath>();
		// begin mark
		ActivityImpl Bef_act = null;
		if (Bef_actId == null) {
			Bef_act = findBeginActivity(prodefentity);
		} else {
			Bef_act = prodefentity.findActivity(Bef_actId);
		}
		// end mark
		ActivityImpl After_act = null;
		if (After_actId == null) {
			After_act = findEndActivity(prodefentity);
		} else {
			After_act = prodefentity.findActivity(After_actId);
		}

		findBestWay(Bef_act, After_act, resultMap, null, hisactivitis,
				searchType);
		return getMaxScoreActList(resultMap, searchType);
	}

	/**
	 * 获取分数最高的匹配路径
	 * 
	 * @param resultMap
	 * @return
	 */
	private List<ActivityImpl> getMaxScoreActList(
			Map<Integer, NodePath> resultMap, String searchType)
			throws WfBusinessException {
		NodePath maxNode = null;
		if (resultMap.size() > 0) {
			Set<Integer> keys = resultMap.keySet();
			for (Integer tempkey : keys) {
				NodePath compareNode = resultMap.get(tempkey);
				if (maxNode == null) {
					maxNode = compareNode;
				} else {
					if (compareNode.getMatchScore() > maxNode.getMatchScore()) {
						maxNode = compareNode;
					}
				}
			}
			if (!StringUtil.isEmpty(maxNode.getErroMsg())) {
				throw new WfBusinessException(maxNode.getErroMsg());
			}
			return filterUnAbleActivity(maxNode.getPaths(), searchType);
		}
		return null;
	}

	/**
	 * 删除不符合用途的活动 目前对“开始结束环节”、“分支环节”做删除
	 * 
	 * @param checklist
	 * @return
	 */
	private List<ActivityImpl> filterUnAbleActivity(
			List<ActivityImpl> checklist, String searchType) {
		for (int i = checklist.size() - 1; i >= 0; i--) {
			// String act_typr = checklist.get(i).getId();
			String act_type = StringUtil.convertString(checklist.get(i)
					.getProperty("type"));
			if (act_type.equals(IWorkFlowTypes.activityType_endEvent)
					|| act_type.equals(IWorkFlowTypes.activityType_startEvent)
					|| act_type.equals(IWorkFlowTypes.activityType_Gateway)
					|| act_type
							.equals(IWorkFlowTypes.activityType_exclusiveGateway)
					|| act_type
							.equals(IWorkFlowTypes.activityType_parallelGateway)) {
				checklist.remove(i);
				continue;
			}

			if (IWorkFlowTypes.searchType_OnlyServiceTask.equals(searchType)) {
				if (!IWorkFlowTypes.activityType_ServicesTask.equals(act_type)) {
					checklist.remove(i);
					continue;
				}
			}

			if (IWorkFlowTypes.searchType_OnlyUserTask.equals(searchType)) {
				if (!IWorkFlowTypes.activityType_UserTask.equals(act_type)) {
					checklist.remove(i);
					continue;
				}
			}
		}
		return checklist;
	}

	/**
	 * 找到第一个开始活动
	 * 
	 * @param prodefentity
	 * @return
	 */
	private ActivityImpl findBeginActivity(ProcessDefinitionEntity prodefentity) {
		List<ActivityImpl> allacts = prodefentity.getActivities();
		for (ActivityImpl tempone : allacts) {
			if (IWorkFlowTypes.activityType_startEvent.equals(tempone
					.getProperty("type"))) {
				return tempone;
			}
		}
		return null;
	}

	/**
	 * 查找第一个手工用户活动
	 * 
	 * @param prodefentity
	 * @return
	 */
	private ActivityImpl findFirstUserTaskActivity(String processInstanceId,
			ProcessDefinitionEntity prodefentity) {
		HistoricTaskInstance firstHisTask = this.findFirstUserTask(
				processInstanceId, prodefentity);
		ActivityImpl reActivity = ((ProcessDefinitionImpl) prodefentity)
				.findActivity(firstHisTask.getTaskDefinitionKey());
		return reActivity;
	}

	/**
	 * 查找第一个手工用户任务
	 * 
	 * @param processInstanceId
	 * @param prodefentity
	 * @return
	 */
	private HistoricTaskInstance findFirstUserTask(String processInstanceId,
			ProcessDefinitionEntity prodefentity) {
		List<HistoricTaskInstance> result = this.getEngine()
				.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished()
				.orderByHistoricTaskInstanceStartTime().asc().orderByTaskId()
				.asc().list();
		HistoricTaskInstance firstHisTask = result.get(0);
		return firstHisTask;
	}

	/**
	 * 找到最后一个结束活动
	 * 
	 * @param prodefentity
	 * @return
	 */
	private ActivityImpl findEndActivity(ProcessDefinitionEntity prodefentity) {
		List<ActivityImpl> allacts = prodefentity.getActivities();
		for (ActivityImpl tempone : allacts) {
			if (IWorkFlowTypes.activityType_endEvent.equals(tempone
					.getProperty("type"))) {
				return tempone;
			}
		}
		return null;
	}

	/**
	 * 查找两点之间最佳匹配路径
	 * 
	 * @param beg_act
	 *            开始节点
	 * @param end_act
	 *            结束节点
	 * @param resultMap
	 *            路径结果
	 * @param nodePath
	 *            当前路径
	 * @param hisactivitis
	 *            历史参考信息
	 */
	private void findBestWay(ActivityImpl beg_act, ActivityImpl end_act,
			Map<Integer, NodePath> resultMap, NodePath nodePath,
			Map<String, Integer> hisactivitis, String searchType) {
		// first check
		if (beg_act == null || resultMap == null) {
			return;
		}
		// first set
		if (resultMap != null && resultMap.size() == 0 && nodePath == null) {
			NodePath firstOne = new NodePath();
			firstOne.addPath(end_act, searchType);
			resultMap.put(Integer.valueOf(resultMap.size()), firstOne);
			nodePath = firstOne;
		}
		// 从尾向头去找
		List<PvmTransition> goForNextsList = end_act.getIncomingTransitions();

		if (goForNextsList != null && goForNextsList.size() > 0) {
			int count = 0;
			int macthSorce = 0;
			for (PvmTransition temp : goForNextsList) {
				if (temp instanceof TransitionImpl) {
					ActivityImpl goForNextOne = (ActivityImpl) temp.getSource();
					if (!nodePath.contains(goForNextOne)) {
						if (count == 0) {
							macthSorce = nodePath.getMatchScore();
							if (hisactivitis != null
									&& hisactivitis.containsKey(goForNextOne
											.getId())) {
								nodePath.addMatchScore();
							}
							nodePath.addPath(goForNextOne, searchType);
							this.findBestWay(beg_act, goForNextOne, resultMap,
									nodePath, hisactivitis, searchType);
						} else {
							NodePath newpath = nodePath.copy(end_act,
									macthSorce);
							resultMap.put(Integer.valueOf(resultMap.size()),
									newpath);
							if (hisactivitis != null
									&& hisactivitis.containsKey(goForNextOne
											.getId())) {
								newpath.addMatchScore();
							}
							newpath.addPath(goForNextOne, searchType);
							this.findBestWay(beg_act, goForNextOne, resultMap,
									newpath, hisactivitis, searchType);
						}
					}
				}
				count++;
			}
		}
	}

	/**
	 * 查找下一个待办事务
	 * 
	 * @param proInstanceID
	 * @return
	 */
	private TaskEntity findPrePareDoneTaskInstance(String proInstanceID) {
		// 取下一个待办事务列表
		/*
		 * List<HistoricTaskInstance> Afer_Tasks = this.getEngine()
		 * .getHistoryService().createHistoricTaskInstanceQuery()
		 * .processInstanceId(proInstanceID).unfinished().list(); if (Afer_Tasks
		 * != null && Afer_Tasks.size() > 0) { return Afer_Tasks.get(0); }
		 */
		TaskEntity After_Task = (TaskEntity) this.getEngine().getTaskService()
				.createTaskQuery().processInstanceId(proInstanceID)
				.singleResult();
		return After_Task;
	}

	/**
	 * 返回自动服务对应的lisenter
	 * 
	 * @param autoServiesBackList
	 * @return
	 * @throws WfBusinessException
	 */
	@SuppressWarnings("unused")
	private List<IWorkFlowRunTime> findAutoTaskListeners(
			List<ActivityImpl> autoServiesBackList) throws WfBusinessException {
		if (autoServiesBackList != null && autoServiesBackList.size() > 0) {
			List<IWorkFlowRunTime> retlist = new ArrayList<IWorkFlowRunTime>();
			for (ActivityImpl temp : autoServiesBackList) {
				ActivityBehavior actbeanvoir = (ClassDelegate) temp
						.getActivityBehavior();
				if (actbeanvoir instanceof ClassDelegate) {
					ClassDelegate classdel = (ClassDelegate) actbeanvoir;
					TaskListener temptask = (TaskListener) ExtReflectUtil
							.invokeMethoad(classdel, "getTaskListenerInstance",
									null);
					if (temptask instanceof IWorkFlowRunTime) {
						retlist.add((IWorkFlowRunTime) temptask);
					}
				}

			}
			return retlist;
		}
		return null;
	}

	/**
	 * 查找上一个任务对应的listener
	 * 
	 * @param processDefinitionId
	 *            工作流程定义id
	 * @param taskDefinitionKey
	 *            任务定义
	 * @return
	 * @throws WfBusinessException
	 */
	@SuppressWarnings("unused")
	private List<IWorkFlowRunTime> findBeforeTaskListener(
			ProcessDefinitionEntity prodefentity, String processDefinitionId,
			String taskDefinitionKey) throws WfBusinessException {
		List<IWorkFlowRunTime> retlist = new ArrayList<IWorkFlowRunTime>();
		if (prodefentity == null) {
			prodefentity = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
					.getEngine().getRepositoryService())
					.getDeployedProcessDefinition(processDefinitionId));
		}
		TaskDefinition taskDefinition = prodefentity.getTaskDefinitions().get(
				taskDefinitionKey);
		List<TaskListener> tasklist = taskDefinition
				.getTaskListener(TaskListener.EVENTNAME_COMPLETE);
		if (tasklist != null) {
			for (TaskListener temp : tasklist) {
				if (temp instanceof ClassDelegate) {
					ClassDelegate classdel = (ClassDelegate) temp;
					// update for 5.14 by panfei
					TaskListener temptask = (TaskListener) ExtReflectUtil
							.invokeMethoad(classdel, "getTaskListenerInstance",
									null);
					if (temptask instanceof IWorkFlowRunTime) {
						retlist.add((IWorkFlowRunTime) temptask);
					}
				}
			}
		}
		if (retlist.size() > 0) {
			return retlist;
		}
		return null;
	}

	/**
	 * 查询工作流定义实体
	 * 
	 * @param proInstanceID
	 * @param Bef_actId
	 * @return
	 */
	private ActivityImpl findActivity(ProcessDefinitionEntity prodefentity,
			String processDefinitionId, String actId) {
		if (prodefentity == null) {
			prodefentity = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
					.getEngine().getRepositoryService())
					.getDeployedProcessDefinition(processDefinitionId));
		}
		ActivityImpl reActivity = ((ProcessDefinitionImpl) prodefentity)
				.findActivity(actId);
		return reActivity;
	}

	/**
	 * 查询最后一个执行完成的活动
	 * 
	 * @param proInstanceID
	 * @param processDefinitionId
	 * @return
	 */
	private ActivityImpl findLastActivity(String proInstanceID,
			ProcessDefinitionEntity prodefentity) {
		List<HistoricActivityInstance> actresult = this.getEngine()
				.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(proInstanceID).finished()
				.orderByHistoricActivityInstanceStartTime().desc()
				.orderByHistoricActivityInstanceId().desc().list();
		HistoricActivityInstance lasthisact = actresult.get(0);
		return this.findActivity(prodefentity, prodefentity.getId(),
				lasthisact.getActivityId());
	}

	/**
	 * 根据工作流实例id查询目前为止最后一个执行任务
	 * 
	 * @param proInstanceID
	 *            工作流实例id
	 * @return
	 */
	private HistoricTaskInstance findLastDoneHistoricUserTaskInstance(
			String proInstanceID) {

		List<HistoricTaskInstance> taskresult = this.getEngine()
				.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(proInstanceID)
				.orderByHistoricTaskInstanceStartTime().desc().orderByTaskId()
				.desc().list();

		if (taskresult != null && taskresult.size() > 0) {
			for (HistoricTaskInstance tmpone : taskresult) {
				if (tmpone.getEndTime() != null) {
					return tmpone;
				}
			}
		}
		return null;
	}

	/**
	 * 匹配流程路径信息
	 * 
	 * @author panfei
	 * 
	 */
	public class NodePath {

		public static final String notRollBack_InParallelGateWay = "正在并行任务处理中，不能驳回任务！";

		/**
		 * 一条可能的连通匹配路径
		 */
		private List<ActivityImpl> paths = new ArrayList<ActivityImpl>();

		/**
		 * 是否有价值
		 */
		private boolean valuably = true;

		/**
		 * 错误原因
		 */
		private String erroMsg;

		/**
		 * 匹配分数
		 */
		private int matchScore = 0;

		public void setMatchScore(int matchScore) {
			this.matchScore = matchScore;
		}

		public int getMatchScore() {
			return matchScore;
		}

		public void addMatchScore() {
			this.matchScore++;
		}

		public void subMatchScore() {
			this.matchScore--;
		}

		public List<ActivityImpl> getPaths() {
			return paths;
		}

		public boolean contains(ActivityImpl one) {
			return this.getPaths().contains(one);
		}

		public void addPath(ActivityImpl one, String filterType) {
			if (one == null || !this.valuably) {
				return;
			}
			String act_type = StringUtil.convertString(one.getProperty("type"));
			// 发现并行节点，不会再继续添加内容
			if (IWorkFlowTypes.activityType_parallelGateway.equals(act_type)) {
				this.valuably = false;
				this.erroMsg = notRollBack_InParallelGateWay;
				return;
			}
			/*
			 * if (IWorkFlowTypes.activityType_Gateway.equals(act_type) ||
			 * IWorkFlowTypes.activityType_parallelGateway .equals(act_type) ||
			 * IWorkFlowTypes.activityType_subProcess.equals(act_type)) {
			 * return; }
			 */
			if (this.valuably) {
				this.paths.add(one);
			}
		}

		/**
		 * 拷贝
		 * 
		 * @return
		 */
		public NodePath copy(ActivityImpl act_end, int macthSorces) {
			NodePath copyone = new NodePath();
			List<ActivityImpl> copy_paths = new ArrayList<ActivityImpl>();
			List<ActivityImpl> parent_paths = this.getPaths();
			for (ActivityImpl tmp : parent_paths) {
				copy_paths.add(tmp);
				if (act_end.getId().equals(tmp.getId())) {
					break;
				}
			}
			copyone.setPaths(copy_paths);
			copyone.setMatchScore(macthSorces);
			/*
			 * for (ActivityImpl checktmp : copy_paths) { if
			 * (hisactivitis.containsKey(checktmp.getId())) {
			 * copyone.addMatchScore(); } }
			 */
			return copyone;
		}

		public void setPaths(List<ActivityImpl> paths) {
			this.paths = paths;
		}

		/**
		 * 获取错误
		 * 
		 * @return
		 */
		public String getErroMsg() {
			// if (this.getPaths() == null || this.getPaths().size() == 0) {
			if (!StringUtil.isEmpty(this.erroMsg)) {
				return this.erroMsg;
			}
			// }
			return null;
		}
	}

	@Override
	public void signal(String userId, String processDefinitionCode,
			String businessCode, WfParametersVO params)
			throws WfBusinessException {
		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例业务编码不能为空！");
		}

		this.checkProcessInstanceLegally(processDefinitionCode, businessCode);
		params = this.checkUserLegally(userId, params);
		if (CheckType.AutoClaim == params
				.getTaskCheckType(SignalStatus.ForWardRunning)) {
			// 检查是否有未分派的任务，进行认领
			List<Task> nedasstasklist = this
					.getTaskQuery(processDefinitionCode, businessCode)
					.taskCandidateUser(userId).list();
			if (nedasstasklist != null && nedasstasklist.size() != 0) {
				for (Task nedd : nedasstasklist) {
					this.getEngine().getTaskService()
							.claim(nedd.getId(), userId);
				}
			}
		}
		// 获取分派的任务，进行完成操作
		List<Task> tasklist = this
				.getTaskQuery(processDefinitionCode, businessCode)
				.taskAssignee(userId).list();
		if (tasklist != null && tasklist.size() != 0) {
			for (Task nedd : tasklist) {
				params.putVariables(IWorkFlowTypes.signalStatus,
						IWorkFlowTypes.SignalStatus.ForWardRunning);
				if (!StringUtil.isEmpty(params.getReMark())) {
					this.getTaskManageService().addComment(userId,
							nedd.getId(), params.getReMark());
				}
				this.checkUserAllowedToSignal((TaskEntity) nedd, userId,
						params.getTaskCheckType(SignalStatus.ForWardRunning));
				this.getEngine().getTaskService()
						.complete(nedd.getId(), params.getContext());
			}
		} else {
			throw new WfBusinessException("未找到当前用户：" + userId + "对应的任务!");
		}
	}

	@Override
	public void rollBack(String userId, String processDefinitionCode,
			String businessCode, WfParametersVO params)
			throws WfBusinessException {
		Collection<ProcessInstanceVO> proints = this
				.getProInstanceQueryService().queryProcessInstancesByBusiCode(
						processDefinitionCode, businessCode, null);
		if (proints != null && proints.size() > 0) {
			for (ProcessInstanceVO tempone : proints) {
				this.rollBack(userId, tempone.getId(), params);
			}
		} else {
			throw new WfBusinessException("未找到对应的工作流实例！");
		}
	}

	@Override
	public void termination(String userId, String processDefinitionCode,
			String businessCode, boolean isChecked) throws WfBusinessException {
		Collection<ProcessInstanceVO> proints = this
				.getProInstanceQueryService().queryProcessInstancesByBusiCode(
						processDefinitionCode, businessCode, null);
		if (proints != null && proints.size() > 0) {
			for (ProcessInstanceVO tempone : proints) {
				this.termination(userId, tempone.getId(), isChecked);
			}
		} else {
			throw new WfBusinessException("未找到对应的工作流实例！");
		}
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}
}
