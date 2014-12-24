package com.idap.web.dataprocess.rebuild.controller;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.exception.RuleException;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idp.pub.constants.Constants;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping("/rebuild")
public class RebuildController extends
		BaseController<Map<String, Object>, String> {

	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "tableInteRuleService")
	public TableInteRuleService tableInteRuleService;

	@RequestMapping(params = "dorebuild=true", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dorebuild(TableInteRule inteRule,
			@RequestParam("rules") String value) {
		Map<String, Object> results = Constants.MAP();
		try {
			ColumnInteRule[] rules = (ColumnInteRule[]) JsonUtils.jsonToEntity(
					value, ColumnInteRule[].class);
			inteRule.getColumnRules().addAll(Arrays.asList(rules));
			logger.info("inteRule:" + JsonUtils.toJson(inteRule));
			TableInteRule tableRule = this.tableInteRuleService
					.insertTableRule(inteRule);
			this.tableInteRuleService.executeRule(tableRule);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (RuleException e) {
			logger.debug(e.getMessage(), e);
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
}
