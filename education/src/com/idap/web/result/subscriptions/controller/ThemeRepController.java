package com.idap.web.result.subscriptions.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.result.subscriptions.entity.ThemeRep;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/themeRep")
public class ThemeRepController extends BaseController<ThemeRep, String> {
	@Resource(name = "themeRepService")
	public void setBaseService(IBaseService<ThemeRep, String> baseService) {
		super.setBaseService(baseService);
	}

}
