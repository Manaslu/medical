package com.idp.workflow.impl.service.activiti;

import java.util.Collection;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricTaskInstance;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
 
/**
 * 历史任务查询实现
 * 
 * @author panfei
 * 
 */
public class HistoricTaskQueryServiceImpl extends BaseServiceImpl implements
		IHistoricTaskQueryService {

	public HistoricTaskQueryServiceImpl() {
		super();
	}

	public HistoricTaskQueryServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

	@Override
	public Collection<HistoricTaskVO> queryHistoricTaskVOs(
			String processInstanceId) throws WfBusinessException {

		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}

		StringBuilder appender = new StringBuilder();
		appender.append(" PROC_INST_ID='");
		appender.append(processInstanceId);
		appender.append("'");
		appender.append(" order by END_TIME desc,ID desc");
		return this.getBaseDaoMapper().queryVOsByWherePart(
				HistoricTaskVO.class, appender.toString());
	}

	@Override
	public Collection<HistoricTaskVO> queryHistoricTaskVOs(
			String processDefinitionCode, String businessCode)
			throws WfBusinessException {

		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例编码不能为空！");
		}

		// if (StringUtil.isEmpty(processDefinitionCode)) {
		// throw new WfBusinessException("工作流定义编码不能为空！");
		// }

		Collection<ProcessInstanceVO> prolist = this
				.getProInstanceQueryService().queryProcessInstancesByBusiCode(
						processDefinitionCode, businessCode, null);

		return queryHistoricTaskVOs(prolist
				.toArray(new ProcessInstanceVO[prolist.size()])[0].getId());
	}

	@Override
	public HistoricTaskVO queryHistoricTaskVOByCode(String processInstanceId,
			String TaskDefineCode) throws WfBusinessException {
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}

		if (StringUtil.isEmpty(TaskDefineCode)) {
			throw new WfBusinessException("工作流任务编码不能为空！");
		}

		StringBuilder appender = new StringBuilder();
		appender.append(" PROC_INST_ID='");
		appender.append(processInstanceId);
		appender.append("'");
		appender.append(" and TASK_ACTION_CODE='");
		appender.append(TaskDefineCode);
		appender.append("'");
		appender.append(" order by END_TIME desc,ID desc");
		List<HistoricTaskVO> histasklist = this.getBaseDaoMapper()
				.queryVOsByWherePart(HistoricTaskVO.class, appender.toString());
		if (histasklist != null && histasklist.size() > 0) {
			return histasklist.get(0);
		}
		return null;
	}

	@Override
	public HistoricTaskVO queryHistoricTaskVOByCode(
			String processDefinitionCode, String businessCode,
			String TaskDefineCode) throws WfBusinessException {
		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例编码不能为空！");
		}

		if (StringUtil.isEmpty(TaskDefineCode)) {
			throw new WfBusinessException("工作流任务编码不能为空！");
		}

		Collection<ProcessInstanceVO> prolist = this
				.getProInstanceQueryService().queryProcessInstancesByBusiCode(
						processDefinitionCode, businessCode, null);

		return queryHistoricTaskVOByCode(
				prolist.toArray(new ProcessInstanceVO[prolist.size()])[0]
						.getId(),
				TaskDefineCode);
	}

	@Override
	public HistoricTaskVO queryUnFinishedHistoricTaskVOByCode(
			String processInstanceId, String TaskDefineCode)
			throws WfBusinessException {
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}

		StringBuilder appender = new StringBuilder();
		appender.append(" PROC_INST_ID='");
		appender.append(processInstanceId);
		appender.append("'");
		if (!StringUtil.isEmpty(TaskDefineCode)) {
			appender.append(" and TASK_ACTION_CODE='");
			appender.append(TaskDefineCode);
			appender.append("'");
		}
		appender.append(" and END_TIME is null ");
		List<HistoricTaskVO> histasklist = this.getBaseDaoMapper()
				.queryVOsByWherePart(HistoricTaskVO.class, appender.toString());
		if (histasklist != null && histasklist.size() > 0) {
			return histasklist.get(0);
		}
		return null;
	}

	@Override
	public int queryNeedBackHistoricTaskVOCounts(String processInstanceId)
			throws WfBusinessException {

		List<HistoricTaskInstance> result = this.getEngine()
				.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished().list();

		if (result != null && result.size() > 0) {
			return result.size();
		}
		return 0;
	}

}
