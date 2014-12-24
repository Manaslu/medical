package com.idap.processmgr.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.entity.NodeAnnex;
import com.idap.processmgr.special.service.NodeAnnexService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("nodeAnnexService")
public class NodeAnnexServiceImpl extends DefaultBaseService<NodeAnnex, String> implements
		NodeAnnexService {
	@Resource(name = "nodeAnnexDao")
	public void setBaseDao(IBaseDao<NodeAnnex, String> baseDao) {
		super.setBaseDao(baseDao);
	}
}
