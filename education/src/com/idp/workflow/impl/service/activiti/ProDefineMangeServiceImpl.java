package com.idp.workflow.impl.service.activiti;

import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.prodef.IProDefineMangeService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.vo.activiti.IConst;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.service.prodef.DeploymentVO;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;
 
/**
 * 工作流定义服务实现
 * 
 * @author panfei
 * 
 */
@Transactional
public class ProDefineMangeServiceImpl extends BaseServiceImpl implements
		IProDefineMangeService {

	public ProDefineMangeServiceImpl() {
		super();
	}

	public ProDefineMangeServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public ProcessDefineVO createProcessDefinition(String deployName,
			String... classPathResources) throws WfBusinessException {
		DeploymentBuilder deploybuilder = this.getEngine()
				.getRepositoryService().createDeployment().name(deployName);
		if (classPathResources != null && classPathResources.length > 0) {
			for (String classPathResource : classPathResources) {
				deploybuilder.addClasspathResource(classPathResource);
			}
			// 部署信息
			Deployment deployvo = deploybuilder.deploy();
			// 流程定义信息
			ProcessDefinition prodefine = this.getEngine()
					.getRepositoryService().createProcessDefinitionQuery()
					.deploymentId(deployvo.getId()).singleResult();
			return VOUtil.convertVO(deployvo, prodefine);
		}
		return null;
	}

	@Override
	public ProcessDefineVO createProcessDefinition(String deployName,
			ZipInputStream zipInputStream) throws WfBusinessException {
		DeploymentBuilder deploybuilder = this.getEngine()
				.getRepositoryService().createDeployment().name(deployName);
		if (zipInputStream != null) {
			deploybuilder.addZipInputStream(zipInputStream);
			// 部署信息
			Deployment deployvo = deploybuilder.deploy();
			// 流程定义信息
			ProcessDefinition prodefine = this.getEngine()
					.getRepositoryService().createProcessDefinitionQuery()
					.deploymentId(deployvo.getId()).singleResult();
			return VOUtil.convertVO(deployvo, prodefine);
		}
		return null;
	}

	@Override
	public ProcessDefineVO createProcessDefinition(DeploymentVO needdelpyVO)
			throws WfBusinessException {
		if (needdelpyVO != null) {
			DeploymentBuilder deploybuilder = this.getEngine()
					.getRepositoryService().createDeployment()
					.name(needdelpyVO.getName());
			if (needdelpyVO.getImageResource() != null
					&& needdelpyVO.getConfigXmlResource() != null) {
				StringBuilder configxmlName = new StringBuilder();
				configxmlName.append(needdelpyVO.getName());
				configxmlName.append(IConst.Res_Name);
				deploybuilder.addInputStream(configxmlName.toString(),
						needdelpyVO.getConfigXmlResource());

				StringBuilder picName = new StringBuilder();
				configxmlName.append(needdelpyVO.getName());
				configxmlName.append(IConst.Pic_Name);
				deploybuilder.addInputStream(picName.toString(),
						needdelpyVO.getImageResource());
				// 部署信息
				Deployment deployvo = deploybuilder.deploy();
				needdelpyVO.setId(deployvo.getId());
				// 流程定义信息
				ProcessDefinition prodefine = this.getEngine()
						.getRepositoryService().createProcessDefinitionQuery()
						.deploymentId(deployvo.getId()).singleResult();
				return VOUtil.convertVO(deployvo, prodefine);
			}
		}
		return null;
	}

	@Override
	public void deleteProcessDefinition(String processdefineId)
			throws WfBusinessException {
		RepositoryService resourceService = this.getEngine()
				.getRepositoryService();
		String deployid = resourceService.createProcessDefinitionQuery()
				.processDefinitionId(processdefineId).singleResult()
				.getDeploymentId();
		resourceService.deleteDeployment(deployid);
	}

	@Override
	public void suspendProcessDefinition(String processdefineId)
			throws WfBusinessException {
		RepositoryService resourceService = this.getEngine()
				.getRepositoryService();
		resourceService.suspendProcessDefinitionById(processdefineId);
	}

	@Override
	public void activateProcessDefinition(String processdefineId)
			throws WfBusinessException {
		RepositoryService resourceService = this.getEngine()
				.getRepositoryService();
		resourceService.activateProcessDefinitionById(processdefineId);
	}

	@Override
	public ProcessDefineVO createProcessDefinition(String deployName,
			String xmlStr) throws WfBusinessException {
		if (!StringUtil.isEmpty(xmlStr)) {
			DeploymentBuilder deploybuilder = this.getEngine()
					.getRepositoryService().createDeployment().name(deployName);
			StringBuilder configxmlName = new StringBuilder();
			configxmlName.append(deployName);
			configxmlName.append(IConst.Res_Name);
			deploybuilder.addString(configxmlName.toString(), xmlStr);
			// 部署信息
			Deployment deployvo = deploybuilder.deploy();
			// 流程定义信息
			ProcessDefinition prodefine = this.getEngine()
					.getRepositoryService().createProcessDefinitionQuery()
					.deploymentId(deployvo.getId()).singleResult();
			return VOUtil.convertVO(deployvo, prodefine);
		} else {
			throw new WfBusinessException("保存流程不能为空！");
		}
	}

	@Override
	public void deleteProcessDefinitionByCode(String processDefinitionCode)
			throws WfBusinessException {
		if (StringUtil.isEmpty(processDefinitionCode)) {
			throw new WfBusinessException("工作流程定义编码不能为空！");
		}
		RepositoryService resourceService = this.getEngine()
				.getRepositoryService();
		String deployid = resourceService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionCode).singleResult()
				.getDeploymentId();
		resourceService.deleteDeployment(deployid);
	}

	@Override
	public void suspendProcessDefinitionByCode(String processDefinitionCode)
			throws WfBusinessException {
		if (StringUtil.isEmpty(processDefinitionCode)) {
			throw new WfBusinessException("工作流程定义编码不能为空！");
		}
		RepositoryService resourceService = this.getEngine()
				.getRepositoryService();
		resourceService.suspendProcessDefinitionByKey(processDefinitionCode);
	}

	@Override
	public void activateProcessDefinitionByCode(String processDefinitionCode)
			throws WfBusinessException {
		if (StringUtil.isEmpty(processDefinitionCode)) {
			throw new WfBusinessException("工作流程定义编码不能为空！");
		}
		RepositoryService resourceService = this.getEngine()
				.getRepositoryService();
		resourceService.suspendProcessDefinitionByKey(processDefinitionCode);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}
}
