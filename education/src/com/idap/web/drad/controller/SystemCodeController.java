package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.SourceDataDic;
import com.idap.drad.entity.SystemCode;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/systemCode")
public class SystemCodeController extends BaseController<SystemCode, String> {

	@Resource(name = "systemCodeService")
	public void setBaseService(IBaseService<SystemCode, String> baseService) {
		super.setBaseService(baseService);
	}
}