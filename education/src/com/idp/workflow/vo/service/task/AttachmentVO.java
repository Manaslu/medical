package com.idp.workflow.vo.service.task;

import java.io.InputStream;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 附近文件信息VO
 * 
 * @author panfei
 * 
 */
public class AttachmentVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5483221042067872951L;

	private String id;

	private String attachmentName;

	private String attachmentType;

	private String attachmentDescription;

	private String taskId;

	private String processInstanceId;

	private String url;

	private InputStream content;

	public AttachmentVO(String attachmentName, String attachmentType,
			String attachmentDescription, String url) {
		this.attachmentName = attachmentName;
		this.attachmentType = attachmentType;
		this.attachmentDescription = attachmentDescription;
		this.url = url;
	}

	public AttachmentVO(String attachmentName, String attachmentType,
			String attachmentDescription, InputStream content) {
		this.attachmentName = attachmentName;
		this.attachmentType = attachmentType;
		this.attachmentDescription = attachmentDescription;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentDescription() {
		return attachmentDescription;
	}

	public void setAttachmentDescription(String attachmentDescription) {
		this.attachmentDescription = attachmentDescription;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}

}
