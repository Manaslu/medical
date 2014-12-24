package com.idap.drad.dao;

import org.springframework.stereotype.Repository;

import com.idap.drad.entity.SourceDataDicColumn;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期�?014-4-4 16:39:06
 * @�?��人员：BAI
 * @功能描述�?
 * @修改日志�? ###################################################
 */

@Repository("SourceDataDicColumnDao")
public class SourceDataDicColumnDao extends
		DefaultBaseDao<SourceDataDicColumn, String> {

	public String getNamespace() {
		return SourceDataDicColumn.class.getName();

	}
}
