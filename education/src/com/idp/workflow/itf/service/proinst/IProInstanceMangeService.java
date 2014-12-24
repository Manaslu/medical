package com.idp.workflow.itf.service.proinst;

import java.util.Map;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.service.WfParametersVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.AttachmentVO;

/**
 * 工作流实例管理服务接口
 * 
 * @author panfei
 * 
 */
public interface IProInstanceMangeService extends InvationCallBacker {

	/**
	 * 根据工作流定义启动一个工作流实例
	 * 
	 * @param processdefineCode
	 *            工作流程定义编码 不能为空
	 * @param businessCode
	 *            业务编码 可以为空
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO startProcessInstanceByCode(String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 根据工作流定义启动一个工作流实例
	 * 
	 * @param processDefinitionId
	 *            工作流定义id
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO startProcessInstanceById(String processDefinitionId)
			throws WfBusinessException;

	/**
	 * 根据工作流定义启动一个工作流实例
	 * 
	 * @param processDefinitionId
	 *            工作流定义id
	 * @param variables
	 *            启动参数
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO startProcessInstanceById(String processDefinitionId,
			WfParametersVO variables) throws WfBusinessException;

	/**
	 * 根据工作流定义启动一个工作流实例
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 不能为空
	 * @param businessCode
	 *            业务编码 可以为空
	 * @param variables
	 *            启动参数
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO startProcessInstanceByCode(String processDefinitionCode,
			String businessCode, WfParametersVO variables)
			throws WfBusinessException;

	/**
	 * 根据工作流定义编码、工作流实例业务编码 删除一个工作流实例
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 可以为空
	 * @param businessCode
	 *            业务编码 不能为空
	 * @param deleteReason
	 *            删除理由
	 * @throws WfBusinessException
	 */
	void deleteProcessInstance(String processDefinitionCode,
			String businessCode, String deleteReason)
			throws WfBusinessException;

	/**
	 * 根据工作流实例id删除一个工作流实例
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param deleteReason
	 *            删除理由
	 * @throws WfBusinessException
	 */
	void deleteProcessInstance(String processInstanceId, String deleteReason)
			throws WfBusinessException;

	/**
	 * 根据工作流定义编码和工作流实例业务编码 挂起／暂停使用一个工作流实例
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 可以为空
	 * @param businessCode
	 *            业务编码 不能为空
	 * @throws WfBusinessException
	 */
	void suspendProcessInstance(String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 根据工作流实例id挂起／暂停使用一个工作流实例
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @throws WfBusinessException
	 */
	void suspendProcessInstance(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据工作流定义编码和工作流实例业务编码 激活启用一个工作流实例
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 可以为空
	 * @param businessCode
	 *            业务编码 不能为空
	 * @throws WfBusinessException
	 */
	void activateProcessInstance(String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 根据工作流实例id激活启用一个工作流实例
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @throws WfBusinessException
	 */
	void activateProcessInstance(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 保存全流程全局自定义参数
	 * 
	 * @param processInstanceId
	 *            流程id
	 * @param variables
	 *            参数值
	 * @throws WfBusinessException
	 */
	void saveGlobalVariables(String processInstanceId,
			Map<String, Object> variables) throws WfBusinessException;

	/**
	 * 获取全局自定义参数
	 * 
	 * @param processInstanceId
	 *            流程id
	 * @return
	 * @throws WfBusinessException
	 */
	Map<String, Object> getGlobalVariables(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 删除全局参数
	 * 
	 * @param processInstanceId
	 *            流程id
	 * @throws WfBusinessException
	 */
	void deleteGlobalVariables(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据流程实例id给任务添加评论
	 * 
	 * @param processInstanceId
	 *            任务id
	 * @param message
	 *            信息摘要
	 */
	void addComment(String userId, String processInstanceId, String message)
			throws WfBusinessException;

	/**
	 * 删除该任务的所有评论
	 * 
	 * @param processInstanceId
	 * @throws WfBusinessException
	 */
	void removeComments(String processInstanceId) throws WfBusinessException;

	/**
	 * 删除指定的评论
	 * 
	 * @param commentId
	 * @throws WfBusinessException
	 */
	void removeComment(String commentId) throws WfBusinessException;

	/**
	 * 增加该任务附件上传
	 * 
	 * @param processInstanceId
	 *            工作实例id
	 * @param attachment
	 *            附件内容
	 * @return 附件id
	 */
	String addAttachment(String processInstanceId, AttachmentVO attachment)
			throws WfBusinessException;

	/**
	 * 修改附件内容
	 * 
	 * 
	 * @param attachment
	 *            附件内容
	 * @return
	 * @throws WfBusinessException
	 */
	void changeAttachment(String attachmentId, String attachmentName,
			String attachmentDescription) throws WfBusinessException;

	/**
	 * 删除指定附件
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param attachmentName
	 *            附件名称
	 */
	void deleteAttachment(String processInstanceId, String attachmentName)
			throws WfBusinessException;

	/**
	 * 删除指定附件
	 * 
	 * @param attachmentId
	 *            附件id
	 */
	void deleteAttachment(String attachmentId) throws WfBusinessException;
}
