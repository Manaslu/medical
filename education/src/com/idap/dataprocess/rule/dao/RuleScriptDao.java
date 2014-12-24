package com.idap.dataprocess.rule.dao;

import org.springframework.stereotype.Repository;

import com.idap.dataprocess.rule.entity.RuleScript;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * 规则脚本库的持久化类
 * 
 * @author Raoxy
 * 
 */
@Repository("ruleScriptDao")
public class RuleScriptDao extends DefaultBaseDao<RuleScript, String> {

	@Override
	public String getNamespace() {
		return RuleScript.class.getName();
	}

}
