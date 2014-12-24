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
 * @功能描述：订阅管理权限实体类
 * @修改日志： #####################
 */

@MetaTable
public class Empower extends SmartEntity {

	private static final long serialVersionUID = 9011021954842638773L;

	@PrimaryKey(createType = PK.useIDP)
	private String Id;// 序列ID

	private String subscribeId;// 订阅代码

	private String userId;// 授权人代码

	private String userName;// 授权人名称

	private String departmentId;// 所属部门

	private String subscribeType;// 订阅状态

	private String reportName;// [附加属性]报表名称

	private String subscribeName;// [附加属性]订阅名称

	private String subscribeMethod;// [附加属性]订阅类型

	private String useremail;// [附加属性]用户email

	private Long uid;// [附加属性]用户id

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
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

	public String getSubscribeMethod() {
		return subscribeMethod;
	}

	public void setSubscribeMethod(String subscribeMethod) {
		this.subscribeMethod = subscribeMethod;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
