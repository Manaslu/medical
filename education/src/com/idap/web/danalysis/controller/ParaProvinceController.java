package com.idap.web.danalysis.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.ParaProvince;

@Controller
@RequestMapping(value = "/paraProvince")
public class ParaProvinceController extends BaseController<ParaProvince, String> {
	@Resource(name = "paraProvinceService")
	public void setBaseService(IBaseService<ParaProvince, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
