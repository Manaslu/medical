package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.HistoricTaskManageServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;

/**
 * 历史任务管理服务构建
 * 
 * @author panfei
 * 
 */
public class HistoricTaskManageServiceBuilder
		extends
		AbstractAdapterBuilder<IHistoricTaskManageService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IHistoricTaskManageService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {

		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IHistoricTaskManageService service = targetSource.getLocater()
						.lookup(IHistoricTaskManageService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new HistoricTaskManageServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			HistoricTaskManageServiceImpl input = new HistoricTaskManageServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IHistoricTaskManageService.class, input);
			return targetSource.getLocater().lookup(
					IHistoricTaskManageService.class);
		}
	}

}
