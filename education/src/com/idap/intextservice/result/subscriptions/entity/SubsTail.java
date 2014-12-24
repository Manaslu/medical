package com.idap.intextservice.result.subscriptions.entity;

import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

@MetaTable
public class SubsTail extends SmartEntity {
	private static final long serialVersionUID = -23450335017065531L;

	@PrimaryKey(createType = PK.useIDP)
	private String id;// 序列号

	private String reportName;// 报表名称

	private String subscribeName;// 订阅名称

	private String subscribeType;// 订阅类型

	private String subscribeState;// 订阅状态

	private String userName;// 用户名称

	private Date pushDate = new Date();;// 退订日期

	private String startTime;// [附加属性]开始时间

	private String endTime;// [附加属性]结束时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getSubscribeName() {
		return subscribeName;
	}

	public void setSubscribeName(String subscribeName) {
		this.subscribeName = subscribeName;
	}

	public String getSubscribeState() {
		return subscribeState;
	}

	public void setSubscribeState(String subscribeState) {
		this.subscribeState = subscribeState;
	}

	public String getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
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

}
