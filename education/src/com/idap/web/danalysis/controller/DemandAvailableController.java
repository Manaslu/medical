package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.DemandAvailable;

@Controller
@RequestMapping(value = "/demandAvailable")
public class DemandAvailableController extends BaseController<DemandAvailable, String> {
	@Resource(name = "demandAvailableService")
	public void setBaseService(IBaseService<DemandAvailable, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
