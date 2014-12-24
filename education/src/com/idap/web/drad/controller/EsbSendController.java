package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.EsbSend;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/esbSend")
public class EsbSendController extends BaseController<EsbSend, String> {
	@Resource(name = "esbSendService")
	public void setBaseService(IBaseService<EsbSend, String> baseService) {
		super.setBaseService(baseService);
	}
}
