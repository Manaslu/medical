package com.idap.drad.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.drad.entity.ProvCode;
import com.idap.drad.service.IProvCodeService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-22 9:28:14
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
@Service("provCodeService")
@Transactional
public class ProvCodeServiceImpl extends DefaultBaseService<ProvCode, String>
		implements IProvCodeService {

	@Resource(name = "ProvCodeDao")
	public void setBaseDao(IBaseDao<ProvCode, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "ProvCodeDao")
	public void setPagerDao(IPagerDao<ProvCode> pagerDao) {
		super.setPagerDao(pagerDao);
	}

}
