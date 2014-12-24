package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.SyncAuditor;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("syncAuditorDao")
public class SyncAuditorDao extends DefaultBaseDao<SyncAuditor,String>{

	@Override
	public String getNamespace() {
		return SyncAuditor.class.getName();
		
	}
}
