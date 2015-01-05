package com.idp.workflow.impl.service.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.transaction.annotation.Transactional;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.IDentityManageService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.identity.IRoleVO;
import com.idp.workflow.vo.identity.IUserGroupVO;
import com.idp.workflow.vo.identity.IUserVO;
import com.idp.workflow.vo.pub.IWorkFlowTypes;
 
/**
 * 
 * @author panfei
 * 
 */
@Transactional
public class DentityManageServiceImpl extends BaseServiceImpl implements
		IDentityManageService {

	public DentityManageServiceImpl() {
		super();
	}

	public DentityManageServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public void addNewUser(String userId) throws WfBusinessException {
		this.addNewUser(userId, null);
	}

	@Override
	public void addNewUser(String userId, String userName)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		User newvo = this.getEngine().getIdentityService().newUser(userId);
		newvo.setFirstName(userName);
		newvo.setLastName(userName);
		this.getEngine().getIdentityService().saveUser(newvo);
	}

	@Override
	public void addNewRole(String roleId) throws WfBusinessException {
		this.addNewRole(roleId, null);
	}

	@Override
	public void addNewRole(String roleId, String roleName)
			throws WfBusinessException {
		if (StringUtil.isEmpty(roleId)) {
			throw new WfBusinessException("角色id不能为空！");
		}
		Group newvo = this.getEngine().getIdentityService().newGroup(roleId);
		newvo.setName(roleName);
		newvo.setType(IWorkFlowTypes.OrgType_Role);
		this.getEngine().getIdentityService().saveGroup(newvo);
	}

	@Override
	public void addUserGroup(String groupId) throws WfBusinessException {
		this.addUserGroup(groupId, null);
	}

	@Override
	public void addUserGroup(String groupId, String groupName)
			throws WfBusinessException {
		if (StringUtil.isEmpty(groupId)) {
			throw new WfBusinessException("用户组id不能为空！");
		}
		Group newvo = this.getEngine().getIdentityService().newGroup(groupId);
		newvo.setName(groupName);
		newvo.setType(IWorkFlowTypes.OrgType_UserGroup);
		this.getEngine().getIdentityService().saveGroup(newvo);
	}

	@Override
	public void relationUserGroup(String userId, String groupId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		if (StringUtil.isEmpty(groupId)) {
			throw new WfBusinessException("用户组id不能为空！");
		}
		this.getEngine().getIdentityService().createMembership(userId, groupId);
	}

	@Override
	public void relationRole(String userId, String roleId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		if (StringUtil.isEmpty(roleId)) {
			throw new WfBusinessException("角色id不能为空！");
		}
		this.getEngine().getIdentityService().createMembership(userId, roleId);
	}

	@Override
	public void deleteUserGroup(String userGroupId) throws WfBusinessException {
		if (StringUtil.isEmpty(userGroupId)) {
			throw new WfBusinessException("用户组id不能为空！");
		}
		List<Group> list = this.getEngine().getIdentityService()
				.createGroupQuery().groupId(userGroupId).list();
		if (list != null && list.size() > 0) {
			this.getEngine().getIdentityService().deleteGroup(userGroupId);
		}
	}

	@Override
	public void deleteRole(String roleId) throws WfBusinessException {

		if (StringUtil.isEmpty(roleId)) {
			throw new WfBusinessException("角色id不能为空！");
		}
		List<Group> list = this.getEngine().getIdentityService()
				.createGroupQuery().groupId(roleId).list();
		if (list != null && list.size() > 0) {
			this.getEngine().getIdentityService().deleteGroup(roleId);
		}
	}

	@Override
	public void deleteUser(String userId) throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		this.getEngine().getIdentityService().deleteUser(userId);
		List<Group> retlist = this.getEngine().getIdentityService()
				.createGroupQuery().groupMember(userId).list();
		if (retlist != null) {
			for (Group temp : retlist) {
				this.getEngine().getIdentityService()
						.deleteMembership(userId, temp.getId());
			}
		}
	}

	@Override
	public void changeUser(IUserVO uservo) throws WfBusinessException {
		User updatevo = this.getEngine().getIdentityService().createUserQuery()
				.userId(uservo.getUserID()).singleResult();
		updatevo.setFirstName(uservo.getUserName());
		updatevo.setLastName(uservo.getUserName());
		this.getEngine().getIdentityService().saveUser(updatevo);
	}

	@Override
	public void changeRole(IRoleVO rolevo) throws WfBusinessException {
		Group groupvo = this.getEngine().getIdentityService()
				.createGroupQuery().groupType(IWorkFlowTypes.OrgType_Role)
				.groupId(rolevo.getRoleID()).singleResult();
		groupvo.setName(rolevo.getRoleName());
		this.getEngine().getIdentityService().saveGroup(groupvo);
	}

	@Override
	public void changeUserGroup(IUserGroupVO usergroupvo)
			throws WfBusinessException {
		Group groupvo = this.getEngine().getIdentityService()
				.createGroupQuery().groupType(IWorkFlowTypes.OrgType_UserGroup)
				.groupId(usergroupvo.getUserGroupID()).singleResult();
		groupvo.setName(usergroupvo.getUserGroupName());
		this.getEngine().getIdentityService().saveGroup(groupvo);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}
}
