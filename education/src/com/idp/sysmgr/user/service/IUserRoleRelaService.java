package com.idp.sysmgr.user.service;

import java.util.Map;

import com.idp.pub.entity.Pager;
import com.idp.sysmgr.user.entity.UserRoleRela;

public interface IUserRoleRelaService {

	
	public UserRoleRela getById (long id) ;
	  
	public Pager<UserRoleRela> query(Pager<UserRoleRela> pager,
			Map<String, Object> params);

}
