package com.idap.web.dataprocess.rule.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.rule.entity.RuleScript;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

/**
 * 脚本库 查询
 * <p>
 * 1. 调用清洗规则 <br>
 * RuleScript.query({isArray:true,params:'{"ruleType":"clean"}'});
 * <p>
 * 2. 调用重构规则 <br>
 * RuleScript.query({isArray:true,params:'{"ruleType":"rebuild"}'});
 *
 * <p>
 * 3. 调用整合(合并)规则 <br>
 *  RuleScript.query({isArray:true,params:'{"ruleType":"merge"}'});
 * <p>
 * 4. 调用排重规则 <br>
 *  RuleScript.query({isArray:true,params:'{"ruleType":"unique"}'});
 * 
 * @author Raoxy
 * 
 */
@Controller
@RequestMapping(value = "/ruleScript")
public class RuleScriptController extends BaseController<RuleScript, Long> {
	@Resource(name = "ruleScriptService")
	public void setBaseService(IBaseService<RuleScript, Long> baseService) {
		super.setBaseService(baseService);
	}
	
	
	@RequestMapping(params = "isArray=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<RuleScript> findByList(@RequestParam("params") String values) {
		Map params=JsonUtils.toMap(values);
		List<RuleScript> result=new  ArrayList<RuleScript>();
		List<RuleScript> chdResult=new  ArrayList<RuleScript>();
		result=this.getBaseService().findList(params);
		params.put("isChd", DataSetUtils.SWITCH_TRUE);//是否查询孩子结点
		chdResult=this.getBaseService().findList(params);
		for(RuleScript chd:chdResult){
			for(RuleScript rule:result){
				if(rule.getScriptId().equals(chd.getParScriptId())){
					rule.getChdRuleScript().add(chd);
				}
			}
		}
		return result;
	}
}
