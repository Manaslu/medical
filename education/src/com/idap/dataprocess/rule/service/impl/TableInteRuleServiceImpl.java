package com.idap.dataprocess.rule.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.entity.DataLogState;
import com.idap.dataprocess.rule.entity.SubRuleScript;
import com.idap.dataprocess.rule.entity.SubScriptPara;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.exception.RuleException;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idap.dataprocess.rule.service.handler.RuleExecuter;
import com.idap.dataprocess.rule.service.handler.RuleSetter;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("tableInteRuleService")
@Transactional
public class TableInteRuleServiceImpl extends DefaultBaseService<TableInteRule, String> implements TableInteRuleService {

	@Resource(name = "tableInteRuleDao")
	public void setBaseDao(IBaseDao<TableInteRule, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "columnInteRuleDao")
	private IBaseDao<ColumnInteRule, String> columnInteRuleDao;

	@Resource(name = "subRuleScriptDao")
	private IBaseDao<SubRuleScript, String> subRuleScriptDao;

	@Resource(name = "subScriptParaDao")
	private IBaseDao<SubScriptPara, String> subScriptParaDao;

	@Resource(name = "ruleExecuter")
	private RuleExecuter ruleExecuter;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	@Override
	public TableInteRule insertTableRule(TableInteRule tableRule) throws BusinessException {
		Assert.notNull(tableRule, "表数据整合规则不允许为空");
		// 1. 生成整合规则的主键Id
		String ruleId = this.buildId("");

		// 2.新插入整合规则对应的字段
		for (ColumnInteRule rule : tableRule.getColumnRules()) {
			rule.setRuleId(ruleId);
			rule.setId(buildId(""));
			this.columnInteRuleDao.save(rule);
			insertAllChildRules(rule);
		}

		// 3.增加整合日志
		tableRule.setRuleId(ruleId);
		this.insertDataInteLog(tableRule, ruleId, DataLogState.LOG_INIT, DataInteLog.PROCESS_TYPE_MANUAL);

		// 4.新插入表整合规则功能
		this.save(tableRule);

		return tableRule;
	}

	/**
	 * 主键生成
	 * 
	 * @param params
	 * @return
	 */
	private String buildId(String params) {
		return generateKeyService.getNextGeneratedKey(params).getNextKey();
	}

	/**
	 * 插入所有的子规则以及对应的参数数据
	 * 
	 * @param rule
	 * @throws BusinessException
	 */
	private void insertAllChildRules(ColumnInteRule rule) throws BusinessException {
		// 一级规则级参数
		for(SubScriptPara param:rule.getScriptParams()){
			param.setId(buildId(""));
			param.setColumnInteId(rule.getId());
			param.setRuleId(rule.getRuleId());
			subScriptParaDao.save(param);
		}
		// 二级规则级参数
		for (SubRuleScript chdRule : rule.getSubRuleScript()) {
			chdRule.setId(buildId(""));
			chdRule.setRuleId(rule.getRuleId());
			chdRule.setColumnRuleId(rule.getId());
			subRuleScriptDao.save(chdRule);
			for (SubScriptPara ruleParm : chdRule.getSubScriptParams()) {
				ruleParm.setId(buildId(""));
				ruleParm.setSubRuleScriptId(chdRule.getId());
				ruleParm.setRuleId(rule.getRuleId());
				subScriptParaDao.save(ruleParm);
			}
		}
	}

	@Override
	public TableInteRule updateTableRule(TableInteRule tableRule) throws BusinessException {
		throw new RuntimeException("此方法还未实现，如若需要，请联系我[raoxiaoyan]");
	}

	@Resource(name = "dataInteLogDao")
	private IBaseDao<DataInteLog, String> dataInteLog;

	private DataInteLog insertDataInteLog(TableInteRule tableRule, String ruleId, DataLogState state, String processType)
			throws BusinessException {
		DataInteLog log = new DataInteLog();

		log.setId(this.buildId(""));
		log.setRuleId(ruleId);
		log.setSourceDataSet1(tableRule.getDataSet1());
		log.setSourceDataSet2(tableRule.getDataSet2());
		// 2.1 创建一个虚拟结果数据集
		DataSet resultSet = new DataSet();
		resultSet.setDataSetId(this.buildId(""));
		log.setOpUser(tableRule.getCreateUser());
		log.setRuleType(tableRule.getRuleType());
		log.setResultDataSet(resultSet);
		log.setOpStats(state.getState());
		log.setProcessType(processType);
		this.dataInteLog.save(log);

		return log;// 返回结果数据集的ID
	}

	@Override
	public void executeRule(final TableInteRule rule) throws RuleException {
		Assert.notNull(rule, "整合规则对象rule为空");
		Assert.notNull(rule.getRuleId(), "整合规则对象主键Id的rule.ruleid为空");
		Assert.notNull(rule.getRuleType(), "整合规则对象规则类型rule.ruleType为空,可选值为['clean','rebuild','unique','merge']");
		try {
			ruleExecuter.execute(new RuleSetter() {
				@Override
				public String getRuleId() {
					return rule.getRuleId();
				}

				@Override
				public String getRuleType() {
					return rule.getRuleType();
				}
			});
		} catch (RuleException e) {
			//李广彬：2014-7-11 新加入【不管是java出错 还是存储过程 将整合日志状态更新为失败状态】
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("ruleId", rule.getRuleId());
			List<DataInteLog> items=this.dataInteLog.find(params);
			if(items.size()>0){
				DataInteLog dil=items.get(0);
				dil.setOpStats(DataLogState.LOG_FAILURE.getState());
				this.dataInteLog.update(dil);
			}
			throw e;
		}
	}

	@Override
	public void executeRule(String ruleId) throws RuleException {
		TableInteRule rule = this.getById(ruleId);
		this.executeRule(rule);
	}

	@Override
	public void deleteTableRule(String ruleId) throws BusinessException {
		Assert.notNull(ruleId, "表整合规则ruleId不允许为空");
		Map<String, Object> params = Constants.MAP();
		params.put("ruleId", ruleId);
		// 1. 删除整合日志
		this.dataInteLog.delete(params);
		// 2. 删除整合字段相关规则
		this.columnInteRuleDao.delete(params);
		// 3. 删除表整合规则
		this.delete(params);
	}
}
