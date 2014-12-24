package com.idap.web.intextservice.customer.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.customer.entity.Customer;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController<Customer, Long>{

	@Resource(name = "customerService")
	public void setBaseService(IBaseService<Customer, Long> baseService) {
		super.setBaseService(baseService);
	}

}
