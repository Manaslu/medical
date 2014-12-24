package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.ProInstanceQueryServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;

/**
 * 流程实例查询服务构建
 * 
 * @author panfei
 * 
 */
public class ProInstanceQueryServiceBuilder
		extends
		AbstractAdapterBuilder<IProInstanceQueryService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IProInstanceQueryService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {
		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IProInstanceQueryService service = targetSource.getLocater()
						.lookup(IProInstanceQueryService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new ProInstanceQueryServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			ProInstanceQueryServiceImpl input = new ProInstanceQueryServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IProInstanceQueryService.class, input);
			return targetSource.getLocater().lookup(
					IProInstanceQueryService.class);
		}
	}

}
