package com.idap.dataprocess.rule.service.handler.impl;

import org.springframework.stereotype.Service;

import com.idap.dataprocess.rule.entity.RuleType;

@Service("cleanRuleHandler")
public class CleanRuleHandler extends DefaultRuleHanlder {

	public RuleType getRuleType() {
		return RuleType.CLEAN_RULE;
	}

}
