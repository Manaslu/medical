package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.ParaHistory;

@Controller
@RequestMapping(value = "/paraHistory")
public class ParaHistoryController extends BaseController<ParaHistory, String> {
	@Resource(name = "paraHistoryService")
	public void setBaseService(IBaseService<ParaHistory, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
