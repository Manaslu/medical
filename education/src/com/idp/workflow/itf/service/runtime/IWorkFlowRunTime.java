package com.idp.workflow.itf.service.runtime;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.vo.service.WfRunTimeContext;

/**
 * 工作流实际运转过程中，每个环节触发的动作 分为 向前、向后（回滚）、之前通知
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowRunTime {

	/**
	 * 向前驱动工作流
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	void goForWard(WfRunTimeContext context) throws WfBusinessException;

	/**
	 * 向后驱动工作流
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	void getBackForWard(WfRunTimeContext context) throws WfBusinessException;

	/**
	 * 通知下一个工作流环节时，会发生
	 * 
	 * @param context
	 * @throws WfBusinessException
	 */
	void beforeActivite(WfRunTimeContext context) throws WfBusinessException;
}
