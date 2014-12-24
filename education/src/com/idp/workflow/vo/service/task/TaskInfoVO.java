package com.idp.workflow.vo.service.task;

import java.util.List;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 任务信息，在工作流上下文中传递
 * 
 * @author panfei
 * 
 */
public class TaskInfoVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1665870446578722334L;

	private String id;

	private String Name;

	private String taskDefinitionKey;

	private String processInstanceId;

	private boolean autoRun = false;

	private boolean ProcessEnd = false;
	// private Map<String, String[]> configParams;

	private List<ConfigParamsVO> configParams;

	private String assignee;

	private String candidateGroup;

	private String reMark;

	private String signature;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public boolean isAutoRun() {
		return autoRun;
	}

	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}

	public List<ConfigParamsVO> getConfigParams() {
		return configParams;
	}

	public void setConfigParams(List<ConfigParamsVO> configParams) {
		this.configParams = configParams;
	}

	/*
	 * public Map<String, String[]> getConfigParams() { return configParams; }
	 * 
	 * public void setConfigParams(Map<String, String[]> configParams) {
	 * this.configParams = configParams; }
	 */

	public boolean isProcessEnd() {
		return ProcessEnd;
	}

	public void setProcessEnd(boolean processEnd) {
		ProcessEnd = processEnd;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCandidateGroup() {
		return candidateGroup;
	}

	public void setCandidateGroup(String candidateGroup) {
		this.candidateGroup = candidateGroup;
	}

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
