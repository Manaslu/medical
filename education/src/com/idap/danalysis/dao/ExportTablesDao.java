package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ExportTables;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("exportTablesDao")
public class ExportTablesDao extends DefaultBaseDao<ExportTables,String>{

	@Override
	public String getNamespace() {
		return ExportTables.class.getName();
		
	}
}
