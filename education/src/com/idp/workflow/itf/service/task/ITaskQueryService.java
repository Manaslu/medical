package com.idp.workflow.itf.service.task;

import java.util.Collection;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.pub.OrderCondition;
import com.idp.workflow.vo.pub.WhereCondition;
import com.idp.workflow.vo.service.task.AttachmentVO;
import com.idp.workflow.vo.service.task.CommentVO;
import com.idp.workflow.vo.service.task.TaskDefineVO;
import com.idp.workflow.vo.service.task.TaskIMageVO;
import com.idp.workflow.vo.service.task.TaskVO;

/**
 * 工作待办事务任务查询服务
 * 
 * @author panfei
 * 
 */
public interface ITaskQueryService extends InvationCallBacker {

	/**
	 * 根据任务id查询指定任务
	 * 
	 * @param taskId
	 * @return
	 */
	TaskVO queryTaskVOById(String taskId) throws WfBusinessException;

	/**
	 * 根据任务名称查询指定任务
	 * 
	 * @param taskName
	 * @return
	 */
	Collection<TaskVO> queryTaskVOByName(String taskName)
			throws WfBusinessException;

	/**
	 * 根据角色id查询待办任务
	 * 
	 * @param roleid
	 *            角色id 必输
	 * @param processInstanceId
	 *            工作流实例id 非必输
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskVO> queryTaskVOByUserRole(String roleid,
			String processInstanceId) throws WfBusinessException;

	/**
	 * 根据角色id查询待办任务
	 * 
	 * @param roleid
	 *            角色id 必输
	 * @param processDefinitionCode
	 *            工作流定义编码 非必输
	 * @param businessCode
	 *            自定义业务流水号 非必输
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskVO> queryTaskVOByUserRole(String roleid,
			String processDefinitionCode, String businessCode)
			throws WfBusinessException;

	/**
	 * 根据用户id和工作流程实例id查询已经指派的任务
	 * 
	 * @param userId
	 *            用户id，不能为空
	 * @param TaskDefineCode
	 *            任务定义编码，标识一个任务环节，可以为空
	 * @param processInstanceId
	 *            可以为空，默认查询所有当前存在流程实例并指派给此用户的任务
	 * @return
	 */
	Collection<TaskVO> queryTakeAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据用户id和工作流程定义编码、工作流实例编码查询已经指派的任务
	 * 
	 * @param userId
	 *            用户id，不能为空
	 * @param TaskDefineCode
	 *            任务定义编码，标识一个任务环节，可以为空
	 * @param processDefinitionCode
	 *            工作流定义编码 可以为空
	 * @param businessCode
	 *            工作流实例编码 可以为空
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskVO> queryTakeAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 根据用户id和工作流程实例id查询可以分配指派给该用户的任务<br>
	 * 适用于抢占式的任务，比如：用户组和角色
	 * 
	 * @param userId
	 *            用户id，不能为空
	 * @param TaskDefineCode
	 *            任务定义编码，标识一个任务环节，可以为空
	 * @param processInstanceId
	 *            可以为空，默认查询所有当前存在流程实例并指派给此用户的任务
	 * @return
	 */
	Collection<TaskVO> queryCanAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据用户id和工作流定义编码、工作流程实例编码查询可以分配指派给该用户的任务<br>
	 * 适用于抢占式的任务，比如：用户组和角色
	 * 
	 * @param userId
	 *            用户id，不能为空
	 * @param TaskDefineCode
	 *            任务定义编码，标识一个任务环节，可以为空
	 * @param processDefinitionCode
	 *            工作流程定义编码 可以为空
	 * @param businessCode
	 *            工作流实例业务编码 可以为空
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskVO> queryCanAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 根据用户id和工作流程实例id查询所有指派给该用户和可以指派给该用户的任务<br>
	 * 等价于 queryCanAssignedTasksByUserId 和 queryTakeAssignedTasksByUserId
	 * 
	 * @param userId
	 *            用户id，不能为空
	 * @param TaskDefineCode
	 *            任务定义编码，标识一个任务环节，可以为空
	 * @param processInstanceId
	 *            可以为空，默认查询所有当前存在流程实例并指派给此用户的任务
	 * @return
	 */
	Collection<TaskVO> queryToDoTasksByUserId(String userId,
			String TaskDefineCode, String processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据用户id和工作流程定义编码查询所有指派给该用户和可以指派给该用户的任务<br>
	 * 
	 * @param userId
	 *            用户id，不能为空
	 * @param TaskDefineCode
	 *            任务定义编码，标识一个任务环节，可以为空
	 * @param processDefinitionCode
	 *            可以为空，默认查询所有当前存在流程实例并指派给此用户的任务
	 * @param businessCode
	 *            可以为空，工作流实例的业务编码
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskVO> queryToDoTasksByUserId(String userId,
			String TaskDefineCode, String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 高级条件查询
	 * 
	 * @param userId
	 * @param whereCondition
	 * @param orderCondition
	 * @return
	 */
	Collection<TaskVO> queryTasksByCondtions(String userId,
			WhereCondition whereCondition, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据工作流实例id查询当前的任务信息 考虑以后可能支持并行分支任务所以返回集合，而不是一个
	 * 
	 * @param processInstanceId
	 *            不能为空
	 * @return
	 */
	Collection<TaskIMageVO> queryCurrentTaskVO(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 根据工作流程定义编码和工作流实例业务编码查询当前的任务信息
	 * 
	 * @param processDefinitionCode
	 *            可以为空
	 * @param businessCode
	 *            不能为空
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskIMageVO> queryCurrentTaskVO(String processDefinitionCode,
			String businessCode) throws WfBusinessException;

	/**
	 * 根据流程定义id查询所有的任务定义信息vo
	 * 
	 * @param processDefinitionId
	 *            流程定义id
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskDefineVO> queryTaskDefineVOs(String processDefinitionId)
			throws WfBusinessException;

	/**
	 * 根据流程定义编码获取目前最新的版本流程定义，查询所有的任务定义信息vo
	 * 
	 * @param processDefinitionCode
	 *            流程定义编码
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<TaskDefineVO> queryTaskDefineVOsByLastProDefineVersion(
			String processDefinitionCode) throws WfBusinessException;

	/**
	 * 根据工作流id和任务编码，查询任务vo
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param TaskDefineCode
	 *            任务编码
	 * @return
	 * @throws WfBusinessException
	 */
	TaskVO queryTaskVOByTaskDefineCode(String processInstanceId,
			String TaskDefineCode) throws WfBusinessException;

	/**
	 * 根据工作流定义编码、工作流实例编码和任务编码查询任务VO
	 * 
	 * @param processDefinitionCode
	 *            可为空
	 * @param businessCode
	 *            不能为空
	 * @param TaskDefineCode
	 *            不能为空
	 * @return
	 * @throws WfBusinessException
	 */
	TaskVO queryTaskVOByTaskDefineCode(String processDefinitionCode,
			String businessCode, String TaskDefineCode)
			throws WfBusinessException;

	/**
	 * 查询该任务所有的评论
	 * 
	 * @param taskId
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<CommentVO> queryTaskCommentVOsById(String taskId)
			throws WfBusinessException;

	/**
	 * 查询该任务下挂的符合名称附件信息
	 * 
	 * @param taskId
	 *            任务id
	 * @param attachmentName
	 *            附件名称
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<AttachmentVO> queryTaskAttachmentVOsByAtchName(String taskId,
			String attachmentName) throws WfBusinessException;

	/**
	 * 根据任务id查询附件VO
	 * 
	 * @param taskId
	 *            任务id
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<AttachmentVO> queryTaskAttachmentVOsById(String taskId)
			throws WfBusinessException;

	/**
	 * 根据附件id查询附件VO
	 * 
	 * @param attachmentId
	 *            附近id
	 * @return
	 * @throws WfBusinessException
	 */
	AttachmentVO queryTaskAttachmentVOsByAtchId(String attachmentId)
			throws WfBusinessException;

}
