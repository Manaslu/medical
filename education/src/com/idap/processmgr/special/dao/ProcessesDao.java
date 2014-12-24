package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.Processes;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("processesDao")
public class ProcessesDao extends DefaultBaseDao<Processes, String> {

	@Override
	public String getNamespace() {
		return Processes.class.getName();
	}
	//获取所有的成果数量
	public Integer findAllCount(String id){
		return getSqlSession().selectOne("findAllCount", id);
	}
	//获取所有的已确认的成果数量
	public Integer findCountOfConfirm(String id){
		return getSqlSession().selectOne("findCountOfConfirm",id);
	}
}