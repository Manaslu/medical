package com.idap.drug.service.impl;






import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.idap.drug.entity.Drug;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;
@Repository("drugService")
public class DrugServiceImpl extends DefaultBaseService<Drug, String> {

	@Override
	@Resource(name="drugDao")
	public void setBaseDao(IBaseDao<Drug, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	@Resource(name="drugDao")
	public void setPagerDao(IPagerDao<Drug> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	

}
