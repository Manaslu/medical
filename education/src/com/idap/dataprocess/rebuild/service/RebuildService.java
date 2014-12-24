package com.idap.dataprocess.rebuild.service;

import java.util.List;
import java.util.Map;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.rule.entity.RuleScript;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.exception.BusinessException;

/**
 * ###################################################
 * 
 * @创建日期：2014-04-01 12:01:15
 * @开发人员：bai
 * @功能描述：
 * @修改日志： ###################################################
 */
public interface RebuildService {
	// 保存重构设置

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：bai
	 * @功能描述：
	 */
	// 1.数据集查询 -》走数据集DataSetService

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：bai
	 * @功能描述：
	 */
	// 2.重构规则查询 -》走 RuleScriptLibService

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：bai
	 * @功能描述：保存或更新整合日志
	 */
	// 4.查询整合日志 -》走 DataInteLogService

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：bai
	 * @功能描述：保存清洗规则
	 * @param rules
	 *            数据集重构规则组 例{“去除数据”,["姓名", "性别"]}
	 * @param dataSetId
	 *            数据集ID
	 * @return
	 * @throws Exception
	 */
	public int saveRebuildRule(String dataSetId, Map<String, List> rules)
			throws BusinessException;

	 
	public DataSet executeRebuild(String ruleId) throws BusinessException;

	public List<RuleScript> query(Map params) throws BusinessException;

	/**
	 * 执行相应的规则,调用后台的存储过程
	 * 
	 * @param rule
	 * @throws BusinessException
	 */
	public void executeRule(TableInteRule rule) throws BusinessException;
}
