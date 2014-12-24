package com.idap.dataprocess.rule.entity;

/**
 * 数据清洗规则类型
 * <p>
 * CLEAN_RULE("clean"), // 清洗规则
 * 
 * UNIQUE_RULE("unique"), // 排重规则
 * 
 * REBUILD_RULE("rebuild"), // 重构规则
 * 
 * ADDRESS_RULE("address"), // 地址匹配规则
 * 
 * POST_RULE("post"), // 邮编匹配规则
 * 
 * MERGE_RULE("merge");// 合并规则
 * 
 * @author Raoxy
 * 
 */
public enum RuleType {
	CLEAN_RULE("clean", "数据清洗", ""), // 清洗规则

	UNIQUE_RULE("unique", "数据排重", ""), // 排重规则

	REBUILD_RULE("rebuild", "数据重构", ""), // 重构规则

	ADDRESS_RULE("address", "地址匹配", "-1"), // 地址匹配规则

	POST_RULE("post", "邮编匹配", "1"), // 邮编匹配规则

	MERGE_RULE("merge", "数据整合", "");// 整合规则

	private String type;

	private String ruleName;//

	private String value;

	private RuleType(String type, String ruleName, String value) {
		this.type = type;
		this.ruleName = ruleName;
		this.value = value;
	}

	public static RuleType transfer(String type) {
		RuleType ruleType = null;
		for (RuleType t : RuleType.values()) {
			if (t.getType().equals(type)) {
				ruleType = t;
				break;
			}
		}
		return ruleType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
