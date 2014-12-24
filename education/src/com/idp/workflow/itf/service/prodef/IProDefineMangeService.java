package com.idp.workflow.itf.service.prodef;

import java.util.zip.ZipInputStream;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.service.prodef.DeploymentVO;
import com.idp.workflow.vo.service.prodef.ProcessDefineVO;

/**
 * 流程定义服务接口
 * 
 * @author panfei
 * 
 */
public interface IProDefineMangeService extends InvationCallBacker {

	/**
	 * 发布工作流定义资源文件
	 * 
	 * @param deployName
	 *            工作流定义资源名称
	 * @param classPathResource
	 *            发布资源classpath路径
	 * @return 部署VO
	 * @throws WfBusinessException
	 */
	ProcessDefineVO createProcessDefinition(String deployName,
			String... classPathResources) throws WfBusinessException;

	/**
	 * 发布工作流定义资源文件
	 * 
	 * @param deployName
	 *            工作流定义资源名称
	 * @param zipInputStream
	 *            压缩流 bar文件
	 * @return 部署VO
	 * @throws WfBusinessException
	 */
	ProcessDefineVO createProcessDefinition(String deployName,
			ZipInputStream zipInputStream) throws WfBusinessException;

	/**
	 * 发布工作流定义资源文件
	 * 
	 * @param needdelpyVO
	 *            传入 工作流定义资源名称、图片资源、配置文件资源
	 * @return 部署VO
	 * @throws WfBusinessException
	 */
	ProcessDefineVO createProcessDefinition(DeploymentVO needdelpyVO)
			throws WfBusinessException;

	/**
	 * 发布工作流定义资源文件
	 * 
	 * @param deployName
	 *            工作流定义资源名称
	 * @param xmlStr
	 *            流程定义文件的字符串格式
	 * @return
	 * @throws WfBusinessException
	 */
	ProcessDefineVO createProcessDefinition(String deployName, String xmlStr)
			throws WfBusinessException;

	/**
	 * 删除 工作流定义资源
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 不能为空
	 * @throws WfBusinessException
	 */
	public void deleteProcessDefinitionByCode(String processDefinitionCode)
			throws WfBusinessException;

	/**
	 * 删除 工作流定义资源
	 * 
	 * @param processdefineId
	 *            工作流定义ID
	 * @throws WfBusinessException
	 */
	public void deleteProcessDefinition(String processdefineId)
			throws WfBusinessException;

	/**
	 * 停止不启用 工作流定义资源
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 不能为空
	 * @throws WfBusinessException
	 */
	void suspendProcessDefinitionByCode(String processDefinitionCode)
			throws WfBusinessException;

	/**
	 * 停止不启用 工作流定义资源
	 * 
	 * @param processdefineId
	 *            工作流定义ID
	 * @throws WfBusinessException
	 */
	void suspendProcessDefinition(String processdefineId)
			throws WfBusinessException;

	/**
	 * 启用 工作流定义资源
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码 不能为空
	 * @throws WfBusinessException
	 */
	void activateProcessDefinitionByCode(String processDefinitionCode)
			throws WfBusinessException;

	/**
	 * 启用 工作流定义资源
	 * 
	 * @param processdefineId
	 *            工作流定义ID
	 * @throws WfBusinessException
	 */
	void activateProcessDefinition(String processdefineId)
			throws WfBusinessException;
}
