package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.Fllow;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("fllowDao")
public class FllowDao extends DefaultBaseDao<Fllow, String>{
	@Override
	public String getNamespace() {
		return Fllow.class.getName();
	}
}
