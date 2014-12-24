package com.idp.workflow.impl.builder.activiti;

import org.activiti.engine.ProcessEngine;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.impl.service.activiti.HistoricTaskQueryServiceImpl;
import com.idp.workflow.itf.builder.AbstractAdapterBuilder;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;

/**
 * 历史任务查询服务构建
 * 
 * @author panfei
 * 
 */
public class HistoricTaskQueryServiceBuilder
		extends
		AbstractAdapterBuilder<IHistoricTaskQueryService, IWorkFlowMetaData<ProcessEngine>> {

	@Override
	public IHistoricTaskQueryService createAdapterService(
			IWorkFlowMetaData<ProcessEngine> targetSource)
			throws WfBusinessException {

		if (targetSource instanceof SpringActivitiMetaData) {

			if (targetSource.isServiceRegAuto()) {
				IHistoricTaskQueryService service = targetSource.getLocater()
						.lookup(IHistoricTaskQueryService.class);
				service.setProcessEngine(targetSource.getWorkFlowObject());
				return service;
			}
			return new HistoricTaskQueryServiceImpl(
					targetSource.getWorkFlowObject());
		} else {
			HistoricTaskQueryServiceImpl input = new HistoricTaskQueryServiceImpl(
					targetSource.getWorkFlowObject());
			targetSource.getLocater().servicesBind(
					IHistoricTaskQueryService.class, input);
			return targetSource.getLocater().lookup(
					IHistoricTaskQueryService.class);
		}
	}

}
