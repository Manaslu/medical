package com.idap.processmgr.special.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.Demand;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("demandDao")
public class DemandDao extends DefaultBaseDao<Demand, String> {

	@Override
	public String getNamespace() {
		return Demand.class.getName();
	}

	public String getReqCodeSeq() {
		return this.getSqlSession().selectOne("getReqCode");
		
	}

	public Demand getProvContacts(String id) {
		return this.getSqlSession().selectOne("getProvContacts",id);
	}

	public Demand getGroupTechContact(String id) {
		return this.getSqlSession().selectOne("getGroupTechContact",id);
	}

	public Demand getTechContect(String id) {
		return this.getSqlSession().selectOne("getTechContect",id);
	}

	public Demand getAllContacts(String demandId) {
		return this.getSqlSession().selectOne("getAllContacts", demandId);
	}

	public List<Demand> getByContact(Map<String,String> map) {
		return this.getSqlSession().selectList("getByContact", map);
	}
}