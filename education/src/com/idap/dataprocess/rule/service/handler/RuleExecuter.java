package com.idap.dataprocess.rule.service.handler;

import com.idap.dataprocess.rule.exception.RuleException;

public interface RuleExecuter {
	public void execute(RuleSetter setter) throws RuleException;
}
