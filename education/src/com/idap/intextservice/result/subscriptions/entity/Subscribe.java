package com.idap.intextservice.result.subscriptions.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###############################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理实体类
 * @修改日志： #####################
 */

@MetaTable
public class Subscribe extends SmartEntity {

	private static final long serialVersionUID = 7596616553476934604L;

	@PrimaryKey(createType = PK.useIDP)
	private String subscribeId;// 订阅代码

	private String subscribeName;// 订阅名称

	private String subscribeMethod;// 订阅类型

	private String pushMethod;// 推送方式

	private String subscribeDesc;// 订阅描述

	private String subscribeStats;// 订阅状态

	private String createUser;// 创建人

	private String isDel;// 是否删除 0删除 1 存在

	private Date createDate = new Date();// 创建时间

	private String startTime;// [附加属性]开始时间

	private String endTime;// [附加属性]结束时间

	private String reportName;// [附加属性]报表名称

	private String createName;// [附加属性]创建名称

	private String userId;// [附加属性]用户Id

	private String subscribeType;// [附加属性]订阅状态

	private String anaThemeId;// [附加属性]报表主题

	private String Id;// // [附加属性]报表名称

	private String anaThemeRepId;// [附加属性]报表名称Id

	private String anaThemeName;// [附加属性]报表主题名称

	private String repName;// [附加属性]报表主题表名名称

	private String empowerId;// [附加属性]报表主题表名名称

	public String getAnaThemeRepId() {
		return anaThemeRepId;
	}

	public void setAnaThemeRepId(String anaThemeRepId) {
		this.anaThemeRepId = anaThemeRepId;
	}

	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getSubscribeName() {
		return subscribeName;
	}

	public void setSubscribeName(String subscribeName) {
		this.subscribeName = subscribeName;
	}

	public String getSubscribeMethod() {
		return subscribeMethod;
	}

	public void setSubscribeMethod(String subscribeMethod) {
		this.subscribeMethod = subscribeMethod;
	}

	public String getPushMethod() {
		return pushMethod;
	}

	public void setPushMethod(String pushMethod) {
		this.pushMethod = pushMethod;
	}

	public String getSubscribeDesc() {
		return subscribeDesc;
	}

	public void setSubscribeDesc(String subscribeDesc) {
		this.subscribeDesc = subscribeDesc;
	}

	public String getSubscribeStats() {
		return subscribeStats;
	}

	public void setSubscribeStats(String subscribeStats) {
		this.subscribeStats = subscribeStats;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
	}

	public String getAnaThemeId() {
		return anaThemeId;
	}

	public void setAnaThemeId(String anaThemeId) {
		this.anaThemeId = anaThemeId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getAnaThemeName() {
		return anaThemeName;
	}

	public void setAnaThemeName(String anaThemeName) {
		this.anaThemeName = anaThemeName;
	}

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getEmpowerId() {
		return empowerId;
	}

	public void setEmpowerId(String empowerId) {
		this.empowerId = empowerId;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

}
