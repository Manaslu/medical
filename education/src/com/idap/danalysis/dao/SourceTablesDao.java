package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.SourceTables;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("sourceTablesDao")
public class SourceTablesDao extends DefaultBaseDao<SourceTables,String>{

	@Override
	public String getNamespace() {
		return SourceTables.class.getName();
		
	}
}
