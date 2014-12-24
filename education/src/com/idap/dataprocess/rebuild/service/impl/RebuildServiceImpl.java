package com.idap.dataprocess.rebuild.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idap.dataprocess.assess.entity.DataSetContour;
import com.idap.dataprocess.dataset.dao.DataSetContentDao;
import com.idap.dataprocess.dataset.dao.DataSetDao;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.definition.dao.CountDao;
import com.idap.dataprocess.rebuild.service.RebuildService;
import com.idap.dataprocess.rule.entity.RuleScript;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * 
 * @创建日期：2014-05-01 12:01:15
 * @开发人员：bai
 * @功能描述：
 * @修改日志： @###################################################
 */
@Service("rebuildService")
@Transactional
public class RebuildServiceImpl extends DefaultBaseService<RuleScript, String>
		implements RebuildService {
	public static final String EXECUTE_REBUILD = "executeRebuild";
	@Resource(name = "dataSetContentDao")
	private DataSetContentDao contentDao;

	//
	@Resource(name = "ruleScriptDao")
	private IBaseDao<RuleScript, String> ruleScriptDao;

	@Resource(name = "ruleScriptDao")
	public void setBaseDao(IBaseDao<RuleScript, String> baseDao) {
		super.setBaseDao(baseDao);
	}// long

	@Resource(name = "dataSetDao")
	private IBaseDao<DataSet, String> dataDao;

	/*
	 * @Resource(name = "dataSetDao") public void setBaseDao(IBaseDao<DataSet,
	 * String> baseDao) { super..setBaseDao(baseDao); }// long
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<RuleScript> query(Map params) {

		// 1.查数据集信息                                     
		//Map dsParams = new HashMap(); 
		return super.findList(params);

	}

	@Override
	public void executeRule(TableInteRule rule) throws BusinessException {
		Assert.notNull(rule, "整合规则对象rule为空");
		Assert.notNull(rule.getRuleId(), "整合规则对象主键Id的rule.ruleid为空");
		Assert.notNull(rule.getRuleType(),
				"整合规则对象规则类型rule.ruleType为空,可选值为['clean','rebuild','unique','merge']");

		throw new RuntimeException("此方法暂未实现!");
	}

	@Override
	public int saveRebuildRule(String dataSetId, Map<String, List> rules)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @创建日期：2014-05-01 12:01:15
	 * @开发人员：bai
	 * @功能描述：执行数据重构的存储过程 写 1、数据集表 2、整合日志表
	 * @param ruleId
	 *            规则ID
	 * @return 返回数据重构主键 成功1 失败0
	 * @throws Exception
	 */
	@Override
	public DataSet executeRebuild(String ruleId) throws BusinessException {
  
		Map params = new HashMap<String, String>();
		params.put("ruleId", ruleId);
		DataSet datavo=   this.dataDao.find(EXECUTE_REBUILD, params).get(0);
		return datavo;    
		   
	};

}
