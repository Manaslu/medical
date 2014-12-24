package com.idap.dataprocess.rule.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idp.pub.utils.DateUtil;

/**
 * 
 * @author Raoxy
 * 
 */
public class TableInteRule implements java.io.Serializable {

	private static final long serialVersionUID = -3310593198822095148L;

	private String ruleId; // 规则代码

	private String ruleType; // 规则类型

	private String ruleDesc; // 规则描述

	private String createUser = "1"; // 创建人

	private Date createDate = new Date(); //

	private DataSet dataSet1; // 数据集1

	// 临时属性,未直接级联查询出该对象,不允许级联查询出来
	private DataInteLog inteLog;// 整合日志

	private DataSet dataSet2; //

	private DataSet firstSet;

	private String linkRule; //

	private String dataset1GroupColumn; //

	private String dataset2GroupColumn; //

	private String resultDataSetName; //

	private List<ColumnInteRule> columnRules = new ArrayList<ColumnInteRule>();

	/** default constructor */
	public TableInteRule() {
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleType() {
		return this.ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleDesc() {
		return this.ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDateStr() {
		return this.createDate != null ? DateUtil
				.dateTimeToStrDefault(this.createDate) : "";
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public DataSet getDataSet1() {
		return this.dataSet1;
	}

	public void setDataSet1(DataSet dataSet1) {
		this.dataSet1 = dataSet1;
	}

	public DataSet getDataSet2() {
		return this.dataSet2;
	}

	public void setDataSet2(DataSet dataSet2) {
		this.dataSet2 = dataSet2;
	}

	public String getLinkRule() {
		return this.linkRule;
	}

	public void setLinkRule(String linkRule) {
		this.linkRule = linkRule;
	}

	public String getDataset1GroupColumn() {
		return this.dataset1GroupColumn;
	}

	public void setDataset1GroupColumn(String dataset1GroupColumn) {
		this.dataset1GroupColumn = dataset1GroupColumn;
	}

	public String getDataset2GroupColumn() {
		return this.dataset2GroupColumn;
	}

	public void setDataset2GroupColumn(String dataset2GroupColumn) {
		this.dataset2GroupColumn = dataset2GroupColumn;
	}

	public String getResultDataSetName() {
		return this.resultDataSetName;
	}

	public void setResultDataSetName(String resultDataSetName) {
		this.resultDataSetName = resultDataSetName;
	}

	public List<ColumnInteRule> getColumnRules() {
		return columnRules;
	}

	public void setColumnRules(List<ColumnInteRule> columnRules) {
		this.columnRules = columnRules;
	}

	public DataSet getFirstSet() {
		return firstSet;
	}

	public void setFirstSet(DataSet firstSet) {
		this.firstSet = firstSet;
	}

	public DataInteLog getInteLog() {
		return inteLog;
	}

	public void setInteLog(DataInteLog inteLog) {
		this.inteLog = inteLog;
	}

}