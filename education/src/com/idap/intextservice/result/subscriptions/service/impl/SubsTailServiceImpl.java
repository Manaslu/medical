package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.SubsTail;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("subsTailService")
public class SubsTailServiceImpl extends DefaultBaseService<SubsTail, String> {

	@Resource(name = "subsTailDao")
	public void setBaseDao(IBaseDao<SubsTail, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "subsTailDao")
	public void setPagerDao(IPagerDao<SubsTail> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
