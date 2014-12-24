package com.idap.dataprocess.rule.service.handler.impl;

import org.springframework.stereotype.Service;

import com.idap.dataprocess.rule.entity.RuleType;

@Service("mergeRuleHandler")
public class MergeRuleHandler extends DefaultRuleHanlder {

	@Override
	public RuleType getRuleType() {
		return RuleType.MERGE_RULE;
	}

}
