package com.idap.dataprocess.rule.service.handler.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.exception.RuleException;
import com.idap.dataprocess.rule.service.handler.RuleExecuter;
import com.idap.dataprocess.rule.service.handler.RuleHandler;
import com.idap.dataprocess.rule.service.handler.RuleHandlerManager;
import com.idap.dataprocess.rule.service.handler.RuleSetter;

@Service("ruleExecuter")
public class RuleExecuterService implements RuleExecuter {

	@Resource(name = "ruleHandlerManager")
	private RuleHandlerManager ruleHandlerManager;

	@Override
	public void execute(RuleSetter setter) throws RuleException {
		RuleHandler ruleHandler = ruleHandlerManager.handler(RuleType
				.transfer(setter.getRuleType()));
		ruleHandler.setRuleId(setter.getRuleId());
		ruleHandler.execute();
	}
}
