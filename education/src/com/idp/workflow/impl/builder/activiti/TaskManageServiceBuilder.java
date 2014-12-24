package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.TaskManageServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.task.ITaskManageService;

/**
 * 任务管理服务构建
 * 
 * @author panfei
 * 
 */
public class TaskManageServiceBuilder
		extends
		AbstractAdapterBuilder<ITaskManageService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public ITaskManageService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {
			if (targetSource.isServiceRegAuto()) {
				ITaskManageService service = targetSource.getLocater().lookup(
						ITaskManageService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new TaskManageServiceImpl(targetSource.getWorkFlowObject());
		} else {
			TaskManageServiceImpl input = new TaskManageServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(ITaskManageService.class,
					input);
			return targetSource.getLocater().lookup(ITaskManageService.class);
		}
	}

}
