package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.DataChech;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/dataCheck")
public class DataChechController extends BaseController<DataChech, String> {
	@Resource(name = "dataChechService")
	public void setBaseService(IBaseService<DataChech, String> baseService) {
		super.setBaseService(baseService);
	}
}
