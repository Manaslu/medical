package com.idap.processmgr.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.entity.Fllow;
import com.idap.processmgr.special.service.FllowService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("fllowService")
public class FllowServiceImpl extends DefaultBaseService<Fllow, String> implements FllowService {
	@Resource(name = "fllowDao")
	public void setBaseDao(IBaseDao<Fllow, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "fllowDao")
	public void setPagerDao(IPagerDao<Fllow> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
