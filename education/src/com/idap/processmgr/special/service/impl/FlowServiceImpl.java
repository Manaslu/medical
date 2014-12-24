package com.idap.processmgr.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.entity.Flow;
import com.idap.processmgr.special.service.FlowService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("flowService")
public class FlowServiceImpl extends DefaultBaseService<Flow, Long> implements FlowService {
	@Resource(name = "flowDao")
	public void setBaseDao(IBaseDao<Flow, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

}
