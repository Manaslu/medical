package com.idap.dataprocess.rule.service.handler;

import java.util.Map;

import com.idap.dataprocess.rule.entity.RuleType;

public class RuleHandlerManager {
	private Map<String, RuleHandler> handlers;

	public RuleHandler handler(RuleType ruleType) {
		RuleHandler ruleHandler = this.handlers.get(ruleType.getType());
		if (ruleHandler == null) {
			throw new RuntimeException("RuleType:[" + ruleType.getType()
					+ "]的处理类不存在!");
		}
		return ruleHandler;
	}

	public Map<String, RuleHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<String, RuleHandler> handlers) {
		this.handlers = handlers;
	}
}
