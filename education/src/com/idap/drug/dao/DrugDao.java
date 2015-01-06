package com.idap.drug.dao;

import com.idap.drug.entity.Drug;
import com.idp.pub.dao.impl.DefaultBaseDao;

public class DrugDao extends DefaultBaseDao<Drug, String> {

	@Override
	public String getNamespace() {
		return null;
	}

}
