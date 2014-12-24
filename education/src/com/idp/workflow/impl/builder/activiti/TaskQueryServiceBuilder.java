package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.TaskQueryServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.task.ITaskQueryService;

/**
 * 任务查询服务构建
 * 
 * @author panfei
 * 
 */
public class TaskQueryServiceBuilder
		extends
		AbstractAdapterBuilder<ITaskQueryService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public ITaskQueryService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {
			if (targetSource.isServiceRegAuto()) {
				ITaskQueryService service = targetSource.getLocater().lookup(
						ITaskQueryService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new TaskQueryServiceImpl(targetSource.getWorkFlowObject());
		} else {
			TaskQueryServiceImpl input = new TaskQueryServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(ITaskQueryService.class,
					input);
			return targetSource.getLocater().lookup(ITaskQueryService.class);
		}
	}

}
