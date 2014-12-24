package com.idp.workflow.impl.service.activiti;

import java.util.Collection;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.AttachmentEntity;
import org.activiti.engine.task.Attachment;
import org.springframework.transaction.annotation.Transactional;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.AuthenticationHolder;
import com.idp.workflow.itf.service.task.ITaskManageService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;
import com.idp.workflow.vo.service.task.AttachmentVO;

/**
 * 任务管理服务实现
 * 
 * @author panfei
 * 
 */
@Transactional
public class TaskManageServiceImpl extends BaseServiceImpl implements
		ITaskManageService {

	public TaskManageServiceImpl() {
		super();
	}

	public TaskManageServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public void deleteTaskVO(String taskId) throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("待办任务id不能为空！");
		}
		this.getEngine().getTaskService().deleteTask(taskId, true);
	}

	@Override
	public void setAssignee(String taskId, String userId)
			throws WfBusinessException {
		this.setAssignee(taskId, userId, true);
	}

	@Override
	public void setAssignee(String taskId, String userId, boolean ischeck)
			throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("待办任务id不能为空！");
		}
		if (ischeck) {
			this.getEngine().getTaskService().claim(taskId, userId);
		} else {
			this.getEngine().getTaskService().setAssignee(taskId, userId);
		}
	}

	@Override
	public void addComment(String userId, String taskId, String message)
			throws WfBusinessException {

		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}

		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("待办任务id不能为空！");
		}
		this.getEngine().getIdentityService().setAuthenticatedUserId(userId);
		this.getEngine().getTaskService().addComment(taskId, null, message);
	}

	@Override
	public void resolveTask(String taskId) throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("待办任务id不能为空！");
		}
		this.getEngine().getTaskService().resolveTask(taskId);
	}

	@Override
	public void delegateTask(String taskId, String OwnerId, String DelegaterId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("待办任务id不能为空！");
		}
		if (StringUtil.isEmpty(OwnerId)) {
			throw new WfBusinessException("被代理委托人id不能为空！");
		}
		if (StringUtil.isEmpty(DelegaterId)) {
			throw new WfBusinessException("代理委托人id不能为空！");
		}
		this.getEngine().getTaskService().setOwner(taskId, OwnerId);
		this.getEngine().getTaskService().delegateTask(taskId, DelegaterId);
		AuthenticationHolder.setCurrentAuthentiUsers(OwnerId, DelegaterId,
				CheckType.None);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

	@Override
	public void addCandidateUser(String taskId, String userId)
			throws WfBusinessException {
		this.getEngine().getTaskService().addCandidateUser(taskId, userId);
	}

	@Override
	public void deleteCandidateUser(String taskId, String userId)
			throws WfBusinessException {
		this.getEngine().getTaskService().deleteCandidateUser(taskId, userId);
	}

	@Override
	public void removeComments(String taskId) throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("任务id不能为空！");
		}
		this.getEngine().getTaskService().deleteComments(taskId, null);
	}

	@Override
	public void removeComment(String commentId) throws WfBusinessException {
		if (StringUtil.isEmpty(commentId)) {
			throw new WfBusinessException("评论id不能为空！");
		}
		this.getEngine().getTaskService().deleteComment(commentId);
	}

	@Override
	public String addAttachment(String taskId, AttachmentVO attachment)
			throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("任务id不能为空！");
		}
		if (attachment == null) {
			throw new WfBusinessException("附件不能为空！");
		}
		Attachment retobj = null;
		if (attachment.getContent() != null) {
			retobj = this
					.getEngine()
					.getTaskService()
					.createAttachment(attachment.getAttachmentType(), taskId,
							null, attachment.getAttachmentName(),
							attachment.getAttachmentDescription(),
							attachment.getContent());
		} else {
			retobj = this
					.getEngine()
					.getTaskService()
					.createAttachment(attachment.getAttachmentType(), taskId,
							null, attachment.getAttachmentName(),
							attachment.getAttachmentDescription(),
							attachment.getUrl());
		}
		return retobj.getId();
	}

	@Override
	public void changeAttachment(String attachmentId, String attachmentName,
			String attachmentDescription) throws WfBusinessException {

		if (attachmentId == null) {
			throw new WfBusinessException("附件id不能为空！");
		}
		AttachmentEntity attachment = new AttachmentEntity();
		attachment.setId(attachmentId);
		attachment.setDescription(attachmentDescription);
		attachment.setName(attachmentName);
		this.getEngine().getTaskService().saveAttachment(attachment);
	}

	@Override
	public void deleteAttachment(String taskId, String attachmentName)
			throws WfBusinessException {
		Collection<AttachmentVO> rescols = this.getTaskQueryService()
				.queryTaskAttachmentVOsByAtchName(taskId, attachmentName);
		if (rescols != null && rescols.size() > 0) {
			for (AttachmentVO tmpone : rescols) {
				this.deleteAttachment(tmpone.getId());
			}
		}
	}

	@Override
	public void deleteAttachment(String attachmentId)
			throws WfBusinessException {
		this.getEngine().getTaskService().deleteAttachment(attachmentId);
	}
}
