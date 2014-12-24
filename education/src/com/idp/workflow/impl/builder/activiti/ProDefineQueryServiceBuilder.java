package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.ProDefineQueryServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;

/**
 * 工作流程定义查询服务构建
 * 
 * @author panfei
 * 
 */
public class ProDefineQueryServiceBuilder
		extends
		AbstractAdapterBuilder<IProDefineQueryService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IProDefineQueryService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {

		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IProDefineQueryService service = targetSource.getLocater()
						.lookup(IProDefineQueryService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new ProDefineQueryServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			ProDefineQueryServiceImpl input = new ProDefineQueryServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IProDefineQueryService.class, input);
			return targetSource.getLocater().lookup(
					IProDefineQueryService.class);
		}
	}

}
