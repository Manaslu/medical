package com.idp.workflow.vo.service.task;

import java.util.Date;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 评论VO
 * 
 * @author panfei
 * 
 */
public class CommentVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7704420638135086649L;

	private String id;

	private String userId;

	private String taskId;

	private String processInstanceId;

	private String type;

	private String fullMessage;

	private Date time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
