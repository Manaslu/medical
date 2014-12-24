package com.idap.intextservice.result.subscriptions.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###############################
 * 
 * @创建日期：2014-6-20
 * @开发人员：yao
 * @功能描述：订阅推送记录/日志实体类
 * @修改日志： #####################
 */

@MetaTable
public class EmailPush extends SmartEntity {

	private static final long serialVersionUID = 951656760484903684L;

	@PrimaryKey(createType = PK.useIDP)
	private String Id;// 序列号

	private String pushStats;// 推送状态

	private String reportName;// 报表名称

	private String subsName;// 订阅名称

	private String subsType;// 订阅类型

	private String userName;// 用户名称

	private Date pushDate = new Date();// 创建时间

	private String email;// [附加属性]email

	private String subscribeId;// [附加属性]订阅代码

	private String startTime;// [附加属性]开始时间

	private String endTime;// [附加属性]结束时间

	private String usrName;// [附加属性] 用户名称

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPushStats() {
		return pushStats;
	}

	public void setPushStats(String pushStats) {
		this.pushStats = pushStats;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getSubsName() {
		return subsName;
	}

	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}

	public String getSubsType() {
		return subsType;
	}

	public void setSubsType(String subsType) {
		this.subsType = subsType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getPushDate() {
		return pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
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

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

}
