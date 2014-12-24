package com.idp.sysmgr.institution.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.institution.dao.InstitutionDao;
import com.idp.sysmgr.institution.dao.InstitutionTreeDao;
import com.idp.sysmgr.institution.entity.Institution;
import com.idp.sysmgr.institution.entity.InstitutionTree;
import com.idp.sysmgr.institution.service.IInstitutionService;


/**
 * 
 * @创建日期：2014-5-08 16:36:42
 * @开发人员：hu
 * @功能描述：机构管理实现
 * @修改日志： 
 */
@Service("institutionService")
@Transactional
public class InstitutionServiceImpl extends DefaultBaseService<Institution, Long> implements
		IInstitutionService{

	@Resource(name = "institutionDao")
	public void setBaseDao(IBaseDao<Institution, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "institutionDao")
	public void setPagerDao(IPagerDao<Institution> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	@Resource(name = "institutionTreeDao")
	private InstitutionTreeDao institutionTreeDao;
	
	@Resource(name = "institutionDao")
	private InstitutionDao institutionDao;
	
	
	public Pager<Institution> query(Pager<Institution>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	//查询下级机构
	@Override
	public List<InstitutionTree> getNodes(InstitutionTree id) {
		Assert.notNull(institutionTreeDao, "必须设置基础接口IBaseDao的实现类");
		return this.institutionTreeDao.getNodes(id);
	}
	
	 
	public List<Institution> ifLowerLevelOrgcd(HashMap map) {
		Assert.notNull(institutionTreeDao, "必须设置基础接口IBaseDao的实现类");
		return this.institutionDao.ifLowerLevelorgcd(map) ;
	} 
	
}
