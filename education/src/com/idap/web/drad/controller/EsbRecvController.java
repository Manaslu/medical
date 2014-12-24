package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.EsbRecv;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/esbRecv")
public class EsbRecvController extends BaseController<EsbRecv, String> {

	@Resource(name = "esbRecvService")
	public void setBaseService(IBaseService<EsbRecv, String> baseService) {
		super.setBaseService(baseService);
	}
}
