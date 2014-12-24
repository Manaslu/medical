package com.idap.web.danalysis.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.danalysis.entity.IndexTheme;
 
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;


/**
* @###################################################
* @功能描述：
* @开发人员：王威
* @创建日期：2014-5-15 11:12:47
* @修改日志：
* @###################################################
*/
@Controller
@RequestMapping("/indexTheme") 
public class IndexThemeController  extends BaseController<IndexTheme, String>{
	private static final Log logger = LogFactory.getLog(IndexThemeController.class);
	@Resource(name = "indexThemeService")
	public void setBaseService(IBaseService<IndexTheme, String> baseService) {
		super.setBaseService(baseService);
	}
	
 




}