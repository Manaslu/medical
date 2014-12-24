package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.TableColumns;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("tableColumnsDao")
public class TableColumnsDao extends DefaultBaseDao<TableColumns,String>{

	@Override
	public String getNamespace() {
		return TableColumns.class.getName();
		
	}
}
