package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.Report;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("reportService")
public class ReportServiceImpl extends DefaultBaseService<Report, String> {

	@Resource(name = "reportDao")
	public void setBaseDao(IBaseDao<Report, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "reportDao")
	public void setPagerDao(IPagerDao<Report> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
