package com.idap.drad.dao;

import org.springframework.stereotype.Repository;

import com.idap.drad.entity.ProvCode;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述:
 * @创建日期:2014-4-21 14:39:43
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

@Repository("ProvCodeDao")
public class ProvCodeDao extends DefaultBaseDao<ProvCode, String> {
	public static final String NAME_SPACE = "com.idap.drad.entity.ProvCode";

	public String getNamespace() {
		return ProvCode.class.getName();

	}
}
