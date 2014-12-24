package com.idp.sysmgr.institution.service;

import java.util.HashMap;
import java.util.List;

import com.idp.sysmgr.institution.entity.Institution;
import com.idp.sysmgr.institution.entity.InstitutionTree;



/**
* @创建日期：2014-5-8 16:35:42
* @开发人员：hu
* @功能描述：机构管理service
* @修改日志：
*/
public interface IInstitutionService {

	//查询下级机构
	List<InstitutionTree> getNodes(InstitutionTree entity);
	public List<Institution> ifLowerLevelOrgcd(HashMap map) ;
}
