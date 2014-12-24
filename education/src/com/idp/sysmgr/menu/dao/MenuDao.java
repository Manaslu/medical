package com.idp.sysmgr.menu.dao;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.menu.entity.Menu;


@Repository("menuDao")
public class MenuDao extends DefaultBaseDao<Menu, Long>{

	public String getNamespace() {
		return Menu.class.getName();
	}
}
