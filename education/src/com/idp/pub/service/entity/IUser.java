package com.idp.pub.service.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户类实现的接口
 * 
 * @author Raoxy
 * 
 */
public interface IUser extends Serializable {
	/**
	 * 获取客户的Id
	 * 
	 * @return
	 */
	public Long getId();

	/**
	 * 获取用户的登录账号
	 */
	public String getLogName();

	/**
	 * 获取客户的姓名
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * 获取客户的密码
	 * 
	 * @return
	 */
	public String getPassword();

	/**
	 * 功能权限
	 * 
	 * @return
	 */
	public List<IAuthority> getAuthoritys();
}