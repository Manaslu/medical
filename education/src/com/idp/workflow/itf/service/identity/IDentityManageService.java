package com.idp.workflow.itf.service.identity;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.identity.IRoleVO;
import com.idp.workflow.vo.identity.IUserGroupVO;
import com.idp.workflow.vo.identity.IUserVO;

/**
 * 访问身份权限管理接口
 * 
 * @author panfei
 * 
 */
public interface IDentityManageService extends InvationCallBacker {

	/**
	 * 新增一个用户
	 * 
	 * @param userId
	 *            用户id
	 * @throws WfBusinessException
	 */
	public void addNewUser(String userId) throws WfBusinessException;

	/**
	 * 新增一个用户
	 * 
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @throws WfBusinessException
	 */
	public void addNewUser(String userId, String userName)
			throws WfBusinessException;

	/**
	 * 创建新角色
	 * 
	 * @param roleId
	 * @throws WfBusinessException
	 */
	public void addNewRole(String roleId) throws WfBusinessException;

	/**
	 * 创建新角色
	 * 
	 * @param roleId
	 * @param roleName
	 * @throws WfBusinessException
	 */
	public void addNewRole(String roleId, String roleName)
			throws WfBusinessException;

	/**
	 * 创建新用户组
	 * 
	 * @param groupId
	 * @throws WfBusinessException
	 */
	public void addUserGroup(String groupId) throws WfBusinessException;

	/**
	 * 创建新用户组
	 * 
	 * @param groupId
	 * @param groupName
	 * @throws WfBusinessException
	 */
	public void addUserGroup(String groupId, String groupName)
			throws WfBusinessException;

	/**
	 * 更新用户组vo
	 * 
	 * @param uservo
	 * @throws WfBusinessException
	 */
	public void changeUser(IUserVO uservo) throws WfBusinessException;

	/**
	 * 更新角色组
	 * 
	 * @param usergroupvo
	 * @throws WfBusinessException
	 */
	public void changeRole(IRoleVO rolevo) throws WfBusinessException;

	/**
	 * 更新用户组信息
	 * 
	 * @param usergroupvo
	 * @throws WfBusinessException
	 */
	public void changeUserGroup(IUserGroupVO usergroupvo)
			throws WfBusinessException;

	/**
	 * 根据用户id绑定具体用户组
	 * 
	 * @param userId
	 *            用户id
	 * @param groupId
	 *            用户组id
	 * @throws WfBusinessException
	 */
	public void relationUserGroup(String userId, String groupId)
			throws WfBusinessException;

	/**
	 * 根据用户id绑定具体角色
	 * 
	 * @param userId
	 *            用户id
	 * @param roleId
	 *            角色id
	 * @throws WfBusinessException
	 */
	public void relationRole(String userId, String roleId)
			throws WfBusinessException;

	/**
	 * 根据用户组id删除用户组信息
	 * 
	 * @param userGroupId
	 *            用户组id
	 * @throws WfBusinessException
	 */
	public void deleteUserGroup(String userGroupId) throws WfBusinessException;

	/**
	 * 根据角色id删除角色信息
	 * 
	 * @param roleId
	 *            角色id
	 * @throws WfBusinessException
	 */
	public void deleteRole(String roleId) throws WfBusinessException;

	/**
	 * 根据用户id删除用户信息
	 * 
	 * @param userId
	 *            用户id
	 * @throws WfBusinessException
	 */
	public void deleteUser(String userId) throws WfBusinessException;
}
