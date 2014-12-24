package com.idp.sysmgr.orgnize.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.entity.Orgnize;

@Repository("orgnizeDao")
public class OrgnizeDao extends DefaultBaseDao<Orgnize, Long> {

	@Override
	public String getNamespace() {
		return Orgnize.class.getName();
	}
}
