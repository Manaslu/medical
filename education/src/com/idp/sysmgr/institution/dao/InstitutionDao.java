package com.idp.sysmgr.institution.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.institution.entity.Institution;
import com.idp.sysmgr.institution.entity.InstitutionTree;

/**
 * 
 * @创建日期：2014-5-08 16:36:42
 * @开发人员：hu
 * @功能描述：机构管理DAO
 * 
 */

@Repository("institutionDao")
public class InstitutionDao extends DefaultBaseDao<Institution, Long> {

	public String getNamespace() {
		return Institution.class.getName();
	}

	// 是否在当前及下级机构
	public List<Institution> ifLowerLevelorgcd(HashMap  map) {
		return get("ifLowerLevelorgcd", map);

	}

	public List<Institution> get(String key, HashMap  map ) {
		return this.getSqlSession().selectList(sqlKey(key), map);

	}
	

}
