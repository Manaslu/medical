package com.idp.pub.service.entity;

/**
 * 权限的接口
 * 
 * @author Raoxy
 * 
 */
public interface IAuthority {

	/**
	 * 获取权限的Id
	 * 
	 * @return
	 */
	public Long getId();

	/**
	 * 获取权限的命名空间
	 * 
	 * @return
	 */
	public String getNamespace();

	public Integer getIndexNo();
}
