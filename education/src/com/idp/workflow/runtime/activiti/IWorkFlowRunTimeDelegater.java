package com.idp.workflow.runtime.activiti;

import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 工作流活动的执行单元创建者
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowRunTimeDelegater {

	/**
	 * 根据活动节点的向前执行
	 * 
	 * @param actimpl
	 * @param proInstanceID
	 * @param params
	 * @throws WfBusinessException
	 */
	void goForWard(ActivityImpl actimpl, String proInstanceID,
			Map<String, Object> params) throws WfBusinessException;

	/**
	 * 根据活动节点的向后执行
	 * 
	 * @param actimpl
	 * @param proInstanceID
	 * @param params
	 * @throws WfBusinessException
	 */
	void backForWard(ActivityImpl actimpl, String proInstanceID,
			Map<String, Object> params) throws WfBusinessException;

}
