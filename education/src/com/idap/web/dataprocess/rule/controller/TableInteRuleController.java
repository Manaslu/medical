package com.idap.web.dataprocess.rule.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

/**
 * 1. 查询整合规则表 <br>
 * 
 * 
 * @author bai
 * 
 */
@Controller
@RequestMapping(value = "/tableInteRule")
public class TableInteRuleController extends
		BaseController<TableInteRule, String> {

	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "tableInteRuleService")
	public void setBaseService(IBaseService<TableInteRule, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "tableInteRuleService")
	public TableInteRuleService tableInteRuleService;

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	@Override
	public Map<String, Object> remove(String ruleId) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.tableInteRuleService.deleteTableRule(ruleId);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			logger.debug("删除整合日志失败", e);
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, "删除整合日志失败");
		}
		return results;
	}
	
	
	   /**
     * @功能描述：重新执行 
     * @创建日期：2014-6-19 12:01:15
     * @开发人员：李广彬
     */
    @RequestMapping(method = RequestMethod.POST, params = "optType=reExecute")
    @ResponseBody
    public Map<String, Object> reExecute(@RequestParam String ruleId) {
        Map<String, Object> results = Constants.MAP();
        try {
            TableInteRule tabRules = this.getBaseService().getById(ruleId);
    
            this.tableInteRuleService.executeRule(tabRules);
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }

}
