package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.WorkFlowEngineServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.IWorkFlowEngineService;

/**
 * 引擎服务构建
 * 
 * @author panfei
 * 
 */
public class EngineServiceBuilder
		extends
		AbstractAdapterBuilder<IWorkFlowEngineService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IWorkFlowEngineService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {

		if (targetSource instanceof SpringActivitiMetaData) {
			if (targetSource.isServiceRegAuto()) {
				IWorkFlowEngineService service = targetSource.getLocater()
						.lookup(IWorkFlowEngineService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new WorkFlowEngineServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			WorkFlowEngineServiceImpl input = new WorkFlowEngineServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IWorkFlowEngineService.class, input);
			return targetSource.getLocater().lookup(
					IWorkFlowEngineService.class);
		}
	}

}
