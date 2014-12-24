package com.idap.web.dataprocess.clean.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.RuleScript;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

/**
 * @###################################################
 * @功能描述：
 * @开发人员：李广彬
 * @创建日期：2014-4-24 11:12:47
 * @修改日志：
 * @################################################### 
 */
@Controller
@RequestMapping("/addressClean")
public class AddressCleanController extends BaseController<TableInteRule, String> {
    private static final Log logger = LogFactory.getLog(AddressCleanController.class);

    /**
     * @功能描述：保存清理设置 并 执行
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     */
    @RequestMapping(method = RequestMethod.POST, params = "optType=execute")
    @ResponseBody
    public Map<String, Object> execute(@RequestParam String params) {
        Map<String, Object> results = Constants.MAP();
        RuleScript addrRule=null;
        RuleScript postRule=null;
        try {
            TableInteRule tabRules = (TableInteRule) JsonUtils.jsonToEntity(params, TableInteRule.class);
            List<ColumnInteRule> colummnRule= tabRules.getColumnRules();
            // 查出脚本 
            Map<String, Object> typeParams=new HashMap<String, Object>();
            typeParams.put("ruleType", RuleType.ADDRESS_RULE.getType());
            List<RuleScript> rules= ruleScriptService.findList(typeParams);
            if(rules.get(0).getScriptCode().toLowerCase().equals(DataSetUtils.ADDRESS_CLEAN_TYPE_ADDRESS.toLowerCase())){
                addrRule=rules.get(0);
                postRule=rules.get(1);
            }else{
                addrRule=rules.get(1);
                postRule=rules.get(0);
            }
            if(colummnRule.get(0).getColType().toLowerCase().equals(DataSetUtils.ADDRESS_CLEAN_TYPE_ADDRESS.toLowerCase())){
                colummnRule.get(0).setRuleScript(addrRule);
                colummnRule.get(1).setRuleScript(postRule);
                if(StringUtils.isBlank(colummnRule.get(1).getDataSet1Col())){
                	colummnRule.remove(1);
                }
            }else{
                colummnRule.get(1).setRuleScript(addrRule);
                colummnRule.get(0).setRuleScript(postRule);
                if(StringUtils.isBlank(colummnRule.get(0).getDataSet1Col())){
                	colummnRule.remove(0);
                }
            }
            tabRules.setRuleType(RuleType.ADDRESS_RULE.getType());
            this.tabRuleService.insertTableRule(tabRules);
            this.tabRuleService.executeRule(tabRules);
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }
    
//    /**
//     * @功能描述：重新执行 
//     * @创建日期：2014-3-3 12:01:15
//     * @开发人员：李广彬
//     */
//    @RequestMapping(method = RequestMethod.POST, params = "optType=reExecute")
//    @ResponseBody
//    public Map<String, Object> reExecute(@RequestParam String ruleId) {
//        Map<String, Object> results = Constants.MAP();
//        try {
//            TableInteRule tabRules = tabRuleBaseService.getById(ruleId);
//    
//            this.tabRuleService.executeRule(tabRules);
//            results.put(Constants.SUCCESS, Constants.TRUE);
//        } catch (Exception e) {
//            results.put(Constants.SUCCESS, Constants.FALSE);
//            results.put(Constants.MESSAGE, e.getMessage());
//            logger.error(e);
//        }
//        return results;
//    }

    // ========================= get/set ===========================
    @Resource(name = "tableInteRuleService")
    public void setBaseService(IBaseService<TableInteRule, String> baseService) {
        super.setBaseService(baseService);
    }

    @Resource(name = "tableInteRuleService")
    private TableInteRuleService tabRuleService;
    
    @Resource(name = "tableInteRuleService")
    private IBaseService<TableInteRule, String> tabRuleBaseService;
    
    @Resource(name = "ruleScriptService")
    private IBaseService<RuleScript, Long> ruleScriptService;

}
