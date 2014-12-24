package com.idap.drad.dao;

import org.springframework.stereotype.Repository;
   
import com.idap.drad.entity.EsbRecv;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
* @###################################################
* @功能描述:
* @创建日期:2014-4-21 14:39:43
* @开发人员：bai
* @修改日志：
* @###################################################
*/

@Repository("EsbRecvDao")
public class EsbRecvDao extends DefaultBaseDao<EsbRecv, String> {
	public static final String NAME_SPACE = "com.idap.drad.entity.EsbRecv";

	// com.idap.drad.entity.EsbRecv
	// @Override
	public String getNamespace() {
		return EsbRecv.class.getName();
	}
	/*
	 * public String getNamespace() { return this.NAME_SPACE;
	 * 
	 * }
	 */

}
