package com.idap.drad.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.drad.entity.EsbDownload;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("esbDownloadService")
public class EsbDownloadServiceImpl extends
		DefaultBaseService<EsbDownload, String> {
	@Resource(name = "esbDownloadDao")
	public void setPagerDao(IPagerDao<EsbDownload> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
