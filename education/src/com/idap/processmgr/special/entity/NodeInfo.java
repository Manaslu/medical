package com.idap.processmgr.special.entity;

import java.io.Serializable;


public class NodeInfo implements Serializable{
	private static final long serialVersionUID = -5499578096667379160L;
	private String nodeId;//节点号(任务ID)
	private String id;//实例ID
	private String nodeApproval;//节点审批信息
	private String signInfo;//会签信息
	private String remarks;//备注
	private String nodeValid;//节点生效否
	private Long nodeSeq;//节点顺序号
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeApproval() {
		return nodeApproval;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setNodeApproval(String nodeApproval) {
		this.nodeApproval = nodeApproval;
	}
	public String getSignInfo() {
		return signInfo;
	}
	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNodeValid() {
		return nodeValid;
	}
	public void setNodeValid(String nodeValid) {
		this.nodeValid = nodeValid;
	}
	public Long getNodeSeq() {
		return nodeSeq;
	}
	public void setNodeSeq(Long nodeSeq) {
		this.nodeSeq = nodeSeq;
	}
}	
