package com.idp.workflow.impl.service.activiti;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Attachment;
import org.springframework.transaction.annotation.Transactional;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.util.cache.MiniCache;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.service.WfParametersVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.AttachmentVO;
 
/**
 * 工作流实例管理服务
 * 
 * @author panfei
 * 
 */
@Transactional
public class ProInstanceMangeServiceImpl extends BaseServiceImpl implements
		IProInstanceMangeService {

	public ProInstanceMangeServiceImpl() {
		super();
	}

	public ProInstanceMangeServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public ProcessInstanceVO startProcessInstanceById(String processDefinitionId)
			throws WfBusinessException {
		return startProcessInstanceById(processDefinitionId, null, null);
	}

	@Override
	public ProcessInstanceVO startProcessInstanceById(
			String processDefinitionId, WfParametersVO variables)
			throws WfBusinessException {
		return startProcessInstanceById(processDefinitionId, null, variables);
	}

	public ProcessInstanceVO startProcessInstanceById(
			String processDefinitionId, String businessCode,
			WfParametersVO variables) throws WfBusinessException {
		Map<String, Object> paraMap = null;
		if (variables != null) {
			paraMap = variables.getContext();
			this.setAuthenticatedSarttPersion(variables);
		}
		ProcessInstance tempdefvo = this
				.getEngine()
				.getRuntimeService()
				.startProcessInstanceById(processDefinitionId, businessCode,
						paraMap);
		return VOUtil.convertProcessInstanceVO(tempdefvo);
	}

	@Override
	public void deleteProcessInstance(String processInstanceId,
			String deleteReason) throws WfBusinessException {
		this.getEngine().getRuntimeService()
				.deleteProcessInstance(processInstanceId, deleteReason);
		this.deleteGlobalVariables(processInstanceId);
	}

	@Override
	public void suspendProcessInstance(String processInstanceId)
			throws WfBusinessException {
		this.getEngine().getRuntimeService()
				.suspendProcessInstanceById(processInstanceId);
	}

	@Override
	public void activateProcessInstance(String processInstanceId)
			throws WfBusinessException {
		this.getEngine().getRuntimeService()
				.activateProcessInstanceById(processInstanceId);
	}

	@Override
	public ProcessInstanceVO startProcessInstanceByCode(
			String processDefinitionCode, String businessCode)
			throws WfBusinessException {
		return this.startProcessInstanceByCode(processDefinitionCode,
				businessCode, null);
	}

	@Override
	public void deleteProcessInstance(String processDefinitionCode,
			String businessCode, String deleteReason)
			throws WfBusinessException {
		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例业务编码不能为空！");
		}
		ProcessInstanceQuery tempquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			tempquery = tempquery.processDefinitionKey(processDefinitionCode);
		}
		tempquery = tempquery.processInstanceBusinessKey(businessCode);
		List<ProcessInstance> dellist = tempquery.list();
		if (dellist != null) {
			for (ProcessInstance tempdel : dellist) {
				this.deleteProcessInstance(tempdel.getProcessInstanceId(),
						deleteReason);
			}
		}
	}

	@Override
	public void suspendProcessInstance(String processDefinitionCode,
			String businessCode) throws WfBusinessException {
		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例业务编码不能为空！");
		}
		ProcessInstanceQuery tempquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			tempquery = tempquery.processDefinitionKey(processDefinitionCode);
		}
		tempquery = tempquery.processInstanceBusinessKey(businessCode);
		List<ProcessInstance> dellist = tempquery.list();
		if (dellist != null) {
			for (ProcessInstance tempdel : dellist) {
				this.suspendProcessInstance(tempdel.getProcessInstanceId());
			}
		}
	}

	@Override
	public void activateProcessInstance(String processDefinitionCode,
			String businessCode) throws WfBusinessException {
		if (StringUtil.isEmpty(businessCode)) {
			throw new WfBusinessException("工作流实例业务编码不能为空！");
		}
		ProcessInstanceQuery tempquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery();
		if (!StringUtil.isEmpty(processDefinitionCode)) {
			tempquery = tempquery.processDefinitionKey(processDefinitionCode);
		}
		tempquery = tempquery.processInstanceBusinessKey(businessCode);
		List<ProcessInstance> dellist = tempquery.list();
		if (dellist != null) {
			for (ProcessInstance tempdel : dellist) {
				this.activateProcessInstance(tempdel.getProcessInstanceId());
			}
		}
	}

	@Override
	public ProcessInstanceVO startProcessInstanceByCode(
			String processDefinitionCode, String businessCode,
			WfParametersVO variables) throws WfBusinessException {

		if (StringUtil.isEmpty(processDefinitionCode)) {
			throw new WfBusinessException("工作流定义编码不能为空！");
		}

		Map<String, Object> paraMap = null;
		if (variables != null) {
			paraMap = variables.getContext();
			this.setAuthenticatedSarttPersion(variables);
		}

		ProcessInstance tempdefvo = this
				.getEngine()
				.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionCode, businessCode,
						paraMap);
		return VOUtil.convertProcessInstanceVO(tempdefvo);
	}

	@Override
	public void saveGlobalVariables(String processInstanceId,
			Map<String, Object> variables) throws WfBusinessException {
		// try {
		// this.getEngine().getRuntimeService()
		// .setVariables(processInstanceId, variables);
		// } catch (ActivitiException e) {
		MiniCache cache = new MiniCache();
		cache.putVariables(processInstanceId, variables);
		// }
	}

	@Override
	public Map<String, Object> getGlobalVariables(String processInstanceId)
			throws WfBusinessException {
		// try {
		// return this.getEngine().getRuntimeService()
		// .getVariables(processInstanceId);
		// } catch (ActivitiException e) {
		MiniCache cache = new MiniCache();
		return cache.getVariables(processInstanceId);
		// }
	}

	@Override
	public void deleteGlobalVariables(String processInstanceId)
			throws WfBusinessException {
		MiniCache cache = new MiniCache();
		cache.clear(processInstanceId);
	}

	/**
	 * 设置工作流启动人
	 * 
	 * @param variables
	 *            环境变量
	 */
	private void setAuthenticatedSarttPersion(WfParametersVO variables) {
		if (!StringUtil.isEmpty(variables
				.getStringValue(IWorkFlowTypes.operationUser))) {
			this.getEngine()
					.getIdentityService()
					.setAuthenticatedUserId(
							variables
									.getStringValue(IWorkFlowTypes.operationUser));
		}
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

	@Override
	public void addComment(String userId, String processInstanceId,
			String message) throws WfBusinessException {

		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流程实例id不能为空！");
		}

		this.getEngine().getIdentityService().setAuthenticatedUserId(userId);
		this.getEngine().getTaskService()
				.addComment(processInstanceId, null, message);
	}

	@Override
	public void removeComments(String processInstanceId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流程实例id不能为空！");
		}
		this.getEngine().getTaskService()
				.deleteComments(null, processInstanceId);
	}

	@Override
	public void removeComment(String commentId) throws WfBusinessException {
		this.getTaskManageService().removeComment(commentId);
	}

	@Override
	public String addAttachment(String processInstanceId,
			AttachmentVO attachment) throws WfBusinessException {
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}
		if (attachment == null) {
			throw new WfBusinessException("附件不能为空！");
		}

		Attachment retobj = null;
		if (attachment.getContent() != null) {
			retobj = this
					.getEngine()
					.getTaskService()
					.createAttachment(attachment.getAttachmentType(), null,
							processInstanceId, attachment.getAttachmentName(),
							attachment.getAttachmentDescription(),
							attachment.getContent());
		} else {
			retobj = this
					.getEngine()
					.getTaskService()
					.createAttachment(attachment.getAttachmentType(), null,
							processInstanceId, attachment.getAttachmentName(),
							attachment.getAttachmentDescription(),
							attachment.getUrl());
		}
		return retobj.getId();
	}

	@Override
	public void changeAttachment(String attachmentId, String attachmentName,
			String attachmentDescription) throws WfBusinessException {
		this.getTaskManageService().changeAttachment(attachmentId,
				attachmentName, attachmentDescription);
	}

	@Override
	public void deleteAttachment(String processInstanceId, String attachmentName)
			throws WfBusinessException {
		Collection<AttachmentVO> rescols = this.getProInstanceQueryService()
				.queryProcessInstanceAttachmentVOsByAtchName(processInstanceId,
						attachmentName);
		if (rescols != null && rescols.size() > 0) {
			for (AttachmentVO tmpone : rescols) {
				this.deleteAttachment(tmpone.getId());
			}
		}
	}

	@Override
	public void deleteAttachment(String attachmentId)
			throws WfBusinessException {
		this.getTaskManageService().deleteAttachment(attachmentId);
	}
}
