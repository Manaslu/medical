package com.idp.workflow.itf.service.task;

import java.util.Collection;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.service.task.HistoricTaskVO;

/**
 * 历史任务查询服务
 * 
 * @author panfei
 * 
 */
public interface IHistoricTaskQueryService extends InvationCallBacker {

	/**
	 * 按照时间降序查询出所有历史记录
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<HistoricTaskVO> queryHistoricTaskVOs(String processInstanceId)
			throws WfBusinessException;

	/**
	 * 按照时间降序查询出所有历史记录
	 * 
	 * @param processDefinitionCode
	 *            工作流定义编码
	 * @param businessCode
	 *            工作流实例编码
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<HistoricTaskVO> queryHistoricTaskVOs(
			String processDefinitionCode, String businessCode)
			throws WfBusinessException;

	/**
	 * 根据工作流id和任务编码，查询任务vo
	 * 
	 * @param processInstanceId
	 *            工作流实例id
	 * @param TaskDefineCode
	 *            任务编码
	 * @return
	 * @throws WfBusinessException
	 */
	HistoricTaskVO queryHistoricTaskVOByCode(String processInstanceId,
			String TaskDefineCode) throws WfBusinessException;

	/**
	 * 根据工作流定义编码、工作流实例编码和任务编码查询任务VO
	 * 
	 * @param processDefinitionCode
	 *            可为空
	 * @param businessCode
	 *            不能为空
	 * @param TaskDefineCode
	 *            不能为空
	 * @return
	 * @throws WfBusinessException
	 */
	HistoricTaskVO queryHistoricTaskVOByCode(String processDefinitionCode,
			String businessCode, String TaskDefineCode)
			throws WfBusinessException;

	/**
	 * 查询未完成的历史任务
	 * 
	 * @param processInstanceId
	 * @param TaskDefineCode
	 * @return
	 * @throws WfBusinessException
	 */
	HistoricTaskVO queryUnFinishedHistoricTaskVOByCode(
			String processInstanceId, String TaskDefineCode)
			throws WfBusinessException;

	/**
	 * 查询需要退回任务的次数
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws WfBusinessException
	 */
	int queryNeedBackHistoricTaskVOCounts(String processInstanceId)
			throws WfBusinessException;
}
