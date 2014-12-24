package com.idp.workflow.vo.identity;

import java.io.Serializable;

/**
 * 垂直权限，按业务部门或组织来划分可访问资源
 * 
 * @author panfei
 * 
 */
public interface IUserGroupVO extends Serializable {

	/**
	 * 获取用户组ID
	 * 
	 * @return
	 */
	String getUserGroupID();

	/**
	 * 获取用户组编码
	 * 
	 * @return
	 */
	String getUserGroupCode();

	/**
	 * 获取用户组名称
	 * 
	 * @return
	 */
	String getUserGroupName();
}
