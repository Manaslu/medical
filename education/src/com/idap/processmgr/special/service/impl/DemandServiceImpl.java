
package com.idap.processmgr.special.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.dao.DemandDao;
import com.idap.processmgr.special.entity.Demand;
import com.idap.processmgr.special.service.DemandService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("demandService")
public class DemandServiceImpl extends DefaultBaseService<Demand, String> implements DemandService{

	@Resource(name = "demandDao")
	public void setBaseDao(IBaseDao<Demand, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "demandDao")
	public void setPagerDao(IPagerDao<Demand> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	@Resource(name = "demandDao")
	private DemandDao demandDao;
	
	public String getReqCodeSeq(){
		return demandDao.getReqCodeSeq();
	}
	
	public Demand getProvContacts(String id){
		return demandDao.getProvContacts(id);
	}
	
	public Demand getGroupTechContact(String id){
		return demandDao.getGroupTechContact(id);
	}

	public Demand getTechContact(String id) {
		return demandDao.getTechContect(id);
	}
	
	@Override
	public Pager<Demand> findByPager(Pager<Demand> pager, Map<String, Object> params) {
		return super.getPagerDao().findByPager(pager, params);
	}

	public Demand getAllContacts(String demandId) {
		return demandDao.getAllContacts(demandId);
	}
	
	public List<Demand> getByContact(Map<String,String> map) {
		return demandDao.getByContact(map);
	}
}