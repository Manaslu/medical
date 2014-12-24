package com.idap.dataprocess.definition.dao;

import org.springframework.stereotype.Repository;

import com.idap.dataprocess.definition.entity.DataTypeDic;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：数据类型
 * @创建日期：2014-4-17 10:55:56
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataTypeDicDao")
public class DataTypeDicDao extends DefaultBaseDao<DataTypeDic, String> {
	public static final String NAME_SPACE = DataTypeDic.class.getName();

	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}

}
