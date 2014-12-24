package com.idap.dataprocess.rule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-7-7 15:39:21
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class SubRuleScript implements java.io.Serializable {
	private String id; // 主键
	private String columnRuleId; // 字段整合规则流水号
	private Long scriptId; // 脚本代码
	private String ruleId; // 规则代码
	private String colName; // 字段名称
	private Long seqNo; // 顺序号
	private String description; // 描述
	private List<SubScriptPara> subScriptParams = new ArrayList<SubScriptPara>();
	private RuleScript ruleScript; // 规则脚本库

	/** default constructor */
	public SubRuleScript() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnRuleId() {
		return this.columnRuleId;
	}

	public void setColumnRuleId(String columnRuleId) {
		this.columnRuleId = columnRuleId;
	}

	public Long getScriptId() {
		return this.scriptId;
	}

	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public Long getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public List<SubScriptPara> getSubScriptParams() {
		return subScriptParams;
	}

	public void setSubScriptParams(List<SubScriptPara> subScriptParams) {
		this.subScriptParams = subScriptParams;
	}

	public RuleScript getRuleScript() {
		return ruleScript;
	}

	public void setRuleScript(RuleScript ruleScript) {
		this.ruleScript = ruleScript;
	}

}