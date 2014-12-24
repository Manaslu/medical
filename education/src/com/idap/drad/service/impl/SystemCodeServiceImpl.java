package com.idap.drad.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.drad.entity.SystemCode;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("systemCodeService")
public class SystemCodeServiceImpl extends DefaultBaseService<SystemCode, String> {
	@Resource(name = "sytemCodeDao")
	public void setBaseDao(IBaseDao<SystemCode, String> baseDao) {
		super.setBaseDao(baseDao);
	}
}
