package com.idap.web.dataprocess.clean.controller;

import java.util.ArrayList;
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

import com.idap.dataprocess.clean.service.CleanService;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
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
@RequestMapping("/dataSetUnique")
public class DataSetUniqueController extends BaseController<TableInteRule, String> {
    private static final Log logger = LogFactory.getLog(DataSetUniqueController.class);

    /**
     * @功能描述：保存排重设置 并 执行
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     */
    @RequestMapping(method = RequestMethod.POST, params = "optype=unique")
    @ResponseBody
    public Map<String, Object> saveClean(@RequestParam String params) {
        Map<String, Object> results = Constants.MAP();
        try {
            TableInteRule tabRules = (TableInteRule) JsonUtils.jsonToEntity(params, TableInteRule.class);
            tabRules.setRuleType(RuleType.UNIQUE_RULE.getType());
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

    /**
     * @功能描述：查看排重设置
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @param ruleId 规则
     * @param dataDefId 源数据集数据定义
     */
    @RequestMapping(method = RequestMethod.POST, params = "optype=preview")
    @ResponseBody
    public Map<String, Object> preview(@RequestParam String ruleId, @RequestParam String dataDefId) {
        Map<String, Object> results = Constants.MAP();
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        try {
            // 2.查询所有字段
            Map<String, String> columnInfo = getColumnsInfo(dataDefId);

            // 1.排重依据
            TableInteRule tabRule = this.getBaseService().getById(ruleId);
            String uniqueColumnsName = groupColumns2string(tabRule.getDataset1GroupColumn(), columnInfo);

            // 3.查询所有字段规则
            List<ColumnInteRule> columnRules = tabRule.getColumnRules();

            // 4.根据规则 取字段中文名 取脚本名
            for (ColumnInteRule colRule : columnRules) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("ruleName", colRule.getRuleScript().getScriptName());
                tempMap.put("columnName", columnInfo.get(colRule.getDataSet1Col()));

                dataList.add(tempMap);
            }
            results.put("result", dataList);
            results.put("uniqueColumnsName", uniqueColumnsName);
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }

    /**
     * @功能描述：分组的字段名转为前台显示的中文名称
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @param groupStr 分隔符
     * @param columnInfo 字段信息
     */
    private String groupColumns2string(String groupStr, Map<String, String> columnInfo) {
        String columnsName = "";
        String[] colArr = null;
        if (StringUtils.isNotBlank(groupStr)) {
            colArr = groupStr.split(",");
            for (String str : colArr) {
                columnsName += columnInfo.get(str) + ",";
            }
        }
        if (columnsName.length() > 0) {
            columnsName = columnsName.substring(0, columnsName.length() - 1);
        }
        return columnsName;
    }

    /**
     * @功能描述：查询字段信息
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @param groupStr 分隔符
     * @param columnInfo 字段信息
     */
    private Map<String, String> getColumnsInfo(String dataDefId) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataDefId", dataDefId);
        params.put("orderBy", "DISPLAY_COL_NUM");
        List<DataDefinitionAttr> result = defAttrService.findList(params);
        for (DataDefinitionAttr col : result) {
            resultMap.put(col.getColumnName(), col.getColumnDesc());
        }
        return resultMap;
    }

    // ========================= get/set ===========================
    @Resource(name = "tableInteRuleService")
    public void setBaseService(IBaseService<TableInteRule, String> baseService) {
        super.setBaseService(baseService);
    }

    @Resource(name = "tableInteRuleService")
    TableInteRuleService tabRuleService;

    @Resource(name = "cleanService")
    CleanService cleanService;

    @Resource(name = "dataDefinitionAttrService")
    IBaseService<DataDefinitionAttr, String> defAttrService;

}
