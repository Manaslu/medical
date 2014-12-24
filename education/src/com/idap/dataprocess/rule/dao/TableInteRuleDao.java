package com.idap.dataprocess.rule.dao;

import org.springframework.stereotype.Repository;

import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * 表整合规则的持久化类
 * 
 * @author Raoxy
 * 
 */
@Repository("tableInteRuleDao")
public class TableInteRuleDao extends DefaultBaseDao<TableInteRule, String> {

	@Override
	public String getNamespace() {
		return TableInteRule.class.getName();
	}

}
