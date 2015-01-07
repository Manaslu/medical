package com.idap.clinic.dao;

import org.springframework.stereotype.Repository;
import com.idap.clinic.entity.IllnessType;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2015-1-7
 * @开发人员：wang
 * @功能描述：疾病类别
 * @修改日志： ###################################################
 */

@Repository("illnessTypeDao")
public class IllnessTypeDao extends DefaultBaseDao<IllnessType, String> {
	public String getNamespace() {
		return IllnessType.class.getName();
	}
}
