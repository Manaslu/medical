package com.idap.processmgr.special.service;

import java.util.List;
import java.util.Map;

import com.idap.processmgr.special.entity.Demand;

public interface DemandService{
	
	/**
	 * 获取需求编号的序列号
	 * @return
	 */
	public String getReqCodeSeq();
	
	/**
	 * 获取省联系人
	 * @param id
	 * @return
	 */
	public Demand getProvContacts(String id);
	
	/**
	 * 获取集团联系人 
	 * @param id
	 * @return
	 */
	public Demand getGroupTechContact(String id);

	/**
	 * 获取技术联系人
	 * @param id
	 * @return
	 */
	public Demand getTechContact(String id);
	
	/**
	 * 获取相应的人的名字
	 * @param demandId
	 * @return
	 */
	public Demand getAllContacts(String demandId);
	
	/**
	 * 根据技术员登陆名获取对应的需求
	 * @param contact
	 * @return
	 */
	public List<Demand> getByContact(Map<String,String> map);
}