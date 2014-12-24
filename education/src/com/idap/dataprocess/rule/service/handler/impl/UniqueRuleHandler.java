package com.idap.dataprocess.rule.service.handler.impl;

import org.springframework.stereotype.Service;

import com.idap.dataprocess.rule.entity.RuleType;

@Service("uniqueRuleHandler")
public class UniqueRuleHandler extends DefaultRuleHanlder {

	public RuleType getRuleType() {
		return RuleType.UNIQUE_RULE;
	}
}
