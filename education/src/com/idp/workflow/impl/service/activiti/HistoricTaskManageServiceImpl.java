package com.idp.workflow.impl.service.activiti;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.springframework.transaction.annotation.Transactional;

import com.idp.workflow.event.action.ActionEventParamsHolder;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 
 * 工作历史任务管理实现
 * 
 * @author panfei
 * 
 */
@Transactional
public class HistoricTaskManageServiceImpl extends BaseServiceImpl implements
		IHistoricTaskManageService {

	public HistoricTaskManageServiceImpl() {
		super();
	}

	public HistoricTaskManageServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

	@Override
	public void saveHistoricTaskInfo(HistoricTaskVO hisvo)
			throws WfBusinessException {
		this.getBaseDaoMapper().insertVO(hisvo);
	}

	@Override
	public void updateHistoricTaskInfo(HistoricTaskVO hisvo)
			throws WfBusinessException {
		// hisvo.setEndTime(new Date());
		this.getBaseDaoMapper().updateVO(hisvo);
	}

	@Override
	public void deleteHistoricTaskInfo(HistoricTaskVO hisvo)
			throws WfBusinessException {
		this.getBaseDaoMapper().deleteVO(HistoricTaskVO.class, hisvo.getId());
	}

	@Override
	public HistoricTaskVO createHistoricTaskVO(String processInstanceId,
			String taskDefinitionCode, String actid) throws WfBusinessException {
		HistoricTaskVO histaskvo = null;
		HistoricActivityInstance convertvo = null;

		List<HistoricActivityInstance> reslist = this.getEngine()
				.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.activityId(taskDefinitionCode).list();

		if (reslist != null && reslist.size() > 0) {
			if (actid == null) {
				convertvo = reslist.get(0);
			} else {
				for (HistoricActivityInstance temp : reslist) {
					if (actid.equals(temp.getId())) {
						convertvo = temp;
						break;
					}
				}
			}
		}
		// 自动任务环节，需要从缓存里面获取
		if (convertvo == null) {
			List<HistoricActivityInstanceEntity> rethistasklist = Context
					.getCommandContext().getDbSqlSession()
					.findInCache(HistoricActivityInstanceEntity.class);
			if (rethistasklist != null) {
				for (HistoricActivityInstanceEntity temp : rethistasklist) {
					if (taskDefinitionCode.equals(temp.getActivityId())) {
						convertvo = temp;
						break;
					}
				}
			}
		}

		if (convertvo == null) {
			return null;
		}

		histaskvo = VOUtil.convertHisTaskVO(convertvo);
		ProcessEngineImpl procengine = (ProcessEngineImpl) this.getEngine();
		histaskvo.setId(procengine.getProcessEngineConfiguration()
				.getIdGenerator().getNextId());
		return histaskvo;
	}

	@Override
	public HistoricTaskVO saveHistoricTaskInfo(TaskInfoVO taskvo)
			throws WfBusinessException {
		if (taskvo != null) {
			HistoricTaskVO histaskvo = null;
			String act_id = taskvo.getId();
			histaskvo = this.createHistoricTaskVO(
					taskvo.getProcessInstanceId(),
					taskvo.getTaskDefinitionKey(), act_id);
			if (histaskvo != null) {
				histaskvo.setSignature(taskvo.getSignature());
				histaskvo.setStartTime(new Date());
				histaskvo.setRemark(taskvo.getReMark());
				histaskvo.setPresentStageTaskCode(ActionEventParamsHolder
						.getCurrentActionEventParams().getTaskStageCode());
				histaskvo.setPresentStageTaskName(ActionEventParamsHolder
						.getCurrentActionEventParams().getTaskStageName());
				taskvo.setId(histaskvo.getId());
				this.saveHistoricTaskInfo(histaskvo);
				return histaskvo;
			}
		}
		return null;
	}
}
