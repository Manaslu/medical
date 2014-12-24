package com.idap.dataprocess.rule.service.handler;

import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.exception.RuleException;

/**
 * 数据整合规则的处理类
 * 
 * @author Raoxy
 * 
 */
public interface RuleHandler {

	public RuleType getRuleType();

	public void setRuleId(String ruleId);

	public void execute() throws RuleException;
}
