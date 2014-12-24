package com.idap.web.drad.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.drad.entity.SourceDataDic;
import com.idap.drad.entity.SourceDataDicColumn;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/dataDic")
public class SourceDataDicController extends
		BaseController<SourceDataDic, String> {

	@Resource(name = "sourceDataDicService")
	public void setBaseService(IBaseService<SourceDataDic, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "sourceDataDicColumnService")
	private IBaseService<SourceDataDicColumn, String> sourceDataDicColumnService;

	@RequestMapping(params = "queryDic=true", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDataDicColumn> query(@RequestParam("params") String params) {
		return this.sourceDataDicColumnService
				.findList(JsonUtils.toMap(params));
	}
}