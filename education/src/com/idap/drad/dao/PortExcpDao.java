package com.idap.drad.dao;

import org.springframework.stereotype.Repository;

import com.idap.drad.entity.PortExcp;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述:异常监控DAO
 * @创建日期:2014-4-21 14:39:43
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

@Repository("PortExcpDao")
public class PortExcpDao extends DefaultBaseDao<PortExcp, String> {

	public String getNamespace() {
		return PortExcp.class.getName();

	}
}
