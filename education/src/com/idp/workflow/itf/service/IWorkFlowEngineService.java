package com.idp.workflow.itf.service;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.vo.service.WfParametersVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;

/**
 * 工作流引擎服务
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowEngineService extends InvationCallBacker {

	/**
	 * 工作流实例启动
	 * 
	 * @param userId
	 *            用户id
	 * @param processDefinitionCode
	 *            工作流定义唯一编码 不能为空
	 * @param businessCode
	 *            业务编码 唯一标识 可以为空
	 * @param params
	 *            入口启动参数
	 * @return 已启动的工作流实例
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO start(String userId, String processDefinitionCode,
			String businessCode, WfParametersVO params)
			throws WfBusinessException;

	/**
	 * 工作流实例启动
	 * 
	 * @param userId
	 *            用户id
	 * @param processDefinitionId
	 *            工作流定义唯一标识 不能为空
	 * @param params
	 *            入口启动参数
	 * @return 已启动的工作流实例
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO start(String userId, String processDefinitionId,
			WfParametersVO params) throws WfBusinessException;

	/**
	 * 工作流向前驱动
	 * 
	 * @param userId
	 *            用户id
	 * @param processDefinitionCode
	 *            工作流定义唯一编码 可以为空
	 * @param businessCode
	 *            业务编码 唯一标识 不能为空
	 * @param params
	 * @throws WfBusinessException
	 */
	void signal(String userId, String processDefinitionCode,
			String businessCode, WfParametersVO params)
			throws WfBusinessException;

	/**
	 * 工作流向前驱动
	 * 
	 * @param userId
	 *            用户id
	 * @param proInstanceID
	 *            工作流实例ID
	 * @param params
	 *            传递参数
	 * @throws WfBusinessException
	 */
	void signal(String userId, String proInstanceID, WfParametersVO params)
			throws WfBusinessException;

	/**
	 * 工作流向后驱动
	 * 
	 * @param userId
	 *            用户id
	 * @param processDefinitionCode
	 *            工作流定义唯一编码 可以为空
	 * @param businessCode
	 *            业务编码 唯一标识 不能为空
	 * @param params
	 * @throws WfBusinessException
	 */
	void rollBack(String userId, String processDefinitionCode,
			String businessCode, WfParametersVO params)
			throws WfBusinessException;

	/**
	 * 工作流向后驱动
	 * 
	 * @param userId
	 *            用户id
	 * @param proInstanceID
	 *            工作流实例ID
	 * @param params
	 *            传递参数
	 * @throws WfBusinessException
	 */
	void rollBack(String userId, String proInstanceID, WfParametersVO params)
			throws WfBusinessException;

	/**
	 * 工作流终止，不可逆不能恢复，之前工作环节的操作全部回滚。只能开始一个新的流程
	 * 
	 * @param processDefinitionCode
	 *            工作流定义唯一编码 可以为空
	 * @param businessCode
	 *            业务编码 唯一标识 不能为空
	 * @throws WfBusinessException
	 */
	void termination(String userId, String processDefinitionCode,
			String businessCode, boolean isChecked) throws WfBusinessException;

	/**
	 * 工作流终止，不可逆不能恢复，之前工作环节的操作全部回滚。只能开始一个新的流程
	 * 
	 * @param proInstanceID
	 *            工作流实例ID
	 * @throws WfBusinessException
	 */
	void termination(String userId, String proInstanceID, boolean isChecked)
			throws WfBusinessException;
}
