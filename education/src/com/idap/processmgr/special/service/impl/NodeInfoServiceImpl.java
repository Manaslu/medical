package com.idap.processmgr.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.entity.NodeInfo;
import com.idap.processmgr.special.service.NodeInfoService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("nodeInfoService")
public class NodeInfoServiceImpl extends DefaultBaseService<NodeInfo, String> implements
		NodeInfoService {

	@Resource(name = "nodeInfoDao")
	public void setBaseDao(IBaseDao<NodeInfo, String> baseDao) {
		super.setBaseDao(baseDao);
	}
}
