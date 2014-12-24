package com.idp.workflow.vo.service.proinst;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 工作流实例信息VO
 * 
 * @author panfei
 * 
 */
public class ProcessInstanceVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7397891276309181639L;

	/**
	 * 工作流实例ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getBusinessCode() {
		return BusinessCode;
	}

	public void setBusinessCode(String businessCode) {
		BusinessCode = businessCode;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public String getProcessDefinitionCode() {
		return processDefinitionCode;
	}

	public void setProcessDefinitionCode(String processDefinitionCode) {
		this.processDefinitionCode = processDefinitionCode;
	}

	/**
	 * 工作流定义ID
	 */
	private String processDefinitionId;

	/**
	 * 业务编码 唯一
	 */
	private String BusinessCode;

	/**
	 * 该实例是否已经结束
	 */
	private boolean isEnd;

	/**
	 * 该实例是否被挂起暂停
	 */
	private boolean isSuspended;

	/**
	 * 流程定义标识
	 */
	private String processDefinitionCode;

	public static final String ID = "id";

	public static final String BUSINESSCODE = "BusinessCode";

	public static final String PROCESSDEFINITIONID = "processDefinitionId";

	public static final String PROCESSDEFINITIONCODE = "processDefinitionCode";

	public static final String SUSPEND_STATE = "Suspend_State";
}
