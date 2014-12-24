package com.idp.workflow.vo.identity;

import java.io.Serializable;

/**
 * 水平交叉权限，划分职位等级
 * 
 * @author panfei
 * 
 */
public interface IRoleVO extends Serializable {

	/**
	 * 获取角色ID
	 * 
	 * @return
	 */
	String getRoleID();

	/**
	 * 获取角色编码
	 * 
	 * @return
	 */
	String getRoleCode();

	/**
	 * 获取角色名称
	 * 
	 * @return
	 */
	String getRoleName();
}
