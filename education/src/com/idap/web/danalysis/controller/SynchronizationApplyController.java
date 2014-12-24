package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.SynchronizationApply;

@Controller
@RequestMapping(value = "/synchronizationApply")
public class SynchronizationApplyController extends BaseController<SynchronizationApply, String> {
	@Resource(name = "synchronizationApplyService")
	public void setBaseService(IBaseService<SynchronizationApply, String> baseService) {
		super.setBaseService(baseService);
	}
	
//	@Resource(name="otherService")
//	public IBaseService<Object,String> otherservice;
	
//	public void list(){
//		this.otherservice.findList(Constants.MAP());
//	}
}
