package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.KPI;

@Controller
@RequestMapping(value = "/kpi")
public class KPIController extends BaseController<KPI, String> {
	@Resource(name = "kpiService")
	public void setBaseService(IBaseService<KPI, String> baseService) {
		super.setBaseService(baseService);
	}
	
//	@Resource(name="otherService")
//	public IBaseService<Object,String> otherservice;
	
//	public void list(){
//		this.otherservice.findList(Constants.MAP());
//	}
}
