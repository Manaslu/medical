package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.SyncLog;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("syncLogDao")
public class SyncLogDao extends DefaultBaseDao<SyncLog,String>{

	@Override
	public String getNamespace() {
		return SyncLog.class.getName();
		
	}
}
