package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.RequTrack;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("requTrackDao")
public class RequTrackDao extends DefaultBaseDao<RequTrack, String> {
	@Override
	public String getNamespace() {
		return RequTrack.class.getName();
	}

}
