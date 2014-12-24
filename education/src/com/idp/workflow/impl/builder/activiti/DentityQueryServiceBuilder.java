package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.DentityQueryServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.identity.IDentityQueryService;

/**
 * 身份认证管理服务构建
 * 
 * @author panfei
 * 
 */
public class DentityQueryServiceBuilder
		extends
		AbstractAdapterBuilder<IDentityQueryService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IDentityQueryService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IDentityQueryService service = targetSource.getLocater()
						.lookup(IDentityQueryService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new DentityQueryServiceImpl(targetSource.getWorkFlowObject());
		} else {
			DentityQueryServiceImpl input = new DentityQueryServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(IDentityQueryService.class,
					input);
			return targetSource.getLocater().lookup(IDentityQueryService.class);
		}
	}
}
