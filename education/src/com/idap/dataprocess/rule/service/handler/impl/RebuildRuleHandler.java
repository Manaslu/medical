package com.idap.dataprocess.rule.service.handler.impl;

import org.springframework.stereotype.Service;

import com.idap.dataprocess.rule.entity.RuleType;

@Service("rebuildRuleHandler")
public class RebuildRuleHandler extends DefaultRuleHanlder {

	@Override
	public RuleType getRuleType() {
		return RuleType.REBUILD_RULE;
	}

}
