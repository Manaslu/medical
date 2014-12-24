package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.SourceIndexs;

@Controller
@RequestMapping(value = "/sourceIndexs")
public class SourceIndexsController extends BaseController<SourceIndexs, String> {
	@Resource(name = "sourceIndexsService")
	public void setBaseService(IBaseService<SourceIndexs, String> baseService) {
		super.setBaseService(baseService);
	}
	
//	@Resource(name="otherService")
//	public IBaseService<Object,String> otherservice;
	
//	public void list(){
//		this.otherservice.findList(Constants.MAP());
//	}
}
