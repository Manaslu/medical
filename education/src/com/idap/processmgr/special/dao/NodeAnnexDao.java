package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.NodeAnnex;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("nodeAnnexDao")
public class NodeAnnexDao extends DefaultBaseDao<NodeAnnex, String> {

	@Override
	public String getNamespace() {
		return NodeAnnex.class.getName();
	}
}