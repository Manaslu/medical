
package com.idap.clinic.dao;


import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.CommonDrug;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("commonDrugDao")
public class CommonDrugDao extends DefaultBaseDao<CommonDrug,String>{
	@Override
	public String getNamespace() {
		return CommonDrug.class.getName();
		
	}
}
