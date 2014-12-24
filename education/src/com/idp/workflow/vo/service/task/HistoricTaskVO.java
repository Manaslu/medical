package com.idp.workflow.vo.service.task;

import java.util.Date;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 历史任务记录VO
 * 
 * @author panfei
 * 
 */
public class HistoricTaskVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 322610368554477540L;

	/**
	 * 执行
	 */
	public final static String ACTIONTYPE_FORWARD = "goforward";

	/**
	 * 取消
	 */
	public final static String ACTIONTYPE_BACKWARD = "backforward";

	/**
	 * 驳回
	 */
	public final static String ACTIONTYPE_REJECT = "reject";

	private String id;

	private String name;

	private String ownerId;

	private String assignerId;

	private Date startTime;

	private Date endTime;

	private String taskDefinitionCode;

	private String actionType;

	private String processInstanceId;

	private String processDefinitionId;

	private String parentTaskId;

	private String remark;

	private String signature;

	private String deleteReason;

	private String description;

	private Integer priority;

	private String executionId;

	private String presentStageTaskCode;

	private String presentStageTaskName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(String assignerId) {
		this.assignerId = assignerId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTaskDefinitionCode() {
		return taskDefinitionCode;
	}

	public void setTaskDefinitionCode(String taskDefinitionCode) {
		this.taskDefinitionCode = taskDefinitionCode;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPresentStageTaskCode() {
		return presentStageTaskCode;
	}

	public void setPresentStageTaskCode(String presentStageTaskCode) {
		this.presentStageTaskCode = presentStageTaskCode;
	}

	public String getPresentStageTaskName() {
		return presentStageTaskName;
	}

	public void setPresentStageTaskName(String presentStageTaskName) {
		this.presentStageTaskName = presentStageTaskName;
	}

}
