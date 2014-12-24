package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaDefinitionBigClient;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraDefinitionBigClientDao")
public class ParaDefinitionBigClientDao extends DefaultBaseDao<ParaDefinitionBigClient,String>{

	@Override
	public String getNamespace() {
		return ParaDefinitionBigClient.class.getName();
		
	}
}
