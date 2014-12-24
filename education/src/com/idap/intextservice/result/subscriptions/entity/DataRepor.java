package com.idap.intextservice.result.subscriptions.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###############################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅主题实体类
 * @修改日志： #####################
 */

public class DataRepor extends SmartEntity {

	private static final long serialVersionUID = 1242605214467401561L;

	@PrimaryKey(createType = PK.useIDP)
	private String reportId;// 报表代码

	private String reportName;// 报表名称

	private String reportDesc;// 报表描述

	private String reportTheme;// 所属主题

	private String businessType;// 所属业务类型

	private String tableName;// 表名

	private String createUser;// 创建人

	private String sourceDataSetId;// 源数据代码

	private String anaMethod;// 分析方法

	private Date createDate = new Date();// 创建日期

	private Date businessDate = new Date();// 业务日期

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public String getReportTheme() {
		return reportTheme;
	}

	public void setReportTheme(String reportTheme) {
		this.reportTheme = reportTheme;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getSourceDataSetId() {
		return sourceDataSetId;
	}

	public void setSourceDataSetId(String sourceDataSetId) {
		this.sourceDataSetId = sourceDataSetId;
	}

	public String getAnaMethod() {
		return anaMethod;
	}

	public void setAnaMethod(String anaMethod) {
		this.anaMethod = anaMethod;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

}
