package com.idap.web.dataprocess.clean.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.clean.entity.DataCleanReport;
import com.idap.dataprocess.clean.service.CleanService;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.SubRuleScript;
import com.idap.dataprocess.rule.entity.SubScriptPara;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idap.web.dataprocess.utils.ColumnInteRuleComparable;
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
@RequestMapping("/dataSetClean")
public class DataSetCleanController extends BaseController<TableInteRule, String> {
    private static final Log logger = LogFactory.getLog(DataSetCleanController.class);

    /**
     * @功能描述：保存清理设置 并 执行
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     */
    @RequestMapping(method = RequestMethod.POST, params = "optype=clean")
    @ResponseBody
    public Map<String, Object> saveClean(@RequestParam String params) {
        Map<String, Object> results = Constants.MAP();
        try {
            TableInteRule tabRules = (TableInteRule) JsonUtils.jsonToEntity(params, TableInteRule.class);
            tabRules.setRuleType(RuleType.CLEAN_RULE.getType());
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
     * @功能描述：查看清理设置
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @param ruleId 规则
     * @param dataDefId 源数据集数据定义
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, params = "optype=preview")
    @ResponseBody
    public Map<String, Object> preview(@RequestParam String ruleId, @RequestParam String dataDefId) {
        Map<String, Object> results = Constants.MAP();
        try {
            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            Map<String, String> columnInfo = getColumnsInfo(dataDefId);//字段名称 信息
            TableInteRule tabRule = this.getBaseService().getById(ruleId);
            List<ColumnInteRule> columnRules = tabRule.getColumnRules();//字段整合规则
            //按匹次排序
            ColumnInteRuleComparable sort = new ColumnInteRuleComparable(); 
            Collections.sort(columnRules,sort);   
            //
            Map<String,String> keyNameMap=new HashMap<String,String>();//联合主键 和前台显示名称
            Map<String, List<String>> ruleColumnMap =new HashMap<String, List<String>>();//联合主键 和 字段列表
            for (ColumnInteRule colRule : columnRules) {
            	buildMapKey(colRule,columnInfo,keyNameMap,ruleColumnMap);
            }
            // 数据格式整理
            for (String key : keyNameMap.keySet()) {
                Map<String, Object> ruleAndColumns = new HashMap<String, Object>();
                ruleAndColumns.put("ruleName", keyNameMap.get(key));
                ruleAndColumns.put("columnNames", ruleColumnMap.get(key));
                dataList.add(ruleAndColumns);
            }
            results.put("result", dataList);
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }
    
    //根据不同的情况去生成Mapkey 跟前台显示的
    //情况1：一级规则无参数
    //情况2：一级规则有参数
    //情况3：一级规则有子规则
    private Map<String,String> buildMapKey(
    		ColumnInteRule colRule,
    		Map<String, String> columnInfo,
    		Map<String,String> keyNameMap,
    		Map<String,List<String>> ruleColumnMap){
    	
    	StringBuffer key=new StringBuffer("");
    	StringBuffer showRuleName=new StringBuffer("");
    	if(colRule.getScriptParams().size()>0){//有参数：匹次号
    		//键
    		key.append(colRule.getBatchNo());
    		//前台显示名
    		showRuleName.append(colRule.getRuleScript().getScriptName());
    		showRuleName.append("(");
    		if(DataSetUtils.RULE_SCRIPT_ID_KEY.equals(colRule.getRuleScript().getScriptId())){
    			showRuleName.append("\n关键字："+colRule.getScriptParams().get(0).getParaVal()); 
    		}else if(DataSetUtils.RULE_SCRIPT_ID_SUBSTR.equals(colRule.getRuleScript().getScriptId())){
    			showRuleName.append(
    					"\n从 "
    						+colRule.getScriptParams().get(0).getParaVal()
    					+" 截取 "
    						+colRule.getScriptParams().get(1).getParaVal()
    					+" 个字符");
    		}
    		showRuleName.append(")");
    		keyNameMap.put(key.toString(), showRuleName.toString());
    		buildRuleColumn(key.toString(),colRule.getDataSet1Col(),ruleColumnMap,columnInfo);
    	}else if(colRule.getSubRuleScript().size()>0){//有子规则：匹次号+子脚本+参数
    		for(SubRuleScript subRule:colRule.getSubRuleScript()){
    			key=new StringBuffer("");
    			key.append(colRule.getBatchNo());
    			key.append(subRule.getScriptId());
    			for(SubScriptPara param:subRule.getSubScriptParams()){
        			key.append(param.getParaVal());
        		}
    			showRuleName=new StringBuffer("");
    			showRuleName.append(colRule.getRuleScript().getScriptName());
    			showRuleName.append("(");
    			showRuleName.append(subRule.getSubScriptParams().get(0).getParaVal()+" "+subRule.getRuleScript().getScriptName()+" "+subRule.getSubScriptParams().get(1).getParaVal());
        		showRuleName.append(")");
        		keyNameMap.put(key.toString(), showRuleName.toString());
        		buildRuleColumn(key.toString(),subRule.getColName(),ruleColumnMap,columnInfo);
    		}
    	}else{//无参数:匹次号
    		key.append(colRule.getBatchNo());
    		showRuleName.append(colRule.getRuleScript().getScriptName());
    		keyNameMap.put(key.toString(), showRuleName.toString());
    		buildRuleColumn(key.toString(),colRule.getDataSet1Col(),ruleColumnMap,columnInfo);
    	}
    	return keyNameMap;
    }
    /**
     * 
     * @param key  联合主键
     * @param column 数据库级的字段名
     * @param ruleColumnMap 结果存放表
     * @param columnInfo 字段名与中文的对照
     * @return 联合主键:[字段1,字段2]
     */
    private Map<String, List<String>> buildRuleColumn(String key,String column,Map<String,List<String>> ruleColumnMap,Map<String, String> columnInfo){
    	if(ruleColumnMap.get(key)==null){
    		List<String> temp=new ArrayList<String>();
        	temp.add(columnInfo.get(column));
    		ruleColumnMap.put(key, temp);
    	}else{
    		ruleColumnMap.get(key).add(columnInfo.get(column));
    	}
    	return ruleColumnMap;
    }

    /**
     * @功能描述：查看清理报告
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @param ruleId 规则
     * @param dataDefId 源数据集数据定义
     */
    @RequestMapping(method = RequestMethod.POST, params = "optype=report")
    @ResponseBody
    public Map<String, Object> report(@RequestParam String ruleId, @RequestParam String dataDefId) {
        Map<String, Object> results = Constants.MAP();
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        try {// cleanService
            Map<String, String> columnInfo = getColumnsInfo(dataDefId);// name－desc
            TableInteRule tabRule = this.getBaseService().getById(ruleId);
            List<ColumnInteRule> columnRules = tabRule.getColumnRules();// 过滤出所有的字段规则
            Map<String, String> rulesMap = new HashMap<String,String>();// id name
            Map<String, String> columnNames = getColumnNames(columnRules, columnInfo);// name
                                                                                      // desc
            List<DataCleanReport> dcrList = cleanService.findCleanReport(ruleId);
            for (String colName : columnNames.keySet()) {// 字段
                List<Object> item = new ArrayList<Object>();
                item.add(columnInfo.get(colName));
                
                for(DataCleanReport report : dcrList){
                	if(colName.equals(report.getDatasetColumnName())){
                		Map<String, Comparable> objMap = new HashMap<String, Comparable>();
                		
                		if(report.getScriptId().toString().equals("6") && report.getSonScriptId()!=null){
                			String[] params = report.getParamter().split(",");
                			if(report.getSonScriptId().toString().equals("39")){
                				rulesMap.put(report.getDatasetColumnName()+report.getBatchNo(), 
                						params[0]+" 全部替换成 "+params[1] );
                				objMap.put("title", report.getDatasetColumnName()+report.getBatchNo());
                			}else if(report.getSonScriptId().toString().equals("40")){
                				rulesMap.put(report.getDatasetColumnName()+report.getBatchNo(), 
                						"头替换 "+params[0] );
                				objMap.put("title", report.getDatasetColumnName()+report.getBatchNo());
                			}else if(report.getSonScriptId().toString().equals("41")){
                				rulesMap.put(report.getDatasetColumnName()+report.getBatchNo(), 
                						"尾替换 "+params[0] );
                				objMap.put("title", report.getDatasetColumnName()+report.getBatchNo());
                			}
                			
                		}else if(report.getScriptId().toString().equals("1")){
            				rulesMap.put(report.getDatasetColumnName()+report.getBatchNo(), 
            						"将 "+report.getParamter()+" 全部去除 " );
            				objMap.put("title", report.getDatasetColumnName()+report.getBatchNo());
                		}else{
                			rulesMap.put(report.getDatasetColumnName()+report.getBatchNo(), 
                					report.getRuleScript().getScriptName());
                			objMap.put("title", report.getDatasetColumnName()+report.getBatchNo());
                		}
                		
                        
                        objMap.put("totalCount", report.getHandleNum());
                        objMap.put("successCount", report.getSuccNum());
                        objMap.put("rate", String.format("% 50.2f", report.getRate() * 100));
                        
                        item.add(objMap);
                	}
                
                }
                dataList.add(item);
            }
            
            // titleList [头尾空格去除,称谓去除]
            // columnNames [名字,电话]
            // List name map map map
            List<List<Object>> targetList = new ArrayList<List<Object>>();

            //过滤dataList中多余数据	
        	for(List<Object> list:dataList){
        		List<Object> tarList = new ArrayList<Object>();
        		tarList.add(list.get(0));
            	for(int i=1;i<=rulesMap.size();i++){
            		if(i>=list.size()) {
            			tarList.add(new HashMap());
            			continue;
            		}
            		Map<String,String> val_map = (Map<String,String>) list.get(i);
                    Collection<String> coll = rulesMap.keySet();
                    Iterator<String>  it= coll.iterator();
                    boolean flag = true;
            		while(it.hasNext()){
            			String val  = it.next();
                		if(val.equals(val_map.get("title"))){
                			tarList.add(val_map);
                			flag = false;
                		}
            		}
            		if(flag) tarList.add(new HashMap());
            	}
            	targetList.add(tarList);
        	}
            
            results.put("titleList", rulesMap);
            results.put("result", targetList);
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
        	e.printStackTrace();
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }

    /**
     * @功能描述：数据清理结果 转map
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     */
    private Map<String, DataCleanReport> list2map(List<DataCleanReport> dcrList) {
        Map<String, DataCleanReport> objsMap = new HashMap<String, DataCleanReport>();
        for (DataCleanReport obj : dcrList) {
            objsMap.put(obj.getDatasetColumnName() + obj.getScriptId() + obj.getSonScriptId(), obj);
        }
        return objsMap;
    }

    /**
     * @功能描述：整合日志转map
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     */
    private Map<String, String> getColumnNames(List<ColumnInteRule> columnRules, Map<String, String> columnInfo) {
        Map<String, String> tempMap = new HashMap<String, String>();
        for (ColumnInteRule rule : columnRules) {
            if (tempMap.get(rule.getDataSet1Col()) == null) {
                tempMap.put(rule.getDataSet1Col(), columnInfo.get(rule.getDataSet1Col()));
            }
        }
        return tempMap;
    }

    /**
     * @功能描述：查询指定数据定义的字段信息
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
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

    /**
     * @功能描述：字段整合规则转Map存储
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     */
    private Map<Long, String> getRulesInfo(List<ColumnInteRule> columnRules) {
        Map<Long, String> tempMap = new HashMap<Long, String>();
        for (ColumnInteRule rule : columnRules) {
            tempMap.put(rule.getRuleScript().getScriptId(), rule.getRuleScript().getScriptName());
        }
        return tempMap;
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
