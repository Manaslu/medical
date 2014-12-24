package com.idp.workflow.vo.service.task;

import java.util.List;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 任务定义vo
 * 
 * @author panfei
 * 
 */
public class TaskDefineVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1121822182549846296L;

	private String id;

	private String name;

	private String taskDefinitionCode;

	private String processDefinitionId;

	// private Map<String, String[]> configParams;

	private List<ConfigParamsVO> configParams;

	private int x = -1;
	private int y = -1;
	private int width = -1;
	private int height = -1;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

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

	public String getTaskDefinitionCode() {
		return taskDefinitionCode;
	}

	public void setTaskDefinitionCode(String taskDefinitionCode) {
		this.taskDefinitionCode = taskDefinitionCode;
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

	/*
	 * public Map<String, String[]> getConfigParams() { return configParams; }
	 * 
	 * public void setConfigParams(Map<String, String[]> configParams) {
	 * this.configParams = configParams; }
	 */
}
