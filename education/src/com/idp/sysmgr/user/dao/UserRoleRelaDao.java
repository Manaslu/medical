package com.idp.sysmgr.user.dao;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.user.entity.UserRoleRela;

/**
 * #############################
 * 
 * @创建日期：2014-5-13 17:25:42
 * @开发人员：huhanjiang
 * @功能描述：用户角色关系dao
 * @修改日志： #############################
 */

@Repository("userRoleRelaDao")
public class UserRoleRelaDao extends DefaultBaseDao<UserRoleRela, Long>{
	public String getNamespace() {
		return UserRoleRela.class.getName();
	}
}
