package com.idp.workflow.itf.service.identity;

import java.util.Collection;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.identity.IRoleVO;
import com.idp.workflow.vo.identity.IUserGroupVO;
import com.idp.workflow.vo.identity.IUserVO;

/**
 * 访问身份权限查询接口
 * 
 * @author panfei
 * 
 */
public interface IDentityQueryService extends InvationCallBacker {

	/**
	 * 根据用户id查询出对应的用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws WfBusinessException
	 */
	IUserVO queryUserVOById(String userId) throws WfBusinessException;

	/**
	 * 根据用户id查询出对应的角色列表
	 * 
	 * @param userId
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<IRoleVO> queryRoleVOsByUserId(String userId)
			throws WfBusinessException;

	/**
	 * 根据用户id查询出对应的用户组列表
	 * 
	 * @param userId
	 * @return
	 * @throws WfBusinessException
	 */
	Collection<IUserGroupVO> queryUserGroupVOsByUserId(String userId)
			throws WfBusinessException;
}
