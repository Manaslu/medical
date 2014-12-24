package com.idap.intextservice.result.subscriptions.entity;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###############################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅主题报表实体类
 * @修改日志： #####################
 */

@MetaTable
public class ThemeRep extends SmartEntity {

	private static final long serialVersionUID = -3609174224055481079L;

	@PrimaryKey(createType = PK.useIDP)
	private String anaThemeRepId;// 分析主题报表标识

	private String anaThemeId;// 分析主题标识

	private String repName;// 报表名称

	private String repType;// 报表类型

	private String execSql;// 执行SQL

	private String state;// 状态

	private String repDesc;// 报表描述

	public String getAnaThemeRepId() {
		return anaThemeRepId;
	}

	public void setAnaThemeRepId(String anaThemeRepId) {
		this.anaThemeRepId = anaThemeRepId;
	}

	public String getAnaThemeId() {
		return anaThemeId;
	}

	public void setAnaThemeId(String anaThemeId) {
		this.anaThemeId = anaThemeId;
	}

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getRepType() {
		return repType;
	}

	public void setRepType(String repType) {
		this.repType = repType;
	}

	public String getExecSql() {
		return execSql;
	}

	public void setExecSql(String execSql) {
		this.execSql = execSql;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRepDesc() {
		return repDesc;
	}

	public void setRepDesc(String repDesc) {
		this.repDesc = repDesc;
	}

}
