package com.idap.intextservice.result.subscriptions.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.result.subscriptions.entity.SubsTail;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("subsTailDao")
public class SubsTailDao extends DefaultBaseDao<SubsTail, String> {
	public String getNamespace() {
		return SubsTail.class.getName();
	}
}
