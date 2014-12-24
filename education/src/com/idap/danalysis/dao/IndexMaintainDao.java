package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.IndexMaintain;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("indexMaintainDao")
public class IndexMaintainDao extends DefaultBaseDao<IndexMaintain,String>{

	@Override
	public String getNamespace() {
		return IndexMaintain.class.getName();
		
	}
}
