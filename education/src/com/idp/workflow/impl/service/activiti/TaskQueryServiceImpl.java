package com.idp.workflow.impl.service.activiti;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.exception.pub.WfNotSupprotException;
import com.idp.workflow.itf.service.task.ITaskQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.OrderCondition;
import com.idp.workflow.vo.pub.WhereCondition;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;
import com.idp.workflow.vo.service.task.AttachmentVO;
import com.idp.workflow.vo.service.task.CommentVO;
import com.idp.workflow.vo.service.task.TaskDefineVO;
import com.idp.workflow.vo.service.task.TaskIMageVO;
import com.idp.workflow.vo.service.task.TaskVO;

/**
 * 工作待办事务任务查询服务实现
 * 
 * @author panfei
 * 
 */
public class TaskQueryServiceImpl extends BaseServiceImpl implements
		ITaskQueryService {

	public TaskQueryServiceImpl() {
		super();
	}

	public TaskQueryServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public TaskVO queryTaskVOById(String taskId) throws WfBusinessException {
		if (StringUtil.isEmpty(taskId)) {
			throw new WfBusinessException("任务id不能为空！");
		}
		Task taskvo = this.getEngine().getTaskService().createTaskQuery()
				.taskId(taskId).singleResult();
		return VOUtil.convertTaskVO(taskvo);
	}

	@Override
	public Collection<TaskVO> queryTaskVOByName(String taskName)
			throws WfBusinessException {
		if (StringUtil.isEmpty(taskName)) {
			throw new WfBusinessException("任务名称不能为空！");
		}
		List<Task> taskvos = this.getEngine().getTaskService()
				.createTaskQuery().taskName(taskName).list();
		return VOUtil.convertTaskVOs(taskvos);
	}

	@Override
	public Collection<TaskVO> queryTakeAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processInstanceId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processInstanceId)) {
			superquery = superquery.processInstanceId(processInstanceId);
		}
		if (!StringUtil.isEmpty(TaskDefineCode)) {
			superquery = superquery.taskDefinitionKey(TaskDefineCode);
		}

		List<Task> tasks = superquery.taskAssignee(userId)
				.orderByTaskCreateTime().desc().list();
		return VOUtil.convertTaskVOs(tasks);
	}

	@Override
	public Collection<TaskVO> queryCanAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processInstanceId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processInstanceId)) {
			superquery = superquery.processInstanceId(processInstanceId);
		}
		if (!StringUtil.isEmpty(TaskDefineCode)) {
			superquery = superquery.taskDefinitionKey(TaskDefineCode);
		}
		List<Task> tasks = superquery.taskCandidateUser(userId)
				.orderByTaskCreateTime().desc().list();
		return VOUtil.convertTaskVOs(tasks);
	}

	@Override
	public Collection<TaskVO> queryToDoTasksByUserId(String userId,
			String TaskDefineCode, String processInstanceId)
			throws WfBusinessException {
		Collection<TaskVO> firstresult = this.queryTakeAssignedTasksByUserId(
				userId, TaskDefineCode, processInstanceId);
		Collection<TaskVO> secondresult = this.queryCanAssignedTasksByUserId(
				userId, TaskDefineCode, processInstanceId);
		if (firstresult != null) {
			if (secondresult != null)
				firstresult.addAll(secondresult);
		} else {
			return secondresult;
		}
		return firstresult;
	}

	@Override
	public Collection<TaskVO> queryTasksByCondtions(String userId,
			WhereCondition whereCondition, OrderCondition orderCondition)
			throws WfBusinessException {
		throw new WfNotSupprotException("comeing later");
	}

	@Override
	public Collection<TaskIMageVO> queryCurrentTaskVO(String processInstanceId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}
		List<Task> rettasklist = this.getEngine().getTaskService()
				.createTaskQuery().processInstanceId(processInstanceId).list();
		if (rettasklist != null && rettasklist.size() > 0) {
			List<TaskIMageVO> retlist = new ArrayList<TaskIMageVO>();

			ProcessDefinitionEntity defimpl = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
					.getEngine().getRepositoryService())
					.getDeployedProcessDefinition(rettasklist.get(0)
							.getProcessDefinitionId()));

			for (Task temp : rettasklist) {
				retlist.add(VOUtil.convertTaskIMageVO(temp,
						defimpl.findActivity(temp.getTaskDefinitionKey())));
			}
			return retlist;
		}
		return null;
	}

	@Override
	public Collection<TaskVO> queryToDoTasksByUserId(String userId,
			String TaskDefineCode, String processDefinitionCode,
			String businessCode) throws WfBusinessException {
		Collection<TaskVO> firstresult = this.queryTakeAssignedTasksByUserId(
				userId, TaskDefineCode, processDefinitionCode, businessCode);
		Collection<TaskVO> secondresult = this.queryCanAssignedTasksByUserId(
				userId, TaskDefineCode, processDefinitionCode, businessCode);
		if (firstresult != null) {
			if (secondresult != null)
				firstresult.addAll(secondresult);
		} else {
			return secondresult;
		}
		return firstresult;
	}

	@Override
	public Collection<TaskIMageVO> queryCurrentTaskVO(
			String processDefinitionCode, String businessCode)
			throws WfBusinessException {

		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例业务编码不能为空！");
		}

		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();

		if (!StringUtil.isEmpty(processDefinitionCode)) {
			superquery = superquery.processDefinitionKey(processDefinitionCode);
		}

		List<Task> rettasklist = superquery.processInstanceBusinessKey(
				businessCode).list();
		if (rettasklist != null && rettasklist.size() > 0) {
			List<TaskIMageVO> retlist = new ArrayList<TaskIMageVO>();

			ProcessDefinitionEntity defimpl = (ProcessDefinitionEntity) (((RepositoryServiceImpl) this
					.getEngine().getRepositoryService())
					.getDeployedProcessDefinition(rettasklist.get(0)
							.getProcessDefinitionId()));

			for (Task temp : rettasklist) {
				retlist.add(VOUtil.convertTaskIMageVO(temp,
						defimpl.findActivity(temp.getTaskDefinitionKey())));
			}
			return retlist;
		}
		return null;
	}

	@Override
	public Collection<TaskVO> queryTakeAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processDefinitionCode,
			String businessCode) throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			superquery = superquery.processDefinitionKey(processDefinitionCode);
		}
		if (!StringUtil.isEmpty(businessCode)) {
			superquery = superquery.processInstanceBusinessKey(businessCode);
		}

		if (!StringUtil.isEmpty(TaskDefineCode)) {
			superquery = superquery.taskDefinitionKey(TaskDefineCode);
		}

		return VOUtil.convertTaskVOs(superquery.taskAssignee(userId)
				.orderByTaskCreateTime().desc().list());
	}

	@Override
	public Collection<TaskVO> queryCanAssignedTasksByUserId(String userId,
			String TaskDefineCode, String processDefinitionCode,
			String businessCode) throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			superquery = superquery.processDefinitionKey(processDefinitionCode);
		}
		if (!StringUtil.isEmpty(businessCode)) {
			superquery = superquery.processInstanceBusinessKey(businessCode);
		}

		if (!StringUtil.isEmpty(TaskDefineCode)) {
			superquery = superquery.taskDefinitionKey(TaskDefineCode);
		}

		return VOUtil.convertTaskVOs(superquery.taskCandidateUser(userId)
				.orderByTaskCreateTime().desc().list());
	}

	@Override
	public Collection<TaskDefineVO> queryTaskDefineVOsByLastProDefineVersion(
			String processDefinitionCode) throws WfBusinessException {
		ProcessDefineVO prodeinevo = this.getProDefineQueryService()
				.queryLastProcessDefinitionByCode(processDefinitionCode);
		if (prodeinevo != null) {
			return this.queryTaskDefineVOs(prodeinevo.getId());
		} else {
			return null;
		}
	}

	@Override
	public Collection<TaskDefineVO> queryTaskDefineVOs(
			String processDefinitionId) throws WfBusinessException {

		if (StringUtil.isEmpty(processDefinitionId)) {
			throw new WfBusinessException("流程定义id不能为空！");
		}
		RepositoryServiceImpl resporySerivcies = (RepositoryServiceImpl) this
				.getEngine().getRepositoryService();
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity) resporySerivcies
				.getDeployedProcessDefinition(processDefinitionId);
		// ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity)
		// resporySerivcies
		// .createProcessDefinitionQuery()
		// .processDefinitionId(processDefinitionId).singleResult();
		if (processEntity != null) {
			// Map<String, TaskDefinition> taskdefs = processEntity
			// .getTaskDefinitions();
			List<ActivityImpl> actlist = processEntity.getActivities();
			if (actlist != null) {
				List<TaskDefineVO> retlist = new ArrayList<TaskDefineVO>();
				for (ActivityImpl actone : actlist) {

					if (IWorkFlowTypes.activityType_ServicesTask.equals(actone
							.getProperty("type"))
							|| IWorkFlowTypes.activityType_UserTask
									.equals(actone.getProperty("type"))) {
						retlist.add(VOUtil.convertTaskDefineVO(actone,
								processDefinitionId));
					}

				}
				return retlist;
			}
		}
		return null;
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

	@Override
	public TaskVO queryTaskVOByTaskDefineCode(String processInstanceId,
			String TaskDefineCode) throws WfBusinessException {

		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}

		if (StringUtil.isEmpty(TaskDefineCode)) {
			throw new WfBusinessException("工作流任务编码不能为空！");
		}

		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();

		superquery = superquery.processInstanceId(processInstanceId)
				.taskDefinitionKey(TaskDefineCode);

		return VOUtil.convertTaskVO(superquery.singleResult());
	}

	@Override
	public TaskVO queryTaskVOByTaskDefineCode(String processDefinitionCode,
			String businessCode, String TaskDefineCode)
			throws WfBusinessException {
		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例编码不能为空！");
		}

		if (StringUtil.isEmpty(TaskDefineCode)) {
			throw new WfBusinessException("工作流任务编码不能为空！");
		}

		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();

		if (!StringUtil.isEmpty(processDefinitionCode)) {
			superquery = superquery.processDefinitionKey(processDefinitionCode);
		}

		superquery = superquery.processInstanceBusinessKey(businessCode)
				.taskDefinitionKey(TaskDefineCode);

		return VOUtil.convertTaskVO(superquery.singleResult());
	}

	@Override
	public Collection<CommentVO> queryTaskCommentVOsById(String taskId)
			throws WfBusinessException {
		List<Comment> comments = this.getEngine().getTaskService()
				.getTaskComments(taskId);
		return VOUtil.convertCommentVOs(comments);
	}

	@Override
	public Collection<AttachmentVO> queryTaskAttachmentVOsByAtchName(
			String taskId, String attachmentName) throws WfBusinessException {
		List<AttachmentVO> attlist = new ArrayList<AttachmentVO>();
		Collection<AttachmentVO> rescols = this
				.queryTaskAttachmentVOsById(taskId);
		if (rescols != null && rescols.size() > 0) {
			for (AttachmentVO tmpone : rescols) {
				if (tmpone.getAttachmentName().equals(attachmentName)) {
					attlist.add(tmpone);
				}
			}
			return attlist;
		}
		return null;
	}

	@Override
	public Collection<AttachmentVO> queryTaskAttachmentVOsById(String taskId)
			throws WfBusinessException {
		List<Attachment> reslist = this.getEngine().getTaskService()
				.getTaskAttachments(taskId);
		if (reslist != null && reslist.size() > 0) {
			List<AttachmentVO> attlist = new ArrayList<AttachmentVO>();
			for (Attachment tmpone : reslist) {
				InputStream content = this.getEngine().getTaskService()
						.getAttachmentContent(tmpone.getId());
				attlist.add(VOUtil.convertAttachmentVO(tmpone, content));
			}
			return attlist;
		}
		return null;
	}

	@Override
	public AttachmentVO queryTaskAttachmentVOsByAtchId(String attachmentId)
			throws WfBusinessException {
		Attachment attachment = this.getEngine().getTaskService()
				.getAttachment(attachmentId);
		InputStream content = this.getEngine().getTaskService()
				.getAttachmentContent(attachmentId);
		return VOUtil.convertAttachmentVO(attachment, content);
	}

	@Override
	public Collection<TaskVO> queryTaskVOByUserRole(String roleid,
			String processInstanceId) throws WfBusinessException {

		if (StringUtil.isEmpty(roleid)) {
			throw new WfBusinessException("用户角色id不能为空！");
		}
		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processInstanceId)) {
			superquery = superquery.processInstanceId(processInstanceId);
		}

		List<Task> tasks = superquery.orderByTaskCreateTime().desc().list();
		return VOUtil.convertTaskVOs(tasks);
	}

	@Override
	public Collection<TaskVO> queryTaskVOByUserRole(String roleid,
			String processDefinitionCode, String businessCode)
			throws WfBusinessException {

		if (StringUtil.isEmpty(roleid)) {
			throw new WfBusinessException("用户角色id不能为空！");
		}
		TaskQuery superquery = this.getEngine().getTaskService()
				.createTaskQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			superquery = superquery.processDefinitionKey(processDefinitionCode);
		}
		if (!StringUtil.isEmpty(businessCode)) {
			superquery = superquery.processInstanceBusinessKey(businessCode);
		}

		List<Task> tasks = superquery.orderByTaskCreateTime().desc().list();
		return VOUtil.convertTaskVOs(tasks);
	}
}
