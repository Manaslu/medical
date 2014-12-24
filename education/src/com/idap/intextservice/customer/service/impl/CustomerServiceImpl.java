package com.idap.intextservice.customer.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.intextservice.customer.entity.Customer;
import com.idap.intextservice.customer.service.ICustomerService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-19 16:36:42
 * @开发人员：hu
 * @功能描述：客户管理实现类
 * @修改日志： ###################################################
 */

@Service("customerService")
public class CustomerServiceImpl extends DefaultBaseService<Customer, Long> implements
		ICustomerService {

	@Resource(name = "customerDao")
	public void setBaseDao(IBaseDao<Customer, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "customerDao")
	public void setPagerDao(IPagerDao<Customer> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	public Pager<Customer> query(Pager<Customer>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	
}
