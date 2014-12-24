package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.DentityManageServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.identity.IDentityManageService;

/**
 * 身份认证查询服务构建
 * 
 * @author panfei
 * 
 */
public class DentityManageServiceBuilder
		extends
		AbstractAdapterBuilder<IDentityManageService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IDentityManageService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IDentityManageService service = targetSource.getLocater()
						.lookup(IDentityManageService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new DentityManageServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			DentityManageServiceImpl input = new DentityManageServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(IDentityManageService.class,
					input);
			return targetSource.getLocater()
					.lookup(IDentityManageService.class);
		}
	}

}
