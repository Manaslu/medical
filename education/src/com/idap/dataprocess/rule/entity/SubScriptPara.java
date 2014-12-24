package com.idap.dataprocess.rule.entity;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-7-7 15:39:20
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class SubScriptPara implements java.io.Serializable {
	private String id; // 参数主键
	private String subRuleScriptId; // 子整合规则主键
	private String columnInteId; //
	private String paraCd; // 参数代码
	private String paraVal; // 参数值
	private Long seqNo; // 顺序号
	private String ruleId; // 规则代码

	/** default constructor */
	public SubScriptPara() {
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubRuleScriptId() {
		return this.subRuleScriptId;
	}

	public void setSubRuleScriptId(String subRuleScriptId) {
		this.subRuleScriptId = subRuleScriptId;
	}

	public String getParaCd() {
		return this.paraCd;
	}

	public void setParaCd(String paraCd) {
		this.paraCd = paraCd;
	}

	public String getParaVal() {
		return this.paraVal;
	}

	public void setParaVal(String paraVal) {
		this.paraVal = paraVal;
	}

	public Long getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public String getColumnInteId() {
		return columnInteId;
	}

	public void setColumnInteId(String columnInteId) {
		this.columnInteId = columnInteId;
	}

}