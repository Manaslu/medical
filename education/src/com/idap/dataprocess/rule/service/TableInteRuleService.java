package com.idap.dataprocess.rule.service;

import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.exception.RuleException;
import com.idp.pub.exception.BusinessException;

public interface TableInteRuleService {
	/**
	 * 针对数据加工的添加数据整合规则
	 * <p>
	 * 1. 先添加字段与规则脚本的对应关系;
	 * <p>
	 * 2. 之后再添加整合日志的相应信息，包括生成结果数据集的ID
	 * <p>
	 * 3. 然后再插入表整合规则的相关信息
	 * 
	 * @param tableRule
	 *            整合规则
	 * @throws BusinessException
	 */
	public TableInteRule insertTableRule(TableInteRule tableRule)
			throws BusinessException;

	public TableInteRule updateTableRule(TableInteRule tableRule)
			throws BusinessException;

	/**
	 * 
	 * @param ruleId
	 * @throws BusinessException
	 */
	public void deleteTableRule(String ruleId) throws BusinessException;

	/**
	 * 执行相应的规则,调用后台的存储过程
	 * 
	 * @param ruleId
	 *            ,ruleType
	 * @throws BusinessException
	 */
	public void executeRule(TableInteRule rule) throws RuleException;

	public void executeRule(String ruleId) throws RuleException;
}
