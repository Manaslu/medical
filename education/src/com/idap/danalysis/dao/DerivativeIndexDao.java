package com.idap.danalysis.dao;

import org.springframework.stereotype.Repository;

import com.idap.danalysis.entity.DerivativeIndex;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("dervativeIndexDao")
public class DerivativeIndexDao extends DefaultBaseDao<DerivativeIndex,String>{

	@Override
	public String getNamespace() {
		return DerivativeIndex.class.getName();
		
	}
}
