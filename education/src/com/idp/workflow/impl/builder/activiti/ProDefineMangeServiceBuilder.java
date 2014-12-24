package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.ProDefineMangeServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.prodef.IProDefineMangeService;

/**
 * 流程定义管理服务构建
 * 
 * @author panfei
 * 
 */
public class ProDefineMangeServiceBuilder
		extends
		AbstractAdapterBuilder<IProDefineMangeService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IProDefineMangeService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IProDefineMangeService service = targetSource.getLocater()
						.lookup(IProDefineMangeService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new ProDefineMangeServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			ProDefineMangeServiceImpl input = new ProDefineMangeServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IProDefineMangeService.class, input);
			return targetSource.getLocater().lookup(
					IProDefineMangeService.class);
		}
	}

}
