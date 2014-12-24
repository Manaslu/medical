package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.SourceIndexs;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("sourceIndexsDao")
public class SourceIndexsDao extends DefaultBaseDao<SourceIndexs,String>{

	@Override
	public String getNamespace() {
		return SourceIndexs.class.getName();
		
	}
}
