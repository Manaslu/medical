package com.idap.web.danalysis.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.ParaYearList;

@Controller
@RequestMapping(value = "/paraYearList")
public class ParaYearListController extends BaseController<ParaYearList, String> {
	@Resource(name = "paraYearListService")
	public void setBaseService(IBaseService<ParaYearList, String> baseService) {
		super.setBaseService(baseService);
	}
	
//	@Resource(name="otherService")
//	public IBaseService<Object,String> otherservice;
	
//	public void list(){
//		this.otherservice.findList(Constants.MAP());
//	}
}
