package com.idp.workflow.vo.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.idp.workflow.itf.service.identity.IDentityChecker;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.IWorkFlowTypes.SignalStatus;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 工作流上下文
 * 
 * @author panfei
 * 
 */
public class WfRunTimeContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2109425332096917227L;

	private Map<String, Object> context = null;

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		setContext(context, false);
	}

	public void setContext(Map<String, Object> context, boolean copy) {
		if (copy) {
			this.context = new HashMap<String, Object>();
			if (context != null) {
				for (String key : context.keySet()) {
					this.context.put(key, context.get(key));
				}
			}
		} else {
			this.context = context;
		}
	}

	/**
	 * 获取当前用户id
	 * 
	 * @return
	 */
	public String getOperationUserId() {
		return String.valueOf(this.getContext().get(
				IWorkFlowTypes.operationUser));
	}

	/**
	 * 获取工作流状态
	 * 
	 * @return
	 */
	public SignalStatus getSignalStatus() {
		if (this.getContext().get(IWorkFlowTypes.signalStatus) == null) {
			return IWorkFlowTypes.SignalStatus.ForWardRunning;
		}
		return (SignalStatus) this.getContext()
				.get(IWorkFlowTypes.signalStatus);
	}

	/**
	 * 获取业务表单VO
	 * 
	 * @return
	 */
	public Object getBillVO() {
		return this.getContext().get(IWorkFlowTypes.billVO);
	}

	/**
	 * 传入表单业务vo
	 * 
	 * @param billvo
	 */
	public void putBillVO(Object billvo) {
		this.putVariables(IWorkFlowTypes.billVO, billvo);
	}

	/**
	 * 向工作传入参数，作用域为全局共享
	 * 
	 * @param key
	 * @param value
	 */
	public void putVariables(String key, Object value) {
		getContext().put(key, value);
	}

	/**
	 * 获取工作传入参数
	 * 
	 * @param key
	 */
	public Object getVariables(String key) {
		return getContext().get(key);
	}

	/**
	 * 删除参数
	 * 
	 * @param key
	 */
	public void removeVariables(String key) {
		getContext().remove(key);
	}

	/**
	 * 获取任务信息
	 * 
	 * @return
	 */
	public TaskInfoVO getTaskInfoVO() {
		Object taskvo = this.getContext().get(IWorkFlowTypes.taskInfo);
		if (taskvo != null) {
			return (TaskInfoVO) taskvo;
		}
		return null;
	}

	/**
	 * 任务权限设置服务
	 * 
	 * @return
	 */
	public IDentityChecker getDentityChecker() {
		Object identitychecker = this.getContext().get(
				IWorkFlowTypes.identityChecker);
		if (identitychecker != null) {
			return (IDentityChecker) identitychecker;
		}
		return null;
	}

	/**
	 * 获取评论
	 * 
	 * @return
	 */
	public String getReMark() {
		return StringUtil.convertString(this
				.getVariables(IWorkFlowTypes.remark));
	}

	/**
	 * 获取会签信息
	 * 
	 * @return
	 */
	public String getSignature() {
		return StringUtil.convertString(this
				.getVariables(IWorkFlowTypes.signature));

	}
}
