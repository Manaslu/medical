package com.idap.processmgr.special.entity;

import java.io.Serializable;

public class Flow implements Serializable{
	private static final long serialVersionUID = -2929421212719656991L;
	private Long flowId;//流程号
	private Long nodeName;//节点名称
	private Long nondeSeq;//节点顺序号
	private String requType;//需求类型
	public Long getFlowId() {
		return flowId;
	}
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	public Long getNodeName() {
		return nodeName;
	}
	public void setNodeName(Long nodeName) {
		this.nodeName = nodeName;
	}
	public Long getNondeSeq() {
		return nondeSeq;
	}
	public void setNondeSeq(Long nondeSeq) {
		this.nondeSeq = nondeSeq;
	}
	public String getRequType() {
		return requType;
	}
	public void setRequType(String requType) {
		this.requType = requType;
	}

}
