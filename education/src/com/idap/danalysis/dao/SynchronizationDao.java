package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.Synchronization;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("synchronizationDao")
public class SynchronizationDao extends DefaultBaseDao<Synchronization,String>{

	@Override
	public String getNamespace() {
		return Synchronization.class.getName();
		
	}
}
