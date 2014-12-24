package com.idap.dataprocess.rule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.idap.dataprocess.rule.dao.ColumnInteRuleDao;
import com.idap.dataprocess.rule.entity.ColumnInteRule; 
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("columnInteRuleService")
public class ColumnInteRuleServiceImpl extends	DefaultBaseService<ColumnInteRule, Long> {

	@Resource(name = "columnInteRuleDao")
	public void setBaseDao(IBaseDao<ColumnInteRule, Long> baseDao) {
		super.setBaseDao(baseDao);
	}
	
                                                                                                                                 
	@Resource(name = "columnInteRuleDao")
	public void setPagerDao(IPagerDao<ColumnInteRule> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	@Resource(name = "columnInteRuleDao")
	private ColumnInteRuleDao columnInteRuleDao;

	
	// 查询用户设置的规则
	public List<ColumnInteRule> getRules(ColumnInteRule id) {
		Assert.notNull(columnInteRuleDao, "必须设置基础接口IBaseDao的实现类");
		return this.columnInteRuleDao.getRule(id);
	}

}
