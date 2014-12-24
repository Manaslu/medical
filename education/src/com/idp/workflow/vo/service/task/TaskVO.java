package com.idp.workflow.vo.service.task;

import java.util.Date;
import java.util.List;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 待办任务实例
 * 
 * @author panfei
 * 
 */
public class TaskVO extends SuperVO {

	/**
	 *  
	 */
	private static final long serialVersionUID = 2746931744523689983L;

	private String id;

	private String name;

	private String ownerId;

	private String assignerId;

	private Date createTime;

	private Date dueDate;

	private String taskDefinitionCode;

	private String processInstanceId;

	private String processDefinitionId;

	// private Map<String, String[]> variableParams;

	private List<ConfigParamsVO> configParams;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/*
	 * public Map<String, String[]> getVariableParams() { return variableParams;
	 * }
	 * 
	 * public void setVariableParams(Map<String, String[]> variableParams) {
	 * this.variableParams = variableParams; }
	 */

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

	public List<ConfigParamsVO> getConfigParams() {
		return configParams;
	}

	public void setConfigParams(List<ConfigParamsVO> configParams) {
		this.configParams = configParams;
	}
}
