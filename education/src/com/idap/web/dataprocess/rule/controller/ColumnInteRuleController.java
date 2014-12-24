package com.idap.web.dataprocess.rule.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

/**
 * 字段整合规则表查询
 * <p>
 * @author bai
 * 
 */
@Controller
@RequestMapping(value = "/columnInteRule")
public class ColumnInteRuleController extends BaseController<ColumnInteRule, Long> {
	@Resource(name = "columnInteRuleService")
	public void setBaseService(IBaseService<ColumnInteRule, Long> baseService) {
		super.setBaseService(baseService);
	}  
	
}
