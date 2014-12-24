package com.idap.web.drad.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drad.entity.DataDownload;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value="/dataDownload")
public class DataDownLoadController extends
		BaseController<DataDownload, String> {
	@Resource(name = "dataDownloadService")
	public void setBaseService(IBaseService<DataDownload, String> baseService) {
		super.setBaseService(baseService);
	}
}
