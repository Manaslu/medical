package com.idp.workflow.impl.service.activiti;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.proinst.IProInstanceQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.OrderCondition;
import com.idp.workflow.vo.pub.WhereCondition;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.AttachmentVO;
import com.idp.workflow.vo.service.task.CommentVO;

/**
 * 工作流实例查询服务实现
 * 
 * @author panfei
 * 
 */
public class ProInstanceQueryServiceImpl extends BaseServiceImpl implements
		IProInstanceQueryService {

	public ProInstanceQueryServiceImpl() {
		super();
	}

	public ProInstanceQueryServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public ProcessInstanceVO queryProcessInstanceById(String processInstanceId)
			throws WfBusinessException {
		ProcessInstance proinst = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (proinst == null) {
			HistoricProcessInstance hisproinstance = this.getEngine()
					.getHistoryService().createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			ProcessDefineVO prodefvo = this.getProDefineQueryService()
					.queryProcessDefinitionById(
							hisproinstance.getProcessDefinitionId());
			return VOUtil.convertProcessInstanceVO(hisproinstance,
					prodefvo.getCode());
		}
		return VOUtil.convertProcessInstanceVO(proinst);
	}

	@Override
	public Collection<ProcessInstanceVO> queryProcessInstancesById(
			OrderCondition orderCondition, String... processInstanceId)
			throws WfBusinessException {
		Set<String> proids = new HashSet<String>();
		if (processInstanceId != null) {
			for (String tempstr : processInstanceId) {
				proids.add(tempstr);
			}
		} else {
			return null;
		}
		ProcessInstanceQuery proinsquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery().processInstanceIds(proids);
		HistoricProcessInstanceQuery hisproinstance = this.getEngine()
				.getHistoryService().createHistoricProcessInstanceQuery()
				.processInstanceIds(proids);
		return orderConditiion(proinsquery, hisproinstance, orderCondition);
	}

	/**
	 * 按条件排序结果
	 * 
	 * @param proinsquery
	 * @param orderCondition
	 * @return
	 * @throws WfBusinessException
	 */
	private List<ProcessInstanceVO> orderConditiion(
			ProcessInstanceQuery proinsquery,
			HistoricProcessInstanceQuery hisproinsquery,
			OrderCondition orderCondition) throws WfBusinessException {
		if (proinsquery == null) {
			return null;
		}
		ProcessInstanceQuery tempquery = proinsquery;
		if (orderCondition != null) {
			String[] orderNames = orderCondition.getOrderNames();
			if (orderNames != null) {
				boolean neverOrder = true;
				for (String orderstr : orderNames) {
					if (StringUtil.isEmpty(orderstr)) {
						continue;
					}

					if (ProcessInstanceVO.ID.equals(orderstr)) {
						tempquery.orderByProcessInstanceId();
						if (hisproinsquery != null) {
							hisproinsquery.orderByProcessInstanceId();
						}
						neverOrder = false;
					}

					if (ProcessInstanceVO.PROCESSDEFINITIONID.equals(orderstr)) {
						tempquery.orderByProcessDefinitionId();
						if (hisproinsquery != null) {
							hisproinsquery.orderByProcessDefinitionId();
						}
						neverOrder = false;
					}

					if (ProcessInstanceVO.PROCESSDEFINITIONCODE
							.equals(orderstr)) {
						tempquery.orderByProcessDefinitionKey();
						neverOrder = false;
					}

					if (!neverOrder) {
						if (IWorkFlowTypes.OrderType.ASC.equals(orderCondition
								.getOrderType())) {
							tempquery.asc();
							if (hisproinsquery != null) {
								hisproinsquery.asc();
							}
						} else {
							tempquery.desc();
							if (hisproinsquery != null) {
								hisproinsquery.desc();
							}
						}
					}
				}
			}
		}
		List<ProcessInstanceVO> retproList = VOUtil
				.convertProcessInstanceVOs(tempquery.list());
		if (hisproinsquery != null) {
			List<ProcessInstanceVO> retprohisList = VOUtil
					.convertProcessInstanceVOs(hisproinsquery.list(), null);
			List<ProcessInstanceVO> meragelist = this.merageRuselt(
					retprohisList, retproList);
			return meragelist;
		} else {
			return retproList;
		}
	}

	/**
	 * 根据当前历史实例结果和存在活动的实例结果 合并成最终结果
	 * 
	 * @param retprohisList
	 * @param retproList
	 * @return
	 */
	private List<ProcessInstanceVO> merageRuselt(
			List<ProcessInstanceVO> retprohisList,
			List<ProcessInstanceVO> retproList) {
		List<ProcessInstanceVO> retlist = new ArrayList<ProcessInstanceVO>();
		if (retprohisList != null) {
			if (retproList == null) {
				return retprohisList;
			}
			point: for (ProcessInstanceVO temp : retprohisList) {
				for (ProcessInstanceVO find : retproList) {
					if (find.getId().equals(temp.getId())) {
						retlist.add(find);
						break point;
					}
				}
				retlist.add(temp);
			}
		}
		return retlist;
	}

	@Override
	public Collection<ProcessInstanceVO> queryProcessInstancesByBusiCode(
			String businessCode, OrderCondition orderCondition)
			throws WfBusinessException {
		return queryProcessInstancesByBusiCode(null, businessCode,
				orderCondition);
	}

	@Override
	public Collection<ProcessInstanceVO> queryProcessInstancesByBusiCode(
			String processDefinitionCode, String businessCode,
			OrderCondition orderCondition) throws WfBusinessException {
		return queryProcessInstancesByBusiCode(processDefinitionCode,
				businessCode, null, orderCondition);
	}

	@Override
	public Collection<ProcessInstanceVO> queryProcessInstancesByBusiCode(
			String processDefinitionCode, String businessCode,
			WhereCondition whereCondition, OrderCondition orderCondition)
			throws WfBusinessException {

		ProcessInstanceQuery tempquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery();
		HistoricProcessInstanceQuery hisproinstance = this.getEngine()
				.getHistoryService().createHistoricProcessInstanceQuery();

		if (!StringUtil.isEmpty(businessCode)) {
			tempquery.processInstanceBusinessKey(businessCode);
			hisproinstance.processInstanceBusinessKey(businessCode);
		}

		if (!StringUtil.isEmpty(processDefinitionCode)) {
			tempquery.processDefinitionKey(processDefinitionCode);
			hisproinstance.processDefinitionKey(processDefinitionCode);
		}

		if (whereCondition != null) {
			if (whereCondition.equal(ProcessInstanceVO.SUSPEND_STATE, true)) {
				tempquery.suspended();
			} else {
				tempquery.active();
			}
		}
		return orderConditiion(tempquery, hisproinstance, orderCondition);
	}

	@Override
	public Collection<ProcessInstanceVO> queryProcessInstancesByDefId(
			String processDefinitionId, OrderCondition orderCondition)
			throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		ProcessDefinition tempdefine = temprpservice
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		if (tempdefine == null) {
			return null;
		}
		return queryProcessInstancesByBusiCode(tempdefine.getKey(), null,
				orderCondition);
	}

	@Override
	public Collection<ProcessInstanceVO> querysuperProcessInstanceById(
			String processInstanceId, OrderCondition orderCondition)
			throws WfBusinessException {
		if (processInstanceId == null) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}
		ProcessInstanceQuery proinsquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.subProcessInstanceId(processInstanceId);
		return orderConditiion(proinsquery, null, orderCondition);
	}

	@Override
	public Collection<ProcessInstanceVO> querysubProcessInstanceById(
			String processInstanceId, OrderCondition orderCondition)
			throws WfBusinessException {
		if (processInstanceId == null) {
			throw new WfBusinessException("工作流实例id不能为空！");
		}
		ProcessInstanceQuery proinsquery = this.getEngine().getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.superProcessInstanceId(processInstanceId);
		HistoricProcessInstanceQuery hisproinstance = this.getEngine()
				.getHistoryService().createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.superProcessInstanceId(processInstanceId);
		return orderConditiion(proinsquery, hisproinstance, orderCondition);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

	@Override
	public Collection<CommentVO> queryProcessInstanceCommentVOsById(
			String processInstanceId) throws WfBusinessException {
		List<Comment> comments = this.getEngine().getTaskService()
				.getProcessInstanceComments(processInstanceId);
		return VOUtil.convertCommentVOs(comments);
	}

	@Override
	public Collection<AttachmentVO> queryProcessInstanceAttachmentVOsByAtchName(
			String processInstanceId, String attachmentName)
			throws WfBusinessException {
		List<AttachmentVO> attlist = new ArrayList<AttachmentVO>();
		Collection<AttachmentVO> rescols = this
				.queryProcessInstanceAttachmentVOsById(processInstanceId);
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
	public Collection<AttachmentVO> queryProcessInstanceAttachmentVOsById(
			String processInstanceId) throws WfBusinessException {
		List<Attachment> reslist = this.getEngine().getTaskService()
				.getProcessInstanceAttachments(processInstanceId);
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
	public AttachmentVO queryProcessInstanceAttachmentVOsByAtchId(
			String attachmentId) throws WfBusinessException {
		return this.getTaskQueryService().queryTaskAttachmentVOsByAtchId(
				attachmentId);
	}

}
