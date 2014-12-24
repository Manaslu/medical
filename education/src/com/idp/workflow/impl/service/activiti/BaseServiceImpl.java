package com.idp.workflow.impl.service.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.TaskQuery;

import com.idp.workflow.event.action.ActionEvent;
import com.idp.workflow.event.action.ActionEventDispatcher;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.filter.NoneTranstacionMangerFilter;
import com.idp.workflow.itf.dao.IDaoMapper;
import com.idp.workflow.itf.filter.IFilter;
import com.idp.workflow.itf.service.ILocater;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;
import com.idp.workflow.itf.service.task.ITaskManageService;
import com.idp.workflow.itf.service.task.ITaskQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.service.Context;
import com.idp.workflow.util.service.LocaterServiceFactory;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.service.WfParametersVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;

/**
 * 服务基础类 虽然 ILocater 完全可以通过LocaterServiceFactory获取，但是建议服务之间传递
 * 
 * @see com.idp.workflow.util.service.LocaterServiceFactory
 * @author panfei
 * 
 */
public class BaseServiceImpl {

	public static void fireEvent(ActionEvent event) throws WfBusinessException {
		ActionEventDispatcher.getInstance().dispatchEvent(event);
	}

	private ProcessEngine engine;

	public ProcessEngine getEngine() {
		return engine;
	}

	public ILocater getLocater() {
		if (locater == null) {
			return LocaterServiceFactory.GetInstance();
		}
		return locater;
	}

	public void setEngine(ProcessEngine engine) {
		this.engine = engine;
	}

	private ILocater locater;

	public void setLocater(ILocater locater) {
		this.locater = locater;
	}

	public BaseServiceImpl() {
		super();
		this.setFirstFilter(new NoneTranstacionMangerFilter());
	}

	public BaseServiceImpl(ProcessEngine engine) {
		this();
		this.engine = engine;
	}

	public BaseServiceImpl(ProcessEngine engine, ILocater locater) {
		this();
		this.engine = engine;
		this.locater = locater;
	}

	/**
	 * 检查用户合法
	 * 
	 * @param userVo
	 * @param params
	 * @return
	 * @throws WfBusinessException
	 */
	public WfParametersVO checkUserLegally(String userId, WfParametersVO params)
			throws WfBusinessException {
		if (params == null) {
			params = new WfParametersVO();
		}

		if (!StringUtil.isEmpty(userId)) {
			params.putVariables(IWorkFlowTypes.operationUser, userId);
		} else {
			throw new WfBusinessException("当前操作用户不能为空！");
		}
		return params;
	}

	/**
	 * 检查工作溜实例是否可以回退
	 * 
	 * @param proInstanceID
	 * @throws WfBusinessException
	 */
	public void checkProcessInstanceRollBackLegally(String proInstanceID)
			throws WfBusinessException {
		ProcessInstanceVO proinstvo = this.getProInstanceQueryService()
				.queryProcessInstanceById(proInstanceID);
		if (proinstvo == null) {
			throw new WfBusinessException("未找到该工作流实例，不能提供服务！");
		}

		if (proinstvo.isSuspended()) {
			throw new WfBusinessException("该工作流实例已经挂起终止，不能提供服务！");
		}
		/*
		 * if (proinstvo.isEnd()) {
		 * 
		 * }
		 */
	}

	/**
	 * 检查工作流实例是否可用
	 * 
	 * @param proInstanceID
	 * @throws WfBusinessException
	 */
	public void checkProcessInstanceLegally(String proInstanceID)
			throws WfBusinessException {
		ProcessInstance proinst = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery().processInstanceId(proInstanceID)
				.singleResult();
		if (proinst == null || proinst.isEnded()) {
			throw new WfBusinessException("工作流实例已经结束，不能提供服务！");
		}

		if (proinst.isSuspended()) {
			throw new WfBusinessException("工作流实例已经挂起终止，不能提供服务！");
		}
	}

	/**
	 * 检查工作流实例是否可用
	 * 
	 * @param processDefinitionCode
	 * @param businessCode
	 * @throws WfBusinessException
	 */
	public void checkProcessInstanceLegally(String processDefinitionCode,
			String businessCode) throws WfBusinessException {
		ProcessInstanceQuery temquery = getProcessInstanceQuery(
				processDefinitionCode, businessCode);

		if (temquery.list() == null || temquery.list().size() == 0) {
			throw new WfBusinessException("工作流实例已经结束，不能提供服务！");
		}

		if (temquery.list().size() > 1) {
			throw new WfBusinessException("不能同时驱动多个工作流实例前进，请使用批量服务接口！");
		}

		ProcessInstance proinst = temquery.list().get(0);

		if (proinst.isEnded()) {
			throw new WfBusinessException("工作流实例已经结束，不能提供服务！");
		}

		if (proinst.isSuspended()) {
			throw new WfBusinessException("工作流实例已经挂起终止，不能提供服务！");
		}
	}

	/**
	 * 初始化工作任务查询
	 * 
	 * @param processDefinitionCode
	 * @param businessCode
	 * @return
	 */
	public TaskQuery getTaskQuery(String processDefinitionCode,
			String businessCode) {
		TaskQuery taskQuery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			taskQuery = taskQuery.processDefinitionKey(processDefinitionCode);
		}
		if (!StringUtil.isEmpty(businessCode)) {
			taskQuery = taskQuery.processInstanceBusinessKey(businessCode);
		}
		return taskQuery;
	}

	/**
	 * 初始化工作流程查询
	 * 
	 * @param processDefinitionCode
	 * @param businessCode
	 * @return
	 */
	public ProcessInstanceQuery getProcessInstanceQuery(
			String processDefinitionCode, String businessCode) {
		ProcessInstanceQuery tempquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			tempquery = tempquery.processDefinitionKey(processDefinitionCode);
		}
		if (!StringUtil.isEmpty(businessCode)) {
			tempquery = tempquery.processInstanceBusinessKey(businessCode);
		}
		return tempquery;
	}

	/**
	 * 初始化工作流实例服务
	 * 
	 * @param engine
	 * @param locater
	 * @return
	 */
	public IProInstanceMangeService getProInstanceMangeService() {
		if (getLocater().contains(IProInstanceMangeService.class)) {
			return getLocater().lookup(IProInstanceMangeService.class);
		}
		return new ProInstanceMangeServiceImpl(getEngine());
	}

	/**
	 * 初始化工作流实例查询服务
	 * 
	 * @param engine
	 * @param locater
	 * @return
	 */
	public IProInstanceQueryService getProInstanceQueryService() {
		if (getLocater().contains(IProInstanceQueryService.class)) {
			return getLocater().lookup(IProInstanceQueryService.class);
		}
		return new ProInstanceQueryServiceImpl(getEngine());
	}

	/**
	 * 初始化工作流定义查询服务
	 * 
	 * @return
	 */
	public IProDefineQueryService getProDefineQueryService() {
		if (getLocater().contains(IProDefineQueryService.class)) {
			return getLocater().lookup(IProDefineQueryService.class);
		}
		return new ProDefineQueryServiceImpl(getEngine());
	}

	/**
	 * 初始化工作流历史任务管理服务
	 * 
	 * @return
	 */
	public IHistoricTaskManageService getHistoricTaskManageService() {
		if (getLocater().contains(IHistoricTaskManageService.class)) {
			return getLocater().lookup(IHistoricTaskManageService.class);
		}
		return new HistoricTaskManageServiceImpl(getEngine());
	}

	/**
	 * 历史任务查询服务
	 * 
	 * @return
	 */
	public IHistoricTaskQueryService getHistoricTaskQueryService() {
		if (getLocater().contains(IHistoricTaskQueryService.class)) {
			return getLocater().lookup(IHistoricTaskQueryService.class);
		}
		return new HistoricTaskQueryServiceImpl(getEngine());
	}

	/**
	 * 获取任务管理服务
	 * 
	 * @return
	 */
	public ITaskManageService getTaskManageService() {
		if (getLocater().contains(ITaskManageService.class)) {
			return getLocater().lookup(ITaskManageService.class);
		}
		return new TaskManageServiceImpl(getEngine());
	}

	/**
	 * 获取任务管理查询服务
	 * 
	 * @return
	 */
	public ITaskQueryService getTaskQueryService() {
		if (getLocater().contains(ITaskQueryService.class)) {
			return getLocater().lookup(ITaskQueryService.class);
		}
		return new TaskQueryServiceImpl(getEngine());
	}

	/**
	 * 过滤器
	 */
	private IFilter<WfParametersVO> firstFilter;

	public IFilter<WfParametersVO> getFirstFilter() {
		return firstFilter;
	}

	public void setFirstFilter(IFilter<WfParametersVO> firstFilter) {
		if (this.getFirstFilter() != null) {
			this.getFirstFilter().setNextFilter(firstFilter);
		} else {
			this.firstFilter = firstFilter;
		}
	}

	public IDaoMapper getBaseDaoMapper() {
		return Context.createBaseDaoMapper();
	}
}
