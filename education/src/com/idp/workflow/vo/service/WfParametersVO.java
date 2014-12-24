package com.idp.workflow.vo.service;

import java.util.HashMap;
import java.util.Map;

import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;
import com.idp.workflow.vo.pub.IWorkFlowTypes.RollBackType;
import com.idp.workflow.vo.pub.IWorkFlowTypes.SignalStatus;
import com.idp.workflow.vo.pub.SuperVO;

/**
 * 工作流入口参数
 * 
 * @author panfei
 * 
 */
public class WfParametersVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -301272465829807471L;
	private Map<String, Object> context = new HashMap<String, Object>();

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	/**
	 * 向工作传入参数
	 * 
	 * @param key
	 * @param value
	 */
	public void putVariables(String key, Object value) {
		getContext().put(key, value);
	}

	/**
	 * 传入业务vo
	 * 
	 * @param value
	 */
	public void putBillVO(Object value) {
		this.putVariables(IWorkFlowTypes.billVO, value);
	}

	/**
	 * 设置任务检查类型
	 * 
	 * @see CheckType
	 * @param value
	 */
	public void putTaskCheckType(CheckType value) {
		this.putVariables(IWorkFlowTypes.taskCheckType, value);
	}

	/**
	 * 设置回退检查类型
	 * 
	 * @see CheckType
	 * @param value
	 */
	public void putRollBackType(RollBackType value) {
		this.putVariables(IWorkFlowTypes.rollBackType, value);
	}

	/**
	 * 设置会签信息
	 * 
	 * @param message
	 */
	public void putSignature(String message) {
		this.putVariables(IWorkFlowTypes.signature, message);
	}

	/**
	 * 获取会签信息内容
	 * 
	 * @return
	 */
	public String getSignature() {
		return this.getStringValue(IWorkFlowTypes.signature);
	}

	/**
	 * 设置评论
	 * 
	 * @param message
	 */
	public void putReMark(String message) {
		this.putVariables(IWorkFlowTypes.remark, message);
	}

	/**
	 * 获取评论
	 * 
	 * @return
	 */
	public String getReMark() {
		return this.getStringValue(IWorkFlowTypes.remark);
	}

	/**
	 * 获取回滚类型
	 * 
	 * @return
	 */
	public RollBackType getRollBackType() {
		return (RollBackType) getContext().get(IWorkFlowTypes.rollBackType);
	}

	/**
	 * 获取任务检查类型
	 * 
	 * @return
	 */
	public CheckType getTaskCheckType(SignalStatus direction) {
		CheckType ret = (CheckType) getContext().get(
				IWorkFlowTypes.taskCheckType);
		if (ret == null) {
			if (SignalStatus.ForWardRunning.equals(direction)) {
				return CheckType.AutoClaim;
			} else {
				return CheckType.MySelfBack;
			}
		}
		return ret;
	}

	/**
	 * 获取字符串类型的值
	 * 
	 * @param key
	 */
	public String getStringValue(String key) {
		return StringUtil.convertString(getContext().get(key));
	}

	/**
	 * 获取当前登陆操作员
	 * 
	 * @return
	 */
	public String getOperationUserId() {
		return StringUtil.convertString(getContext().get(
				IWorkFlowTypes.operationUser));
	}
}
