package com.idap.dataprocess.rule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-22 9:12:44
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class ColumnInteRule implements java.io.Serializable {

	private static final long serialVersionUID = 1001373482302600959L;

	private String id; // 字段整合规则的ID
	private String ruleId; // 表整合规则ID
	private RuleScript ruleScript; // 规则脚本库
	private String colRuleType; // 规则类型
	private String dataSet1Col; // 数据集1的字段
	private String dataSet2Col; // 数据集2的字段
	private String ruleDesc; // 规则描述
	private String resultColumnName; // 结果集的字段名
	private String resultColumnType; // 结果 集的字段类型
	private Long resultColumnLength; // 结果集的字段长度
	private String resultColumnDesc; // 结果集的字段描述
	private Long seqNo; // 顺序号
	private String batchNo; // 匹次号
	private String scriptId; // [附加，用于保存脚本id]
	private String colType; // [附加属性]列类型： 前台传入 addr：地址列 post：邮编列
	private String dataset1GroupColumn; // [附加，保存排重依据的]
	private List<SubRuleScript> subRuleScript = new ArrayList<SubRuleScript>();// 二级规则
	private List<SubScriptPara> scriptParams = new ArrayList<SubScriptPara>();// 一级规则的所有参数
	private String optType; // [附加，保存自定义的操作类型，（头替换尾替换）]
	private String src; // [附加，要替换的字符]
	private String to; // [附加，替换后的字符]
	private String keyword; // [附加，剔除关键字以后字符]
	private String startIndex;// [附加，字符串截取起始位置]
	private String strLength; // [附加，字符串截取长度]
	private String optName; // [附加，（头替换尾替换名称）]

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}

	public String getStrLength() {
		return strLength;
	}

	public void setStrLength(String strLength) {
		this.strLength = strLength;
	}

	public String getDataset1GroupColumn() {
		return dataset1GroupColumn;
	}

	public List<SubScriptPara> getScriptParams() {
		return scriptParams;
	}

	public void setScriptParams(List<SubScriptPara> scriptParams) {
		this.scriptParams = scriptParams;
	}

	public void setDataset1GroupColumn(String dataset1GroupColumn) {
		this.dataset1GroupColumn = dataset1GroupColumn;
	}

	public String getScriptId() {
		return scriptId;
	}

	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

	public String getResultColumnDesc() {
		return resultColumnDesc;
	}

	public void setResultColumnDesc(String resultColumnDesc) {
		this.resultColumnDesc = resultColumnDesc;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	/** default constructor */
	public ColumnInteRule() {
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getColRuleType() {
		return this.colRuleType;
	}

	public void setColRuleType(String colRuleType) {
		this.colRuleType = colRuleType;
	}

	public String getDataSet1Col() {
		return this.dataSet1Col;
	}

	public void setDataSet1Col(String dataSet1Col) {
		this.dataSet1Col = dataSet1Col;
	}

	public String getDataSet2Col() {
		return this.dataSet2Col;
	}

	public void setDataSet2Col(String dataSet2Col) {
		this.dataSet2Col = dataSet2Col;
	}

	public String getRuleDesc() {
		return this.ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getResultColumnName() {
		return this.resultColumnName;
	}

	public void setResultColumnName(String resultColumnName) {
		this.resultColumnName = resultColumnName;
	}

	public String getResultColumnType() {
		return this.resultColumnType;
	}

	public void setResultColumnType(String resultColumnType) {
		this.resultColumnType = resultColumnType;
	}

	public Long getResultColumnLength() {
		return this.resultColumnLength;
	}

	public void setResultColumnLength(Long resultColumnLength) {
		this.resultColumnLength = resultColumnLength;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RuleScript getRuleScript() {
		return ruleScript;
	}

	public void setRuleScript(RuleScript ruleScript) {
		this.ruleScript = ruleScript;
	}

	public List<SubRuleScript> getSubRuleScript() {
		return subRuleScript;
	}

	public void setSubRuleScript(List<SubRuleScript> subRuleScript) {
		this.subRuleScript = subRuleScript;
	}

}