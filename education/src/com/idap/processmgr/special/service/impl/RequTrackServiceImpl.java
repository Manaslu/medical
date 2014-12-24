package com.idap.processmgr.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.entity.RequTrack;
import com.idap.processmgr.special.service.RequTrackService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("requTrackService")
public class RequTrackServiceImpl extends DefaultBaseService<RequTrack, String> implements
		RequTrackService {
	@Resource(name = "requTrackDao")
	public void setBaseDao(IBaseDao<RequTrack, String> baseDao) {
		super.setBaseDao(baseDao);
	}
}
