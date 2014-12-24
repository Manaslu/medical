package com.idp.workflow.event.action;

import com.idp.workflow.vo.pub.IWorkFlowTypes;

/**
 * 时间动作参数
 * 
 * @author panfei
 * 
 */
public class ActionEventParams {

	private String taskStageCode;

	private String taskStageName;

	private String passTaskCodeName = IWorkFlowTypes.passDefualt;

	public boolean canPass(String taskName) {

		if (IWorkFlowTypes.passDefualt.equals(passTaskCodeName)) {
			return true;
		} else if (passTaskCodeName.equals(taskName)) {
			return true;
		} else {
			return false;
		}
	}

	public void setPassTaskCodeName(String passTaskCodeName) {
		this.passTaskCodeName = passTaskCodeName;
	}

	public String getTaskStageName() {
		return taskStageName;
	}

	public void setTaskStageName(String taskStageName) {
		this.taskStageName = taskStageName;
	}

	public String getTaskStageCode() {
		return taskStageCode;
	}

	public void setTaskStageCode(String taskStageCode) {
		this.taskStageCode = taskStageCode;
	}

}
