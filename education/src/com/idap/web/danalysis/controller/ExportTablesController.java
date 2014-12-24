package com.idap.web.danalysis.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.ExportTables;

@Controller
@RequestMapping(value = "/exportTables")
public class ExportTablesController extends BaseController<ExportTables, String> {
	@Resource(name = "exportTablesService")
	public void setBaseService(IBaseService<ExportTables, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
