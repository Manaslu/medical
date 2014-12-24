package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.PortExcp;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/portExcp")
public class PortExcpController extends BaseController<PortExcp, String> {
	@Resource(name = "portExcpService")
	public void setBaseService(IBaseService<PortExcp, String> baseService) {
		super.setBaseService(baseService);
	}
}
