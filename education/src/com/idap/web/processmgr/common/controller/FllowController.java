package com.idap.web.processmgr.common.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.processmgr.special.entity.Fllow;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/fllow")
public class FllowController extends BaseController<Fllow, String> {
	
	@Resource(name = "fllowService")
	public void setBaseService(IBaseService<Fllow, String> baseService) {
		super.setBaseService(baseService);
	}
}
