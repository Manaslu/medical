package com.idap.drad.dao;

import org.springframework.stereotype.Repository;

import com.idap.drad.entity.SourceDataDic;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述:
 * @创建日期:2014-4-21 14:39:43
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */

@Repository("SourceDataDicDao")
public class SourceDataDicDao extends DefaultBaseDao<SourceDataDic, String> {
	public String getNamespace() {
		return SourceDataDic.class.getName();

	}
}
