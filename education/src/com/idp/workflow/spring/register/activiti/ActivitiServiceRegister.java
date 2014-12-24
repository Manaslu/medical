package com.idp.workflow.spring.register.activiti;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.idp.workflow.exception.pub.ProcessNotFoundException;
import com.idp.workflow.exception.pub.WfBusinessException;
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
import com.idp.workflow.util.spring.DynamicServiceRegister;
import com.idp.workflow.util.spring.ServiceBeanFactory;

/**
 * 支持activiti的工作流服务注册
 * 
 * @author panfei
 * 
 */
public class ActivitiServiceRegister extends DynamicServiceRegister {

	@Override
	public void registerServiceBeansDefine(
			DefaultListableBeanFactory beanfactory)
			throws ProcessNotFoundException {
		// 设置工厂bean
		ServiceBeanFactory.GetInstance().clearSpringContext();
		ServiceBeanFactory.GetInstance().setBeanfactory(beanfactory);
		try {
			// 注册工作流引擎服务
			ServiceBeanFactory.GetInstance().servicesBind(
					IWorkFlowEngineService.class,
					WorkFlowEngineServiceImpl.class);
			// 注册工作流定义管理服务
			ServiceBeanFactory.GetInstance().servicesBind(
					IProDefineMangeService.class,
					ProDefineMangeServiceImpl.class);
			// 注册工作流定义查询服务
			ServiceBeanFactory.GetInstance().servicesBind(
					IProDefineQueryService.class,
					ProDefineQueryServiceImpl.class);
			// 注册工作流实例管理服务
			ServiceBeanFactory.GetInstance().servicesBind(
					IProInstanceMangeService.class,
					ProInstanceMangeServiceImpl.class);
			// 注册工作流实例查询服务
			ServiceBeanFactory.GetInstance().servicesBind(
					IProInstanceQueryService.class,
					ProInstanceQueryServiceImpl.class);
			// 注册工作流任务查询服务
			ServiceBeanFactory.GetInstance().servicesBind(
					ITaskQueryService.class, TaskQueryServiceImpl.class);
			// 注册工作流任务管理服务
			ServiceBeanFactory.GetInstance().servicesBind(
					ITaskManageService.class, TaskManageServiceImpl.class);
			// 注册身份认真管理服务
			ServiceBeanFactory.GetInstance()
					.servicesBind(IDentityManageService.class,
							DentityManageServiceImpl.class);
			// 注册身份查询服务
			ServiceBeanFactory.GetInstance().servicesBind(
					IDentityQueryService.class, DentityQueryServiceImpl.class);
			// 历史任务管理
			ServiceBeanFactory.GetInstance().servicesBind(
					IHistoricTaskManageService.class,
					HistoricTaskManageServiceImpl.class);
			// 历史任务查询管理
			ServiceBeanFactory.GetInstance().servicesBind(
					IHistoricTaskQueryService.class,
					HistoricTaskQueryServiceImpl.class);
		} catch (WfBusinessException e) {
			throw new ProcessNotFoundException(e);
		}
	}

}
