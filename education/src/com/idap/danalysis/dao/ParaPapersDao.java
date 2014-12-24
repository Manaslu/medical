package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.ParaPapers;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("paraPapersDao")
public class ParaPapersDao extends DefaultBaseDao<ParaPapers,String>{

	@Override
	public String getNamespace() {
		return ParaPapers.class.getName();
		
	}
}
