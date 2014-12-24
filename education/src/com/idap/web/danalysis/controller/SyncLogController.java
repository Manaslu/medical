package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.SyncLog;

@Controller
@RequestMapping(value = "/syncLog")
public class SyncLogController extends BaseController<SyncLog, String> {
	@Resource(name = "syncLogService")
	public void setBaseService(IBaseService<SyncLog, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
