package com.idap.drad.dao;

import org.springframework.stereotype.Repository;

import com.idap.drad.entity.EsbDownload;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("esbDownloadDao")
public class EsbDownloadDao extends DefaultBaseDao<EsbDownload, String> {
	@Override
	public String getNamespace() {
		return EsbDownload.class.getName();
	}

}
