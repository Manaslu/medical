package com.idap.drug.dao;

import org.springframework.stereotype.Repository;

import com.idap.drug.entity.Drug;
import com.idp.pub.dao.impl.DefaultBaseDao;
@Repository("drugDao")
public class DrugDao extends DefaultBaseDao<Drug, String> {

	@Override
	public String getNamespace() {
		return Drug.class.getName();
	}

}
