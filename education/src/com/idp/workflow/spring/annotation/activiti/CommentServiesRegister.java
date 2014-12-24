package com.idp.workflow.spring.annotation.activiti;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.idp.workflow.impl.service.activiti.DentityManageServiceImpl;
import com.idp.workflow.impl.service.activiti.DentityQueryServiceImpl;
import com.idp.workflow.impl.service.activiti.HistoricTaskManageServiceImpl;
import com.idp.workflow.impl.service.activiti.HistoricTaskQueryServiceImpl;
import com.idp.workflow.impl.service.activiti.ProDefineMangeServiceImpl;
import com.idp.workflow.impl.service.activiti.ProDefineQueryServiceImpl;
import com.idp.workflow.impl.service.activiti.ProInstanceMangeServiceImpl;
import com.idp.workflow.impl.service.activiti.ProInstanceQueryServiceImpl;
import com.idp.workflow.impl.service.activiti.TaskManageServiceImpl;
import com.idp.workflow.impl.service.activiti.TaskQueryServiceImpl;
import com.idp.workflow.impl.service.activiti.WorkFlowEngineServiceImpl;
import com.idp.workflow.itf.service.IWorkFlowEngineService;
import com.idp.workflow.itf.service.identity.IDentityManageService;
import com.idp.workflow.itf.service.identity.IDentityQueryService;
import com.idp.workflow.itf.service.prodef.IProDefineMangeService;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;
import com.idp.workflow.itf.service.task.ITaskManageService;
import com.idp.workflow.itf.service.task.ITaskQueryService;

/**
 * 注解实现工作流服务注册
 * 
 * @author panfei
 * 
 */
@Configuration
public class CommentServiesRegister {

	@Bean(name = "com.idp.workflow.itf.service.IWorkFlowEngineService")
	public IWorkFlowEngineService getWorkFlowEngineService() {
		return new WorkFlowEngineServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.prodef.IProDefineMangeService")
	public IProDefineMangeService getProDefineMangeService() {
		return new ProDefineMangeServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.prodef.IProDefineQueryService")
	public IProDefineQueryService getProDefineQueryService() {
		return new ProDefineQueryServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.proinst.IProInstanceMangeService")
	public IProInstanceMangeService getProInstanceMangeService() {
		return new ProInstanceMangeServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.proinst.IProInstanceQueryService")
	public IProInstanceQueryService getProInstanceQueryService() {
		return new ProInstanceQueryServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.task.ITaskQueryService")
	public ITaskQueryService getTaskQueryService() {
		return new TaskQueryServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.task.ITaskManageService")
	public ITaskManageService getTaskManageService() {
		return new TaskManageServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.identity.IDentityManageService")
	public IDentityManageService getDentityManageService() {
		return new DentityManageServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.identity.IDentityQueryService")
	public IDentityQueryService getDentityQueryService() {
		return new DentityQueryServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.task.IHistoricTaskQueryService")
	public IHistoricTaskQueryService getHistoricTaskQueryService() {
		return new HistoricTaskQueryServiceImpl();
	}

	@Bean(name = "com.idp.workflow.itf.service.task.IHistoricTaskManageService")
	public IHistoricTaskManageService getHistoricTaskManageService() {
		return new HistoricTaskManageServiceImpl();
	}
}
