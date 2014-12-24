package com.idap.web.result.subscriptions.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.result.subscriptions.entity.DataRepor;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理主题controller
 * @修改日志： ###################################################
 */

@Controller
@RequestMapping(value = "/dataRepor")
public class DataReporController extends BaseController<DataRepor, Long> {
	@Resource(name = "dataReporService")
	public void setBaseService(IBaseService<DataRepor, Long> baseService) {
		super.setBaseService(baseService);
	}

}