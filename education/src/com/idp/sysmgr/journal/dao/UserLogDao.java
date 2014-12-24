package com.idp.sysmgr.journal.dao;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.journal.entity.UserLog;

/**
 * #############################
 * 
 * @创建日期：2014-5-12
 * @开发人员：huhanjiang
 * @功能描述：用户操作日志dao
 * @修改日志： #############################
 */
@Repository("userLogDao")
public class UserLogDao extends DefaultBaseDao<UserLog, Long> {

	public String getNamespace() {
		return UserLog.class.getName();
	}
}
