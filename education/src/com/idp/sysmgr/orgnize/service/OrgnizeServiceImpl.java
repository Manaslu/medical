package com.idp.sysmgr.orgnize.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.entity.Orgnize;

@Service("orgnizeService")
@Transactional
public class OrgnizeServiceImpl extends DefaultBaseService<Orgnize, String> {
	@Resource(name = "orgnizeDao")
	public void setBaseDao(IBaseDao<Orgnize, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "orgnizeDao")
	public void setPagerDao(IPagerDao<Orgnize> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
