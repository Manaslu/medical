package com.idp.security.usersmonitor.entity;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.Column;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;
import com.idp.pub.entity.annotation.TimeStamp;
import com.idp.pub.service.entity.IUser;

/**
 * 用户信息
 * 
 * @author panfei
 * 
 */
@MetaTable(name = "T02_USER_ONLINE")
public class UserEntity extends SmartEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2341601242176969592L;

	@PrimaryKey(name = "SESSION_ID", createType = PK.useIDP)
	private String sessonid;

	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "USR_NAME")
	private String userName;

	@TimeStamp(name = "TS")
	private String ts;

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public static UserEntity createNewOne(IUser user) {
		return createNewOne(user, null);
	}

	public static UserEntity createNewOne(IUser user, String sessonid) {
		UserEntity userentity = new UserEntity();
		userentity.setId(user.getId());
		userentity.setUserName(user.getUserName());
		userentity.setLoginName(user.getLogName());
		userentity.setSessonid(sessonid);
		return userentity;
	}

	public String getSessonid() {
		return sessonid;
	}

	public void setSessonid(String sessonid) {
		this.sessonid = sessonid;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
