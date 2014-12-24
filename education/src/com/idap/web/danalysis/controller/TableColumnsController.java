package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.TableColumns;

@Controller
@RequestMapping(value = "/tableColumns")
public class TableColumnsController extends BaseController<TableColumns, String> {
	@Resource(name = "tableColumnsService")
	public void setBaseService(IBaseService<TableColumns, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
