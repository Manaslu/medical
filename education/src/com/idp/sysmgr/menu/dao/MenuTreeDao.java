package com.idp.sysmgr.menu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.menu.entity.MenuTree;

@Repository("menuTreeDao")
public class MenuTreeDao extends DefaultBaseDao<MenuTree, String> {

	@Override
	public String getNamespace() {
		return MenuTree.class.getName();
	}

	public List<MenuTree> findUserMenu(String userId) {
		return this.getSqlSession().selectList("findUserMenu", userId);
	}
	
	public List<MenuTree> findRoleMenu(String userId) {
		return this.getSqlSession().selectList("findRoleMenu", userId);
	}
}
