package com.idap.dataprocess.rule.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * 字段整合规则
 * 
 * @author Raoxy
 * 
 */
@Repository("columnInteRuleDao")
public class ColumnInteRuleDao extends DefaultBaseDao<ColumnInteRule, String> {

	@Override
	public String getNamespace() {
		return ColumnInteRule.class.getName();
	}
	
	// 查询用户设置的规则
	public List<ColumnInteRule> getRule(ColumnInteRule id) {
		return get("getRules", id);

	}

	public List<ColumnInteRule> get(String key, ColumnInteRule id) {
		return this.getSqlSession().selectList(sqlKey(key), id);

	}
}
