package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaImportantClient;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraImportantClientDao")
public class ParaImportantClientDao extends DefaultBaseDao<ParaImportantClient,String>{

	@Override
	public String getNamespace() {
		return ParaImportantClient.class.getName();
		
	}
}
