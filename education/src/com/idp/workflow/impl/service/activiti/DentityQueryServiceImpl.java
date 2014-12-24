package com.idp.workflow.impl.service.activiti;

import java.util.Collection;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.IDentityQueryService;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.util.vo.activiti.VOUtil;
import com.idp.workflow.vo.identity.IRoleVO;
import com.idp.workflow.vo.identity.IUserGroupVO;
import com.idp.workflow.vo.identity.IUserVO;
import com.idp.workflow.vo.pub.IWorkFlowTypes;

/**
 * 
 * @author panfei
 * 
 */
public class DentityQueryServiceImpl extends BaseServiceImpl implements
		IDentityQueryService {

	public DentityQueryServiceImpl() {
		super();
	}

	public DentityQueryServiceImpl(ProcessEngine engine) {
		super(engine);
	}

	@Override
	public IUserVO queryUserVOById(String userId) throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		User newvo = this.getEngine().getIdentityService().createUserQuery()
				.userId(userId).singleResult();
		return VOUtil.convertUserVO(newvo);
	}

	@Override
	public Collection<IRoleVO> queryRoleVOsByUserId(String userId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		List<Group> retlist = this.getEngine().getIdentityService()
				.createGroupQuery().groupMember(userId)
				.groupType(IWorkFlowTypes.OrgType_Role).orderByGroupId().desc()
				.list();

		return VOUtil.convertRoleVOs(retlist);
	}

	@Override
	public Collection<IUserGroupVO> queryUserGroupVOsByUserId(String userId)
			throws WfBusinessException {
		if (StringUtil.isEmpty(userId)) {
			throw new WfBusinessException("用户id不能为空！");
		}
		List<Group> retlist = this.getEngine().getIdentityService()
				.createGroupQuery().groupMember(userId)
				.groupType(IWorkFlowTypes.OrgType_UserGroup).orderByGroupId()
				.desc().list();
		return VOUtil.convertUserGroupVOs(retlist);
	}

	@Override
	public void setProcessEngine(Object engine) {
		this.setEngine((ProcessEngine) engine);
	}

}
