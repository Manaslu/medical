package com.idap.intextservice.customer.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.customer.entity.Customer;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-19 16:36:42
 * @开发人员：hu
 * @功能描述：客户管理dao
 * @修改日志： ###################################################
 */

@Repository("customerDao")
public class CustomerDao extends DefaultBaseDao<Customer, Long> {
	public String getNamespace() {
		return Customer.class.getName();
	}
}
