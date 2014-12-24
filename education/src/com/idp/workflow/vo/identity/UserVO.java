package com.idp.workflow.vo.identity;

import java.util.Collection;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 由于期望达到平台无关性,工作流内部传递USERVO对象，而非基础用户的实际VO
 * 
 * @author panfei
 * 
 */
public class UserVO extends SuperVO implements IUserVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8682468080169991500L;

	/**
	 * 用户名 用户真实姓名
	 * 
	 * @param userID
	 * @param userName
	 */
	public UserVO(String userID, String userName) {
		this(userID, null, userName);
	}

	/**
	 * 
	 * 用户ID必须输入 用户编码 用户名称选输
	 * 
	 * @param userID
	 * @param userCode
	 * @param userName
	 */
	public UserVO(String userID, String password, String userName) {
		this.userID = userID;
		this.password = password;
		this.userName = userName;
	}

	private String userID;

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String getUserID() {
		return userID;
	}

	private String userName;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	private Collection<IRoleVO> roleVOs;

	public void setRoleVOs(Collection<IRoleVO> roleVOs) {
		this.roleVOs = roleVOs;
	}

	public Collection<IRoleVO> getRoleVOs() {
		return roleVOs;
	}

	private Collection<IUserGroupVO> userGroupVOs;

	public void setUserGroupVOs(Collection<IUserGroupVO> userGroupVOs) {
		this.userGroupVOs = userGroupVOs;
	}

	public Collection<IUserGroupVO> getUserGroupVOs() {
		return userGroupVOs;
	}

	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

}
