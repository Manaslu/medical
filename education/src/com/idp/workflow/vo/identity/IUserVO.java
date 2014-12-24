package com.idp.workflow.vo.identity;

import java.io.Serializable;

/**
 * 用户信息接口 权限最终受益人
 * 
 * 用户组和角色的不同在于“1个楼有2层，每层都有经理办公室，秘书办公室，文员办公室。层：用户组 室：角色”
 * 
 * @author panfei
 */
public interface IUserVO extends Serializable {

	/**
	 * 用户id唯一表示
	 * 
	 * @return
	 */
	String getUserID();

	/**
	 * 用户编码
	 * 
	 * @return
	 */
	String getPassword();

	/**
	 * 用户名称
	 * 
	 * @return
	 */
	String getUserName();

	/**
	 * 获取对应的角色组，一个用户可以对应多个角色
	 * 
	 * @return
	 */
	// Collection<IRoleVO> getRoleVOs();

	/**
	 * 获取对应的用户组，本来一个用户只能对应一个用户组，考虑中国国情
	 * 
	 * @return
	 */
	// Collection<IUserGroupVO> getUserGroupVOs();
}
