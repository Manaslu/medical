package com.idap.dataprocess.clean.entity;

import com.idap.dataprocess.rule.entity.RuleScript;

/**
 * @###################################################
 * @功能描述：数据清洗报告
 * @创建日期：2014-5-28 15:48:26
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class DataCleanReport implements java.io.Serializable {
	private String id; //
	private Long scriptId; //
	private String datasetColumnName; //
	private Long handleNum; //
	private Long succNum; //
	private String ruleName; //
	private String ruleId; //
	private String startTime;// [附加属性]开始时间
	private String endTime;// [附加属性]结束时间
	private Long sonScriptId;
	private String paramter;
	private RuleScript ruleScript; // 规则脚本库
	private String batchNo; // 匹次号

	/** default constructor */
	public DataCleanReport() {
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public float getRate() {
		return succNum.floatValue() / handleNum.floatValue();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getScriptId() {
		return this.scriptId;
	}

	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}

	public String getDatasetColumnName() {
		return this.datasetColumnName;
	}

	public void setDatasetColumnName(String datasetColumnName) {
		this.datasetColumnName = datasetColumnName;
	}

	public Long getHandleNum() {
		return this.handleNum;
	}

	public void setHandleNum(Long handleNum) {
		this.handleNum = handleNum;
	}

	public Long getSuccNum() {
		return this.succNum;
	}

	public void setSuccNum(Long succNum) {
		this.succNum = succNum;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getSonScriptId() {
		return sonScriptId;
	}

	public void setSonScriptId(Long sonScriptId) {
		this.sonScriptId = sonScriptId;
	}

	public String getParamter() {
		return paramter;
	}

	public void setParamter(String paramter) {
		this.paramter = paramter;
	}

	public RuleScript getRuleScript() {
		return ruleScript;
	}

	public void setRuleScript(RuleScript ruleScript) {
		this.ruleScript = ruleScript;
	}
}