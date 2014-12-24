package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.SyncAuditor;

@Controller
@RequestMapping(value = "/syncAuditor")
public class SyncAuditorController extends BaseController<SyncAuditor, String> {
	@Resource(name = "syncAuditorService")
	public void setBaseService(IBaseService<SyncAuditor, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
