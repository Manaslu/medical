package com.idap.web.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.drad.entity.ProvCode;
import com.idap.drad.entity.SystemCode;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController<ProvCode, String> {
	@Resource(name = "provCodeService")
	public void setBaseService(IBaseService<ProvCode, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "systemCodeService")
	private IBaseService<SystemCode, String> systemCodeService;

	@RequestMapping(method = RequestMethod.GET, params = "code=true")
	@ResponseBody
	public List<SystemCode> findList() {
		return this.systemCodeService.findList(Constants.MAP());
	}
}
