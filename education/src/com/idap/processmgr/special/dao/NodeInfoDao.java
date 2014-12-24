package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.NodeInfo;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("nodeInfoDao")
public class NodeInfoDao extends DefaultBaseDao<NodeInfo, String> {
	@Override
	public String getNamespace() {
		return NodeInfo.class.getName();
	}

}
