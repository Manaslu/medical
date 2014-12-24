package com.idap.intextservice.result.subscriptions.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.result.subscriptions.entity.DataRepor;
import com.idap.intextservice.result.subscriptions.service.IDataReporService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅主题实现类
 * @修改日志： ###################################################
 */

@Service("dataReporService")
public class DataReporServiceImpl extends DefaultBaseService<DataRepor, Long>
		implements IDataReporService {

	@Resource(name = "dataReporDao")
	public void setBaseDao(IBaseDao<DataRepor, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "dataReporDao")
	public void setPagerDao(IPagerDao<DataRepor> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
