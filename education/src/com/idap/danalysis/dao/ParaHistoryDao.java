package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaHistory;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraHistoryDao")
public class ParaHistoryDao extends DefaultBaseDao<ParaHistory,String>{

	@Override
	public String getNamespace() {
		return ParaHistory.class.getName();
		
	}
}
