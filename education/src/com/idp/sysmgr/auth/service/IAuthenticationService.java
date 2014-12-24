package com.idp.sysmgr.auth.service;

import java.util.List;

import com.idp.pub.service.entity.IUser;
import com.idp.sysmgr.menu.entity.MenuTree;

public interface IAuthenticationService {
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public IUser authentication(String userName, String password)
			throws Exception;

	public List<MenuTree> findUserMenu(String userId);
	
	public List<MenuTree> findRoleMenu(String userId);
}
