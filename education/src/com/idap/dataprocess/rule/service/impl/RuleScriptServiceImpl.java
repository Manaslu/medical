package com.idap.dataprocess.rule.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.rule.entity.RuleScript;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("ruleScriptService")
@Transactional
public class RuleScriptServiceImpl extends DefaultBaseService<RuleScript, Long> {
	@Resource(name = "ruleScriptDao")
	public void setBaseDao(IBaseDao<RuleScript, Long> baseDao) {
		super.setBaseDao(baseDao);
	}
}
