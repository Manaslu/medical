package com.idp.workflow.itf.service.task;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.service.task.AttachmentVO;

/**
 * 任务管理服务
 * 
 * @author panfei
 * 
 */
public interface ITaskManageService extends InvationCallBacker {

	/**
	 * 根据任务id删除任务
	 * 
	 * @param taskId
	 *            任务id
	 */
	void deleteTaskVO(String taskId) throws WfBusinessException;

	/**
	 * 根据任务id设置委托办理人<br>
	 * 
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            委托人id 实际任务办理人
	 */
	void setAssignee(String taskId, String userId) throws WfBusinessException;

	/**
	 * 根据任务id设置委托办理人<br>
	 * 增加了该任务是否已经被其它代理人认领的检查
	 * 
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            委托人id
	 * @param ischeck
	 *            是否检查 该任务是否已经被其它代理人认领
	 */
	void setAssignee(String taskId, String userId, boolean ischeck)
			throws WfBusinessException;

	/**
	 * 转派 其它委托代理人执行该任务
	 * 
	 * @param taskId
	 *            任务id
	 * @param OwnerId
	 *            任务实际原拥有者
	 * @param DelegaterId
	 *            转发人
	 */
	void delegateTask(String taskId, String OwnerId, String DelegaterId)
			throws WfBusinessException;

	/**
	 * 加签其它候选人
	 * 
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            新增候选人
	 * @throws WfBusinessException
	 */
	void addCandidateUser(String taskId, String userId)
			throws WfBusinessException;

	/**
	 * 删除其它候选人
	 * 
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            需要删除的候选人
	 * @throws WfBusinessException
	 */
	void deleteCandidateUser(String taskId, String userId)
			throws WfBusinessException;

	/**
	 * 其它委托代理人解决完成该任务
	 * 
	 * @param taskId
	 *            任务id
	 */
	void resolveTask(String taskId) throws WfBusinessException;

	/**
	 * 根据任务id给任务添加评论
	 * 
	 * @param taskId
	 *            任务id
	 * @param message
	 *            信息摘要
	 */
	void addComment(String userId, String taskId, String message)
			throws WfBusinessException;

	/**
	 * 删除该任务的所有评论
	 * 
	 * @param taskId
	 * @throws WfBusinessException
	 */
	void removeComments(String taskId) throws WfBusinessException;

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
	 * @param taskId
	 *            任务id
	 * @param attachment
	 *            附件内容
	 * @return 附件id
	 */
	String addAttachment(String taskId, AttachmentVO attachment)
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
	 * @param taskId
	 *            任务id
	 * @param attachmentName
	 *            附件名称
	 */
	void deleteAttachment(String taskId, String attachmentName)
			throws WfBusinessException;

	/**
	 * 删除指定附件
	 * 
	 * @param attachmentId
	 *            附件id
	 */
	void deleteAttachment(String attachmentId) throws WfBusinessException;
}
