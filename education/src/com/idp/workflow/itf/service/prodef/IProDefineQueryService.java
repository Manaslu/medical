package com.idp.workflow.itf.service.prodef;

import java.util.Collection;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.pub.OrderCondition;
import com.idp.workflow.vo.service.prodef.DeploymentVO;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;

/**
 * 
 * 工作流程定义 查询服务
 * 
 * @author panfei
 * 
 */
public interface IProDefineQueryService extends InvationCallBacker {

	/**
	 * 根据流程定义ID查询对应的流程定义VO
	 * 
	 * @param processdefineId
	 *            流程定义ID
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessDefineVO queryProcessDefinitionById(String processdefineId)
			throws WfBusinessException;

	/**
	 * 根据流程定义编码查询对应的流程定义VO
	 * 
	 * @param processDefinitionCode
	 *            流程定义编码
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessDefineVO> queryProcessDefinitionByCode(
			String processDefinitionCode) throws WfBusinessException;

	/**
	 * 根据工作流定义编码查询出对应的工作流
	 * 
	 * @see com.idp.workflow.vo.pub.IWorkFlowTypes 排序方式 降序、升序 输出
	 * @param processdefineCode
	 *            工作流定义编码
	 * @param orderType
	 *            排序方式
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessDefineVO> queryProcessDefinitionByCode(
			String processdefineCode, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据工作流name查询出对应的工作流
	 * 
	 * @param processdefineName
	 *            工作流名称
	 * @param orderType
	 *            排序方式
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<ProcessDefineVO> queryProcessDefinitionByName(
			String processdefineName, OrderCondition orderCondition)
			throws WfBusinessException;

	/**
	 * 根据工作流定义编码查询最新版本的工作流定义VO
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 若为null，标识查询所有流程定义的最新版本
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessDefineVO queryLastProcessDefinitionByCode(
			String processDefinitionCode) throws WfBusinessException;

	/**
	 * 根据工作流定义编码和具体版本查询出具体的流程定义
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码
	 * @param version
	 *            版本号
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessDefineVO queryProcessDefinitionByVersion(
			String processDefinitionCode, Integer version)
			throws WfBusinessException;

	/**
	 * 根据流程定义ID查询对应的部署资源VO
	 * 
	 * @param processdefineId
	 *            流程定义ID
	 * @return
	 * @throws WfBusinessException
	 */
	DeploymentVO queryDeploymentVOById(String processdefineId)
			throws WfBusinessException;

	/**
	 * 根据流程定义编码查询对应的部署资源VO
	 * 
	 * @param processDefinitionCode
	 *            流程定义编码
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<DeploymentVO> queryDeploymentVOByCode(
			String processDefinitionCode) throws WfBusinessException;
}
