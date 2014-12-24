package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.drad.entity.DataDownload;
import com.idap.drad.service.DataDownloadService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-21 14:39:43
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
@Transactional
@Service("dataDownloadService")
public class DataDownloadServiceImpl extends
		DefaultBaseService<DataDownload, String> implements DataDownloadService {

	@Resource(name = "DataDownloadDao")
	public void setBaseDao(IBaseDao<DataDownload, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "DataDownloadDao")
	public void setPagerDao(IPagerDao<DataDownload> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<DataDownload> query(
			Pager<com.idap.drad.entity.DataDownload> pager,
			Map<String, Object> params) {
		return this.findByPager(pager, params);

	}

	@Override
	public DataDownload save(DataDownload entity) {
		return null;
	}

}
