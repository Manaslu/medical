package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.DemandAvailable;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("demandAvailableDao")
public class DemandAvailableDao extends DefaultBaseDao<DemandAvailable,String>{

	@Override
	public String getNamespace() {
		return DemandAvailable.class.getName();
		
	}
}
