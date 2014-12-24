package com.idp.sysmgr.institution.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.institution.entity.InstitutionTree;

/**
 * 
 * @创建日期：2014-5-14
 * @开发人员：huhanjiang
 * @功能描述：机构树dao
 *
 */

@Repository("institutionTreeDao")
public class InstitutionTreeDao extends DefaultBaseDao<InstitutionTree, String> {

	@Override
	public String getNamespace() {
		return InstitutionTree.class.getName();
	}
	
	//查询下级机构
	public List<InstitutionTree> getNodes(InstitutionTree id) {
		return get("getNodes", id);

	}
	public List<InstitutionTree> get(String key, InstitutionTree id) {
		return this.getSqlSession().selectList(sqlKey(key), id);

	}
}
