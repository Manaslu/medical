package com.idp.workflow.itf.service.proinst;

import java.util.Collection;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.pub.OrderCondition;
import com.idp.workflow.vo.pub.WhereCondition;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.AttachmentVO;
import com.idp.workflow.vo.service.task.CommentVO;

/**
 * 工作流实例查询服务
 * 
 * @author panfei
 * 
 */
public interface IProInstanceQueryService extends InvationCallBacker {

	/**
	 * 根据工作流实例id查询出对应的工作流实例vo
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessInstanceVO queryProcessInstanceById(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 批量查询工作流实例集合
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> queryProcessInstancesById(
			OrderCondition orderCondition, String... processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据业务编码查询出工作流实例集合
	 * 
	 * @param businessCode
	 *            业务编码
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> queryProcessInstancesByBusiCode(
			String businessCode, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据业务编码、工作流定义id 查询出工作流实例集合
	 * 
	 * @param businessCode
	 *            业务编码
	 * @param processDefinitionCode
	 *            工作流定义编码
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> queryProcessInstancesByBusiCode(
			String processDefinitionCode, String businessCode,
			OrderCondition orderCondition) throws WfBusinessException;

	/**
	 * 根据 业务编码、工作流定义id等条件查询出 工作流实例集合
	 * 
	 * @param businessCode
	 *            业务编码
	 * @param processDefinitionCode
	 *            工作流定义编码
	 * @param whereCondition
	 *            查询条件
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> queryProcessInstancesByBusiCode(
			String processDefinitionCode, String businessCode,
			WhereCondition whereCondition, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据 工作流定义id和排序条件 查询出 工作流定义集合
	 * 
	 * @param processDefinitionId
	 *            工作流定义id
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> queryProcessInstancesByDefId(
			String processDefinitionId, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据工作流实例id和排序条件查询出 父工作流实例集合
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> querysuperProcessInstanceById(
			String processInstanceId, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据工作流实例id和排序条件查询出 子工作流实例集合
	 * 
	 * @param processInstanceId
	 *            工作流实例
	 * @param orderCondition
	 *            排序条件
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessInstanceVO> querysubProcessInstanceById(
			String processInstanceId, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 查询该任务所有的评论
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<CommentVO> queryProcessInstanceCommentVOsById(
			String processInstanceId) throws WfBusinessException;

	/**
	 * 查询该工作流实例下挂的符合名称附件信息
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param attachmentName
	 *            附件名称
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<AttachmentVO> queryProcessInstanceAttachmentVOsByAtchName(
			String processInstanceId, String attachmentName)
			throws WfBusinessException;

	/**
	 * 根据工作流实例id查询附件VO
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<AttachmentVO> queryProcessInstanceAttachmentVOsById(
			String processInstanceId) throws WfBusinessException;

	/**
	 * 根据附件id查询附件VO
	 * 
	 * @param attachmentId
	 *            附近id
	 * @return
	 * @throws WfBusinessException
	 */
	AttachmentVO queryProcessInstanceAttachmentVOsByAtchId(String attachmentId)
			throws WfBusinessException;
}
