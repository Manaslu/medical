package com.idap.intextservice.dataServiceProcess.service;

import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.exception.BusinessException;

public interface DataFlowExecutor {
	/**
	 * 针对数据服务流程管理中手工定义流程定制规则，模拟增加整合规则,
	 * 
	 * @param tableRule
	 *            整合规则
	 * @param processId
	 *            流程规则定制的ID
	 * @param resultId
	 *            结果数据集的 ID
	 * @param indexNo
	 * @return
	 * @throws BusinessException
	 */
	public void execute(String processId) throws BusinessException;

	/**
	 * 
	 * 
	 * @param tableRule
	 * 
	 * @param processId
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public TableInteRule insertTableRule(TableInteRule tableRule,
			String processId, String resultId, Integer indexNo)
			throws BusinessException;

	/**
	 * 针对数据服务流程管理中数据集抽取数据整合规则
	 * 
	 * @param processId
	 *            流程规则定制的ID
	 * @return
	 * @throws BusinessException
	 */
	public String insertTableRule(String processId) throws BusinessException;
}
