package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.SynchronizationApply;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("synchronizationApplyDao")
public class SynchronizationApplyDao extends DefaultBaseDao<SynchronizationApply,String>{

	@Override
	public String getNamespace() {
		return SynchronizationApply.class.getName();
		
	}
}
