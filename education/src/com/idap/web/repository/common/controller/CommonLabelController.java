package com.idap.web.repository.common.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.repository.entity.LabelLib;
import com.idap.intextservice.repository.service.ILabelLibService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/commonLabel")
public class CommonLabelController extends BaseController<LabelLib, String> {
	@Resource(name = "labelLibService")
	public void setBaseService(IBaseService<LabelLib, String> baseService) {
		super.setBaseService(baseService);
	}   
	@Resource(name = "labelLibService")
	ILabelLibService labelLibService;
	
	// @Resource(name="LabelLibDao")
		 
	@ResponseBody  
	@RequestMapping(params="primary=true" , method=RequestMethod.GET  )                            
		public List<HashMap<java.lang.String, java.lang.String>> generaterPrimaryKey() {
    //.POST
		return   labelLibService.generaterPrimaryKey() ;
	}
}
