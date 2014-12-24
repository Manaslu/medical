package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.ProInstanceMangeServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;

/**
 * 工作流程实例管理构建
 * 
 * @author panfei
 * 
 */
public class ProInstanceManageServiceBuilder
		extends
		AbstractAdapterBuilder<IProInstanceMangeService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IProInstanceMangeService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IProInstanceMangeService service = targetSource.getLocater()
						.lookup(IProInstanceMangeService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new ProInstanceMangeServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			ProInstanceMangeServiceImpl input = new ProInstanceMangeServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IProInstanceMangeService.class, input);
			return targetSource.getLocater().lookup(
					IProInstanceMangeService.class);
		}
	}

}
