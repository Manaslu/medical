package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.Flow;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("flowDao")
public class FlowDao extends DefaultBaseDao<Flow, Long>{
	
	@Override
	public String getNamespace() {
		return Flow.class.getName();
	}
}
