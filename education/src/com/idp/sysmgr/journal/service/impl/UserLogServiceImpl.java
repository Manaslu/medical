package com.idp.sysmgr.journal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.journal.entity.UserLog;

/**
 * @创建日期：2014-5-12
 * @开发人员：huhanjiang
 * @功能描述：用户操作日志
 * @修改日志：
 */
@Service("userLogService")
@Transactional
public class UserLogServiceImpl extends DefaultBaseService<UserLog, Long> {

	@Resource(name = "userLogDao")
	public void setBaseDao(IBaseDao<UserLog, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "userLogDao")
	public void setPagerDao(IPagerDao<UserLog> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
