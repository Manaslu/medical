package com.idap.web.result.subscriptions.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.result.subscriptions.entity.SubsTail;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/subsTail")
public class SubsTailController extends BaseController<SubsTail, String> {
	@Resource(name = "subsTailService")
	public void setBaseService(IBaseService<SubsTail, String> baseService) {
		super.setBaseService(baseService);
	}

}
