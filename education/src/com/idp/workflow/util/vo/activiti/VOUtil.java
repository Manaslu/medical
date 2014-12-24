package com.idp.workflow.util.vo.activiti;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.AttachmentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.loader.activiti.ExtReflectUtil;
import com.idp.workflow.util.service.LocaterServiceFactory;
import com.idp.workflow.vo.identity.IRoleVO;
import com.idp.workflow.vo.identity.IUserGroupVO;
import com.idp.workflow.vo.identity.RoleVO;
import com.idp.workflow.vo.identity.UserGroupVO;
import com.idp.workflow.vo.identity.UserVO;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.service.prodef.DeploymentVO;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.AttachmentVO;
import com.idp.workflow.vo.service.task.CommentVO;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
import com.idp.workflow.vo.service.task.TaskDefineVO;
import com.idp.workflow.vo.service.task.TaskIMageVO;
import com.idp.workflow.vo.service.task.TaskInfoVO;
import com.idp.workflow.vo.service.task.TaskVO;

/**
 * 业务VO转换
 * 
 * @author panfei
 * 
 */
public class VOUtil {

	/**
	 * 转换为用户组vo
	 * 
	 * @param retlist
	 * @return
	 */
	public static List<IUserGroupVO> convertUserGroupVOs(List<Group> retlist) {
		if (retlist == null) {
			return null;
		}
		List<IUserGroupVO> list = new ArrayList<IUserGroupVO>();
		for (Group temp : retlist) {
			UserGroupVO converone = new UserGroupVO(temp.getId(),
					temp.getName());
			list.add(converone);
		}
		return list;
	}

	/**
	 * 转换为角色vo
	 * 
	 * @param retlist
	 * @return
	 */
	public static List<IRoleVO> convertRoleVOs(List<Group> retlist) {
		if (retlist == null) {
			return null;
		}
		List<IRoleVO> list = new ArrayList<IRoleVO>();
		for (Group temp : retlist) {
			RoleVO converone = new RoleVO(temp.getId(), temp.getName());
			list.add(converone);
		}
		return list;
	}

	/**
	 * 用户vo转换
	 * 
	 * @param uservo
	 * @return
	 */
	public static UserVO convertUserVO(User uservo) {
		if (uservo == null) {
			return null;
		}
		UserVO retvo = new UserVO(uservo.getId(), uservo.getPassword(),
				uservo.getFirstName());
		return retvo;
	}

	/**
	 * 任务定义信息vo转换
	 * 
	 * @param taskdef
	 * @return
	 * @throws WfBusinessException
	 */
	@SuppressWarnings("unchecked")
	public static TaskDefineVO convertTaskDefineVO(ActivityImpl actinfo,
			String processDefinitionId) throws WfBusinessException {
		if (actinfo == null) {
			return null;
		}

		TaskDefineVO retdef = new TaskDefineVO();
		retdef.setId(actinfo.getId());
		retdef.setTaskDefinitionCode(actinfo.getId());
		retdef.setProcessDefinitionId(processDefinitionId);
		retdef.setName(String.valueOf(actinfo.getProperty("name")));
		retdef.setHeight(actinfo.getHeight());
		retdef.setWidth(actinfo.getWidth());
		retdef.setX(actinfo.getX());
		retdef.setY(actinfo.getY());

		if (IWorkFlowTypes.activityType_ServicesTask.equals(actinfo
				.getProperty("type"))) {
			ClassDelegate calssdelegate = (ClassDelegate) actinfo
					.getActivityBehavior();
			// update for 5.14 by panfei
			List<FieldDeclaration> fielddecls = ((List<FieldDeclaration>) ExtReflectUtil
					.invokeField(calssdelegate, "fieldDeclarations"));
			String params = null;
			if (fielddecls != null && fielddecls.size() > 0) {
				for (FieldDeclaration felddcare : fielddecls) {
					if (IWorkFlowTypes.configParamsName.equals(felddcare
							.getName())) {
						if (felddcare.getValue() != null) {
							FixedValue value = (FixedValue) felddcare
									.getValue();
							params = value.getExpressionText();
						}
						break;
					}
				}
			}
			retdef.setConfigParams(StringUtil.convertConfigParamsVO(params));

		} else if (IWorkFlowTypes.activityType_UserTask.equals(actinfo
				.getProperty("type"))) {

		}

		return retdef;
	}

	/**
	 * 任务vo转换 该vo包含图片信息
	 * 
	 * @param taskinfo
	 * @param activiti
	 * @return
	 */
	public static TaskIMageVO convertTaskIMageVO(Task taskinfo,
			ActivityImpl activiti) {
		if (taskinfo == null || activiti == null) {
			return null;
		}
		TaskIMageVO retvo = new TaskIMageVO();
		retvo.setId(taskinfo.getId());
		retvo.setName(taskinfo.getName());
		retvo.setOwnerId(taskinfo.getOwner());
		retvo.setAssignerId(taskinfo.getAssignee());
		retvo.setCreateTime(taskinfo.getCreateTime());
		retvo.setDueDate(taskinfo.getDueDate());
		retvo.setConfigParams(StringUtil.convertConfigParamsVO(taskinfo
				.getDescription()));
		retvo.setTaskDefinitionCode(taskinfo.getTaskDefinitionKey());
		retvo.setProcessDefinitionId(taskinfo.getProcessDefinitionId());
		retvo.setProcessInstanceId(taskinfo.getProcessInstanceId());
		retvo.setX(activiti.getX());
		retvo.setY(activiti.getY());
		retvo.setHeight(activiti.getHeight());
		retvo.setWidth(activiti.getWidth());
		return retvo;
	}

	/**
	 * 任务vo转换
	 * 
	 * @param taskinfo
	 *            需要转换的任务vo
	 * @return
	 */
	public static TaskVO convertTaskVO(Task taskinfo) {
		if (taskinfo == null) {
			return null;
		}
		TaskVO retvo = new TaskVO();
		retvo.setId(taskinfo.getId());
		retvo.setName(taskinfo.getName());
		retvo.setOwnerId(taskinfo.getOwner());
		retvo.setAssignerId(taskinfo.getAssignee());
		retvo.setCreateTime(taskinfo.getCreateTime());
		retvo.setDueDate(taskinfo.getDueDate());
		retvo.setConfigParams(StringUtil.convertConfigParamsVO(taskinfo
				.getDescription()));
		retvo.setTaskDefinitionCode(taskinfo.getTaskDefinitionKey());
		retvo.setProcessDefinitionId(taskinfo.getProcessDefinitionId());
		retvo.setProcessInstanceId(taskinfo.getProcessInstanceId());
		return retvo;
	}

	/**
	 * 历史任务vo转换
	 * 
	 * @param taskinfo
	 * @return
	 */
	public static HistoricTaskVO convertHisTaskVO(HistoricTaskInstance taskinfo) {
		if (taskinfo == null) {
			return null;
		}
		HistoricTaskVO retvo = new HistoricTaskVO();
		retvo.setId(taskinfo.getId());
		retvo.setName(taskinfo.getName());
		retvo.setOwnerId(taskinfo.getOwner());
		retvo.setAssignerId(taskinfo.getAssignee());
		retvo.setTaskDefinitionCode(taskinfo.getTaskDefinitionKey());
		retvo.setProcessDefinitionId(taskinfo.getProcessDefinitionId());
		retvo.setProcessInstanceId(taskinfo.getProcessInstanceId());
		// retvo.setActionType(HistoricTaskVO.ACTIONTYPE_BACKWARD);
		retvo.setDescription(taskinfo.getDescription());
		return retvo;
	}

	/**
	 * 历史活动转换历史任务vo
	 * 
	 * @param actinfo
	 * @return
	 */
	public static HistoricTaskVO convertHisTaskVO(
			HistoricActivityInstance actinfo) {
		if (actinfo == null) {
			return null;
		}
		HistoricTaskVO retvo = new HistoricTaskVO();
		retvo.setParentTaskId(actinfo.getTaskId());
		retvo.setName(actinfo.getActivityName());
		retvo.setOwnerId(actinfo.getAssignee());
		retvo.setAssignerId(actinfo.getAssignee());
		retvo.setTaskDefinitionCode(actinfo.getActivityId());
		retvo.setProcessDefinitionId(actinfo.getProcessDefinitionId());
		retvo.setProcessInstanceId(actinfo.getProcessInstanceId());
		return retvo;
	}

	/**
	 * 任务批量vo转换
	 * 
	 * @param taskinfos
	 *            需要转换的任务vo集合
	 * @return
	 */
	public static List<TaskVO> convertTaskVOs(List<Task> taskinfos) {
		if (taskinfos != null) {
			List<TaskVO> retlist = new ArrayList<TaskVO>();
			for (Task temp : taskinfos) {
				retlist.add(convertTaskVO(temp));
			}
			return retlist;
		}
		return null;
	}

	/**
	 * 转换为工作流定义VO
	 * 
	 * @param deployvo
	 *            资源部署
	 * @param prodefvo
	 * @return
	 */
	public static ProcessDefineVO convertVO(Deployment deployvo,
			ProcessDefinition prodefvo) {
		if (prodefvo == null) {
			return null;
		}
		// just new
		ProcessDefineVO retvo = new ProcessDefineVO();
		// id
		retvo.setId(prodefvo.getId());
		// name
		retvo.setName(prodefvo.getName());
		// code
		retvo.setCode(prodefvo.getKey());
		retvo.setCategory(prodefvo.getCategory());
		// version
		retvo.setVersion(prodefvo.getVersion());
		// resources
		retvo.setResourceName(prodefvo.getResourceName());
		retvo.setDiagramResourceName(prodefvo.getDiagramResourceName());
		// deployid
		retvo.setDeploymentId(prodefvo.getDeploymentId());
		// deployname
		if (deployvo != null) {
			retvo.setDeployName(deployvo.getName());
		}
		// others
		retvo.setHasStartFormKey(prodefvo.hasStartFormKey());
		retvo.setSuspensionState(((ProcessDefinitionEntity) prodefvo)
				.getSuspensionState());
		retvo.setDescription(((ProcessDefinitionEntity) prodefvo)
				.getDescription());
		return retvo;
	}

	/**
	 * 批量转换为流程定义vo
	 * 
	 * @param list
	 * @return
	 */
	public static List<ProcessDefineVO> convertProcessDefineVOs(
			List<ProcessDefinition> list) {
		if (list != null) {
			List<ProcessDefineVO> retlist = new ArrayList<ProcessDefineVO>();
			for (ProcessDefinition temp : list) {
				retlist.add(convertVO(null, temp));
			}
			return retlist;
		}
		return null;
	}

	/**
	 * 转换为部署VO
	 * 
	 * @param deployvo
	 * @return
	 */
	public static DeploymentVO convertDeployVO(Deployment deployvo,
			ProcessDefinition prodefvo) {
		if (prodefvo == null || deployvo == null) {
			return null;
		}
		// just new
		DeploymentVO retvo = new DeploymentVO();
		// id
		retvo.setId(deployvo.getId());
		// name
		retvo.setName(deployvo.getName());
		// time
		retvo.setDeploymentTime(deployvo.getDeploymentTime());
		// xml resource
		DeploymentEntity depentity = (DeploymentEntity) deployvo;
		String xmlresname = prodefvo.getResourceName();
		ResourceEntity xmlres = depentity.getResource(xmlresname);
		if (xmlres != null) {
			retvo.setConfigXmlResource(new ByteArrayInputStream(xmlres
					.getBytes()));
		}
		// pic resource
		String picresname = prodefvo.getDiagramResourceName();
		ResourceEntity picres = depentity.getResource(picresname);
		if (picres != null) {
			retvo.setConfigXmlResource(new ByteArrayInputStream(picres
					.getBytes()));
		}
		return retvo;
	}

	/**
	 * 
	 * @param proinstvos
	 * @param processDefinitionCode
	 * @return
	 */
	public static List<ProcessInstanceVO> convertProcessInstanceVOs(
			List<HistoricProcessInstance> proinstvos,
			String processDefinitionCode) {
		if (proinstvos != null) {
			List<ProcessInstanceVO> retlist = new ArrayList<ProcessInstanceVO>();
			for (HistoricProcessInstance temp : proinstvos) {
				retlist.add(convertProcessInstanceVO(temp,
						processDefinitionCode));
			}
			return retlist;
		}
		return null;
	}

	/**
	 * 工作流实例vo转换
	 * 
	 * @param proinstvo
	 * @return
	 */
	public static ProcessInstanceVO convertProcessInstanceVO(
			HistoricProcessInstance proinstvo, String processDefinitionCode) {
		if (proinstvo == null) {
			return null;
		}
		ProcessInstanceVO retvo = new ProcessInstanceVO();
		// id
		retvo.setId(proinstvo.getId());
		// prodefid
		retvo.setProcessDefinitionId(proinstvo.getProcessDefinitionId());
		// if (proinstvo instanceof HistoricProcessInstanceEntity) {
		// prodefkey
		retvo.setProcessDefinitionCode(processDefinitionCode);
		// businessCode
		retvo.setBusinessCode(proinstvo.getBusinessKey());

		if (proinstvo.getEndTime() == null) {
			// end
			retvo.setEnd(false);
			// suspend
			retvo.setSuspended(false);
		} else {
			retvo.setEnd(true);
			// suspend
			retvo.setSuspended(false);
		}
		// }
		return retvo;
	}

	/**
	 * 工作流实例vo转换
	 * 
	 * @param proinstvo
	 * @return
	 * @throws WfBusinessException
	 */
	public static ProcessInstanceVO convertProcessInstanceVO(
			ProcessInstance proinstvo) throws WfBusinessException {

		if (proinstvo == null) {
			return null;
		}

		ProcessInstanceVO retvo = new ProcessInstanceVO();
		// id
		retvo.setId(proinstvo.getProcessInstanceId());
		// prodefid
		retvo.setProcessDefinitionId(proinstvo.getProcessDefinitionId());

		IProDefineQueryService prodefquery = LocaterServiceFactory
				.GetInstance().lookup(IProDefineQueryService.class);
		if (prodefquery != null) {
			ProcessDefineVO prodefvo = prodefquery
					.queryProcessDefinitionById(proinstvo
							.getProcessDefinitionId());
			retvo.setProcessDefinitionCode(prodefvo.getCode());
		}
		/*
		 * 通过缓存获取部署信息，有空指针的情况 if (proinstvo instanceof ExecutionEntity) {
		 * ExecutionEntity excution = (ExecutionEntity) proinstvo;
		 * ProcessDefinitionEntity proinsdef = (ProcessDefinitionEntity)
		 * excution .getProcessDefinition(); // prodefkey
		 * retvo.setProcessDefinitionCode(proinsdef.getKey()); }
		 */
		// businessCode
		retvo.setBusinessCode(proinstvo.getBusinessKey());
		// suspend
		retvo.setSuspended(proinstvo.isSuspended());
		// end
		retvo.setEnd(proinstvo.isEnded());
		return retvo;
	}

	/**
	 * 批量转换为工作流实例vo
	 * 
	 * @param list
	 * @return
	 * @throws WfBusinessException
	 */
	public static List<ProcessInstanceVO> convertProcessInstanceVOs(
			List<ProcessInstance> list) throws WfBusinessException {
		if (list != null) {
			List<ProcessInstanceVO> retlist = new ArrayList<ProcessInstanceVO>();
			for (ProcessInstance temp : list) {
				retlist.add(convertProcessInstanceVO(temp));
			}
			return retlist;
		}
		return null;
	}

	/**
	 * 转换工作流中任务信息vo
	 * 
	 * @param lastHisTask
	 * @return
	 */
	public static TaskInfoVO convertTaskInfoVO(HistoricTaskInstance lastHisTask) {
		TaskInfoVO taskinfo = new TaskInfoVO();
		taskinfo.setName(lastHisTask.getName());
		taskinfo.setProcessInstanceId(lastHisTask.getProcessInstanceId());
		taskinfo.setTaskDefinitionKey(lastHisTask.getTaskDefinitionKey());
		taskinfo.setAutoRun(false);
		taskinfo.setConfigParams(StringUtil.convertConfigParamsVO(lastHisTask
				.getDescription()));
		return taskinfo;
	}

	@SuppressWarnings("unchecked")
	public static TaskInfoVO convertTaskInfoVO(ActivityImpl actimpl,
			String proInstanceID) throws WfBusinessException {

		ClassDelegate calssdel = (ClassDelegate) actimpl.getActivityBehavior();
		TaskInfoVO taskinfo = new TaskInfoVO();
		taskinfo.setName(actimpl.getId());
		taskinfo.setProcessInstanceId(proInstanceID);
		taskinfo.setTaskDefinitionKey(actimpl.getId());
		taskinfo.setAutoRun(checkIsAuto(actimpl));
		if (calssdel != null) {
			// update for 5.14 by panfei
			List<FieldDeclaration> list = ((List<FieldDeclaration>) ExtReflectUtil
					.invokeField(calssdel, "fieldDeclarations"));

			if (list != null) {
				for (FieldDeclaration temp : list) {
					if (IWorkFlowTypes.configParamsName.equals(temp.getName())) {
						FixedValue value = (FixedValue) temp.getValue();
						if (value != null) {
							taskinfo.setConfigParams(StringUtil
									.convertConfigParamsVO(value
											.getExpressionText()));
						}
					}
				}
			}
		}
		return taskinfo;

	}

	public static TaskInfoVO convertTaskInfoVO4User(ActivityImpl actimpl,
			String proInstanceID, Map<String, ?> params) {
		UserTaskActivityBehavior useract = (UserTaskActivityBehavior) actimpl
				.getActivityBehavior();
		TaskInfoVO taskinfo = new TaskInfoVO();
		taskinfo.setName(actimpl.getId());
		taskinfo.setProcessInstanceId(proInstanceID);
		taskinfo.setTaskDefinitionKey(actimpl.getId());
		taskinfo.setAutoRun(checkIsAuto(actimpl));
		taskinfo.setReMark(StringUtil.convertString(params
				.get(IWorkFlowTypes.remark)));
		taskinfo.setSignature(StringUtil.convertString(params
				.get(IWorkFlowTypes.signature)));
		Expression descripExpression = useract.getTaskDefinition()
				.getDescriptionExpression();
		if (descripExpression != null) {
			taskinfo.setConfigParams(StringUtil
					.convertConfigParamsVO(descripExpression
							.getExpressionText()));
		}
		taskinfo.setAssignee(useract.getTaskDefinition()
				.getAssigneeExpression() != null ? useract.getTaskDefinition()
				.getAssigneeExpression().getExpressionText() : null);
		taskinfo.setCandidateGroup(useract.getTaskDefinition()
				.getCandidateGroupIdExpressions().isEmpty() ? null : useract
				.getTaskDefinition().getCandidateGroupIdExpressions()
				.iterator().next().getExpressionText());
		return taskinfo;
	}

	/**
	 * 判断任务是否是自动任务
	 * 
	 * @param actimpl
	 * @return
	 */
	public static boolean checkIsAuto(ActivityImpl actimpl) {
		String act_type = StringUtil.convertString(actimpl.getProperty("type"));
		if (IWorkFlowTypes.activityType_UserTask.equals(act_type)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据历史工作流实例VO转换为活动的工作流实例VO
	 * 
	 * @param hisproinsvo
	 * @return
	 */
	public static ExecutionEntity convertExecutionEntity(
			HistoricProcessInstance hisproinsvo) {
		ExecutionEntity newExecution = new ExecutionEntity();
		// 设置状态
		newExecution.setActive(true);
		newExecution.setSuspensionState(SuspensionState.ACTIVE.getStateCode());
		// 设置业务流水号
		newExecution.setBusinessKey(hisproinsvo.getBusinessKey());
		// 设置工作流定义ID
		newExecution.setProcessDefinitionId(hisproinsvo
				.getProcessDefinitionId());
		newExecution.setExecutions(new ArrayList<ExecutionEntity>());
		return newExecution;
	}

	/**
	 * 任务vo转换为历史任务vo
	 * 
	 * @param taskvo
	 * @return
	 */
	public static HistoricTaskVO convertHistoricTaskVO(Task taskvo) {
		if (taskvo == null) {
			return null;
		}
		HistoricTaskVO retvo = new HistoricTaskVO();
		retvo.setId(taskvo.getId());
		retvo.setParentTaskId(taskvo.getId());
		retvo.setAssignerId(taskvo.getAssignee());
		retvo.setDescription(taskvo.getDescription());
		retvo.setName(taskvo.getName());
		retvo.setOwnerId(taskvo.getOwner());
		retvo.setTaskDefinitionCode(taskvo.getTaskDefinitionKey());
		retvo.setOwnerId(taskvo.getOwner());
		retvo.setProcessDefinitionId(taskvo.getProcessDefinitionId());
		retvo.setProcessInstanceId(taskvo.getProcessInstanceId());
		retvo.setExecutionId(taskvo.getExecutionId());
		// retvo.setActionType(HistoricTaskVO.ACTIONTYPE_FORWARD);
		return retvo;
	}

	/**
	 * 评论VO集合转换
	 * 
	 * @param comments
	 * @return
	 */
	public static List<CommentVO> convertCommentVOs(List<Comment> comments) {
		if (comments != null) {
			List<CommentVO> list = new ArrayList<CommentVO>();
			for (Comment tmpone : comments) {
				list.add(convertCommentVO(tmpone));
			}
			return list;
		}
		return null;
	}

	/**
	 * 评论VO转换
	 * 
	 * @param comment
	 * @return
	 */
	public static CommentVO convertCommentVO(Comment comment) {

		if (comment == null) {
			return null;
		}

		CommentVO conmvo = new CommentVO();
		conmvo.setId(comment.getId());
		conmvo.setProcessInstanceId(comment.getProcessInstanceId());
		conmvo.setTaskId(comment.getTaskId());
		conmvo.setTime(comment.getTime());
		conmvo.setType(comment.getType());
		conmvo.setUserId(comment.getUserId());
		conmvo.setFullMessage(comment.getFullMessage());
		return conmvo;
	}

	/**
	 * 附件vo转换
	 * 
	 * @param attachment
	 * @return
	 */
	public static AttachmentVO convertAttachmentVO(Attachment attachment,
			InputStream content) {
		if (attachment == null) {
			return null;
		}
		AttachmentVO retvo = new AttachmentVO(attachment.getName(),
				attachment.getType(), attachment.getDescription(),
				attachment.getUrl());
		retvo.setContent(content);
		retvo.setId(attachment.getId());
		retvo.setProcessInstanceId(attachment.getProcessInstanceId());
		retvo.setTaskId(attachment.getTaskId());
		return retvo;
	}

	/**
	 * 附件vo转换
	 * 
	 * @param attachment
	 * @return
	 */
	public static Attachment convertAttachmentEntity(AttachmentVO attachmentvo) {

		if (attachmentvo == null) {
			return null;
		}

		AttachmentEntity attachment = new AttachmentEntity();
		attachment.setId(attachmentvo.getId());
		attachment.setName(attachmentvo.getAttachmentName());
		attachment.setType(attachmentvo.getAttachmentType());
		attachment.setDescription(attachmentvo.getAttachmentDescription());
		attachment.setUrl(attachmentvo.getUrl());
		attachment.setTaskId(attachmentvo.getTaskId());
		attachment.setProcessInstanceId(attachmentvo.getProcessInstanceId());
		return attachment;
	}
}
