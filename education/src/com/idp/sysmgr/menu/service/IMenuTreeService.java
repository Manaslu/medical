package com.idp.sysmgr.menu.service;

import java.util.List;

import com.idp.sysmgr.menu.entity.MenuTree;


public interface IMenuTreeService {
	public void initMenu(List<MenuTree> menus, String parentId) ;
}
