package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.EsbDownload;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/esbDownload")
public class EsbDownloadController extends BaseController<EsbDownload, String> {
	@Resource(name = "esbDownloadService")
	public void setBaseService(IBaseService<EsbDownload, String> baseService) {
		super.setBaseService(baseService);
	}
}
