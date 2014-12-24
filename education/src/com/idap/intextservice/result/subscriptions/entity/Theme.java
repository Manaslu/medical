package com.idap.intextservice.result.subscriptions.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###############################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅主题实体类
 * @修改日志： #####################
 */

@MetaTable
public class Theme extends SmartEntity {

	private static final long serialVersionUID = 4054795972693074345L;

	@PrimaryKey(createType = PK.useIDP)
	private String anaThemeId;// 分析主题标识

	private String anaThemeName;// 分析主题名称

	private String anaThemeDesc;// 分析主题描述

	private String dispResourceLink;// 展示资源链接

	private String state;// 状态

	private String createPsn;// 创建人

	private Date createDt = new Date();// 创建时间

	public String getAnaThemeId() {
		return anaThemeId;
	}

	public void setAnaThemeId(String anaThemeId) {
		this.anaThemeId = anaThemeId;
	}

	public String getAnaThemeName() {
		return anaThemeName;
	}

	public void setAnaThemeName(String anaThemeName) {
		this.anaThemeName = anaThemeName;
	}

	public String getAnaThemeDesc() {
		return anaThemeDesc;
	}

	public void setAnaThemeDesc(String anaThemeDesc) {
		this.anaThemeDesc = anaThemeDesc;
	}

	public String getDispResourceLink() {
		return dispResourceLink;
	}

	public void setDispResourceLink(String dispResourceLink) {
		this.dispResourceLink = dispResourceLink;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatePsn() {
		return createPsn;
	}

	public void setCreatePsn(String createPsn) {
		this.createPsn = createPsn;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

}
