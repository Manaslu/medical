package com.idp.workflow.event.action.activiti;

import java.util.HashMap;
import java.util.Map;

import com.idp.workflow.event.action.ActionEvent;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 历史记录触发时间
 * 
 * @author panfei
 * 
 */
public class HistroicTaskActionEvent extends ActionEvent {

	private static final String voName = "histaskvo";

	private static final String taskInfoName = "taskInfo";

	public HistroicTaskActionEvent(Object source, String actionCode,
			Map<String, Object> paramObjects) {
		super(source, actionCode, paramObjects);
	}

	public HistroicTaskActionEvent(Object source, String actionCode,
			HistoricTaskVO hisTaskVO) {
		super(source, actionCode, new HashMap<String, Object>());
		this.getParamObjects().put(voName, hisTaskVO);
	}

	public HistroicTaskActionEvent(Object source, String actionCode,
			TaskInfoVO taskinfo) {
		super(source, actionCode, new HashMap<String, Object>());
		this.getParamObjects().put(taskInfoName, taskinfo);
	}

	public TaskInfoVO getTaskInfoVO() {
		return (TaskInfoVO) this.getParamObjects().get(taskInfoName);
	}

	public HistoricTaskVO getHistoricTaskVO() {
		return (HistoricTaskVO) this.getParamObjects().get(voName);
	}

	public void putHistoricTaskVO(HistoricTaskVO hisvo) {
		this.getParamObjects().put(voName, hisvo);
	}
}
