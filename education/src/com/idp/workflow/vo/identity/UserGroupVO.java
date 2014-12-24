package com.idp.workflow.vo.identity;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 默认用户组VO
 * 
 * @author panfei
 * 
 */
public class UserGroupVO extends SuperVO implements IUserGroupVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8768939031699754952L;

	public UserGroupVO(String userGroupID, String userGroupName) {
		this(userGroupID, userGroupID, userGroupName);
	}

	public UserGroupVO(String userGroupID, String userGroupCode,
			String userGroupName) {
		this.userGroupID = userGroupID;
		this.userGroupCode = userGroupCode;
		this.userGroupName = userGroupName;
	}

	private String userGroupID;

	public void setUserGroupID(String userGroupID) {
		this.userGroupID = userGroupID;
	}

	@Override
	public String getUserGroupID() {
		return userGroupID;
	}

	private String userGroupCode;

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	@Override
	public String getUserGroupCode() {
		return userGroupCode;
	}

	private String userGroupName;

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Override
	public String getUserGroupName() {
		return userGroupName;
	}

}
