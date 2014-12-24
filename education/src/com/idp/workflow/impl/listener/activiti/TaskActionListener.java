package com.idp.workflow.impl.listener.activiti;

import java.util.Date;

import com.idp.workflow.event.action.ActionEvent;
import com.idp.workflow.event.action.ActionEventParamsHolder;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.listener.ActionListener;
import com.idp.workflow.itf.service.identity.AuthenticationHolder;
import com.idp.workflow.itf.service.task.IHistoricTaskManageService;
import com.idp.workflow.itf.service.task.IHistoricTaskQueryService;
import com.idp.workflow.util.service.Context;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 内部业务事件处理
 * 
 * @author panfei
 * 
 */
public class TaskActionListener implements ActionListener<ActionEvent> {

	@Override
	public void handleEvent(ActionEvent event) throws WfBusinessException {

		TaskInfoVO taskinfvo = (TaskInfoVO) event.getParamObjects().get(
				IWorkFlowTypes.taskInfo);

		IHistoricTaskManageService hisservices = Context.getILocater().lookup(
				IHistoricTaskManageService.class);

		IHistoricTaskQueryService hisqueryservices = Context.getILocater()
				.lookup(IHistoricTaskQueryService.class);

		if (ActionEvent.ACTIONCODE_FORWARDBEFORE.equals(event.getActionCode())
				|| ActionEvent.ACTIONCODE_BACKWARDBEFORE.equals(event
						.getActionCode())
				|| ActionEvent.ACTIONCODE_REJECTBEFORE.equals(event
						.getActionCode())
				|| ActionEvent.ACTIONCODE_FORWARDAFTER.equals(event
						.getActionCode())
				|| ActionEvent.ACTIONCODE_BACKWARDAFTER.equals(event
						.getActionCode())
				|| ActionEvent.ACTIONCODE_REJECTAFTER.equals(event
						.getActionCode())) {
			if (!ActionEventParamsHolder.getCurrentActionEventParams().canPass(
					taskinfvo.getTaskDefinitionKey())) {
				return;
			}
		}

		if (ActionEvent.ACTIONCODE_FORWARDBEFORE.equals(event.getActionCode())
				|| ActionEvent.ACTIONCODE_BACKWARDBEFORE.equals(event
						.getActionCode())
				|| ActionEvent.ACTIONCODE_REJECTBEFORE.equals(event
						.getActionCode())) {
			hisservices.saveHistoricTaskInfo(taskinfvo);
		} else if (ActionEvent.ACTIONCODE_FORWARDAFTER.equals(event
				.getActionCode())) {
			HistoricTaskVO queryvo = hisqueryservices
					.queryUnFinishedHistoricTaskVOByCode(
							taskinfvo.getProcessInstanceId(),
							taskinfvo.getTaskDefinitionKey());
			if (queryvo != null) {
				queryvo.setEndTime(new Date());
				queryvo.setActionType(HistoricTaskVO.ACTIONTYPE_FORWARD);
				hisservices.updateHistoricTaskInfo(queryvo);
			}
		} else if (ActionEvent.ACTIONCODE_BACKWARDAFTER.equals(event
				.getActionCode())) {
			HistoricTaskVO queryvo = hisqueryservices
					.queryUnFinishedHistoricTaskVOByCode(
							taskinfvo.getProcessInstanceId(),
							taskinfvo.getTaskDefinitionKey());
			if (queryvo != null) {
				queryvo.setEndTime(new Date());
				queryvo.setActionType(HistoricTaskVO.ACTIONTYPE_BACKWARD);
				hisservices.updateHistoricTaskInfo(queryvo);
			}
		} else if (ActionEvent.ACTIONCODE_REJECTAFTER.equals(event
				.getActionCode())) {
			HistoricTaskVO queryvo = hisqueryservices
					.queryUnFinishedHistoricTaskVOByCode(
							taskinfvo.getProcessInstanceId(),
							taskinfvo.getTaskDefinitionKey());
			if (queryvo != null) {
				queryvo.setAssignerId(AuthenticationHolder
						.getCurrentAuthentiUsers().getTaskAssignerId());
				queryvo.setEndTime(new Date());
				queryvo.setActionType(HistoricTaskVO.ACTIONTYPE_REJECT);
				hisservices.updateHistoricTaskInfo(queryvo);
			}
		}
	}
}
