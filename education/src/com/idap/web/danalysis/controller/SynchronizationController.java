package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.Synchronization;

@Controller
@RequestMapping(value = "/synchronization")
public class SynchronizationController extends BaseController<Synchronization, String> {
	@Resource(name = "synchronizationService")
	public void setBaseService(IBaseService<Synchronization, String> baseService) {
		super.setBaseService(baseService);
	}
	
//	@Resource(name="otherService")
//	public IBaseService<Object,String> otherservice;
	
//	public void list(){
//		this.otherservice.findList(Constants.MAP());
//	}
}
