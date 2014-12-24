package com.idp.workflow.impl.service.activiti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.prodef.IProDefineQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.OrderCondition;
import com.idp.workflow.vo.service.prodef.DeploymentVO;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;

/**
 * 工作流定义查询服务实现
 * 
 * @author panfei
 * 
 */
public class ProDefineQueryServiceImpl extends BaseServiceImpl implements
		IProDefineQueryService {

	public ProDefineQueryServiceImpl() {
		super();
	}

	public ProDefineQueryServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public ProcessDefineVO queryProcessDefinitionById(String processdefineId)
			throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		ProcessDefinition tempdefine = temprpservice
				.createProcessDefinitionQuery()
				.processDefinitionId(processdefineId).singleResult();
		return VOUtil.convertVO(null, tempdefine);
	}

	@Override
	public Collection<ProcessDefineVO> queryProcessDefinitionByCode(
			String processdefineCode, OrderCondition orderCondition)
			throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		ProcessDefinitionQuery prodefquery = temprpservice
				.createProcessDefinitionQuery().processDefinitionKey(
						processdefineCode);
		return orderList(prodefquery, orderCondition);
	}

	@Override
	public Collection<ProcessDefineVO> queryProcessDefinitionByName(
			String processdefineName, OrderCondition orderCondition)
			throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		ProcessDefinitionQuery prodefquery = temprpservice
				.createProcessDefinitionQuery().processDefinitionName(
						processdefineName);
		return orderList(prodefquery, orderCondition);
	}

	/**
	 * 根据传入列名和排序方式排序输出
	 * 
	 * @param defquery
	 * @param orderCondition
	 * @return
	 */
	private Collection<ProcessDefineVO> orderList(
			ProcessDefinitionQuery defquery, OrderCondition orderCondition) {
		ProcessDefinitionQuery temppdefQuery = defquery;
		if (orderCondition != null) {
			String[] orderNames = orderCondition.getOrderNames();
			if (orderNames != null) {
				boolean neverOrder = true;
				for (String orderstr : orderNames) {
					if (StringUtil.isEmpty(orderstr)) {
						continue;
					}

					if (ProcessDefineVO.ID.equals(orderstr)) {
						temppdefQuery = temppdefQuery
								.orderByProcessDefinitionId();
						neverOrder = false;
					}
					if (ProcessDefineVO.CODE.equals(orderstr)) {
						temppdefQuery = temppdefQuery
								.orderByProcessDefinitionKey();
						neverOrder = false;
					}
					if (ProcessDefineVO.VERSION.equals(orderstr)) {
						temppdefQuery = temppdefQuery
								.orderByProcessDefinitionVersion();
						neverOrder = false;
					}
					if (ProcessDefineVO.NAME.equals(orderstr)) {
						temppdefQuery = temppdefQuery
								.orderByProcessDefinitionName();
						neverOrder = false;
					}
				}
				if (!neverOrder) {
					if (IWorkFlowTypes.OrderType.ASC.equals(orderCondition
							.getOrderType())) {
						temppdefQuery.asc();
					} else {
						temppdefQuery.desc();
					}
				}
			}
		}
		List<ProcessDefinition> templist = temppdefQuery.list();
		return VOUtil.convertProcessDefineVOs(templist);
	}

	@Override
	public ProcessDefineVO queryLastProcessDefinitionByCode(
			String processdefineCode) throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		ProcessDefinitionQuery tempdefquery = temprpservice
				.createProcessDefinitionQuery();
		if (!StringUtil.isEmpty(processdefineCode)) {
			tempdefquery = tempdefquery.processDefinitionKey(processdefineCode);
		}

		ProcessDefinition tempdefine = tempdefquery.latestVersion()
				.singleResult();
		return VOUtil.convertVO(null, tempdefine);
	}

	@Override
	public ProcessDefineVO queryProcessDefinitionByVersion(
			String processdefineCode, Integer version)
			throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		/*
		 * List<ProcessDefinition> listprdef = temprpservice
		 * .createProcessDefinitionQuery()
		 * .processDefinitionKey(processdefineCode).list(); if (listprdef !=
		 * null) { for (ProcessDefinition tempvo : listprdef) { if
		 * (Integer.valueOf(tempvo.getVersion()).equals(version)) { return
		 * VOUtil.convertVO(null, tempvo); } } }
		 */
		ProcessDefinition tempdefvp = temprpservice
				.createProcessDefinitionQuery()
				.processDefinitionKey(processdefineCode)
				.processDefinitionVersion(version).singleResult();
		return VOUtil.convertVO(null, tempdefvp);
	}

	@Override
	public DeploymentVO queryDeploymentVOById(String processdefineId)
			throws WfBusinessException {
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		// query prodefinevo
		ProcessDefinition defvo = temprpservice.createProcessDefinitionQuery()
				.processDefinitionId(processdefineId).singleResult();
		// query deployvo
		Deployment delpovo = temprpservice.createDeploymentQuery()
				.deploymentId(defvo.getDeploymentId()).singleResult();
		return VOUtil.convertDeployVO(delpovo, defvo);
	}

	@Override
	public Collection<ProcessDefineVO> queryProcessDefinitionByCode(
			String processDefinitionCode) throws WfBusinessException {
		if (StringUtil.isEmpty(processDefinitionCode)) {
			throw new WfBusinessException("工作流程定义编码不能为空！");
		}
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		List<ProcessDefinition> tempdefine = temprpservice
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionCode)
				.orderByProcessDefinitionVersion().desc().list();
		return VOUtil.convertProcessDefineVOs(tempdefine);
	}

	@Override
	public Collection<DeploymentVO> queryDeploymentVOByCode(
			String processDefinitionCode) throws WfBusinessException {
		if (StringUtil.isEmpty(processDefinitionCode)) {
			throw new WfBusinessException("工作流程定义编码不能为空！");
		}
		RepositoryService temprpservice = this.getEngine()
				.getRepositoryService();
		// query prodefinevo
		Collection<ProcessDefinition> defvos = temprpservice
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionCode)
				.orderByDeploymentId().desc().list();
		List<DeploymentVO> retlist = new ArrayList<DeploymentVO>();

		if (defvos != null) {
			for (ProcessDefinition temp : defvos) {
				// query deployvo
				Deployment delpovo = temprpservice.createDeploymentQuery()
						.deploymentId(temp.getDeploymentId()).singleResult();
				VOUtil.convertDeployVO(delpovo, temp);
			}
		}
		return retlist;
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

}
