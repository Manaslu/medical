package com.idap.processmgr.special.entity;

import java.io.Serializable;
import java.util.Date;

public class RequTrack implements Serializable{
	private static final long serialVersionUID = 3452456824447094947L;
	private String nodeId;//节点号
	private String id;//实例ID
	private String opPer;//操作人
	private Date opTime = new Date();//操作时间
	private String remarks;//备注
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpPer() {
		return opPer;
	}
	public void setOpPer(String opPer) {
		this.opPer = opPer;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
