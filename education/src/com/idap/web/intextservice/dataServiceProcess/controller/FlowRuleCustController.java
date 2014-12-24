package com.idap.web.intextservice.dataServiceProcess.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.RuleScript;
import com.idap.dataprocess.rule.entity.SubRuleScript;
import com.idap.dataprocess.rule.entity.SubScriptPara;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.service.impl.ColumnInteRuleServiceImpl;
import com.idap.intextservice.dataServiceProcess.entity.FlowRuleCust;
import com.idap.intextservice.dataServiceProcess.service.DataFlowExecutor;
import com.idp.pub.constants.Constants;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/flowRuleCust")
public class FlowRuleCustController extends
		BaseController<FlowRuleCust, String> {

	@Resource(name = "flowRuleCustService")
	public void setBaseService(IBaseService<FlowRuleCust, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "dataFlowExecutor")
	public DataFlowExecutor dataFlowExecutor;

	@Resource(name = "flowRuleCustService")
	public IBaseService<FlowRuleCust, String> flowRuleCustService;
	
	@Resource(name = "columnInteRuleService")
	public ColumnInteRuleServiceImpl columnInteRuleService;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	// 查询用户设置的规则
	@RequestMapping(params = "findRules=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<ColumnInteRule> findRule(@RequestParam("params") String values) {
		Map<String, Object> map = JsonUtils.toMap(values);
		String ruleId = (String) map.get("ruleId");
		ColumnInteRule s = new ColumnInteRule();
		s.setRuleId(ruleId);
		return columnInteRuleService.getRules(s);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> create(FlowRuleCust entity) {
		Map<String, Object> results = Constants.MAP();
		// entitys是添加的规则
		// String entity
		// ="[{\"colRuleType\":\"rebuild\",\"ruleDesc\":\"电话重构\",\"resultColumnName\":\"姓名,年龄\"}]";
		String entitys = entity.getRules();
		try {
			if (!"".equals(entitys)) {// 手工定义规则
				String sourceDataSet = generateKeyService.getNextGeneratedKey(
						"").getNextKey();// 生成原数据集的ID
				// 手工定义的时候没有设置源数据集，所以得动态虚拟生成一个源数据集id保存到表里
				entity.setSourceDataSet(sourceDataSet);
				FlowRuleCust flowRuleCust = this.flowRuleCustService
						.save(entity);

				// 流程规则定制id
				String flowRuleCustId = flowRuleCust.getFlowRuleCustId();
				// 把添加的规则转化成list数组
				ColumnInteRule[] list = (ColumnInteRule[]) JsonUtils
						.jsonToEntity(entitys, ColumnInteRule[].class);
				Integer indexNo = 0;
				Integer indexNo1 = 0;
				String resultSetId ="";
				TableInteRule inteRule = new TableInteRule();
				int x = 0;
				for (int i=0;i<list.length;i++) {
					Boolean flag = false;
					int t = i+1;
					if(list.length == t){
						t=i;
					}
					ColumnInteRule obj = list[i];
					ColumnInteRule objStr = list[t];
					
					//保存排重规则
					if(("unique".equals(obj.getColRuleType()) && !obj.getDataset1GroupColumn().equals(objStr.getDataset1GroupColumn())) 
							|| (obj.equals(objStr) && "unique".equals(obj.getColRuleType()))){
						flag = true;
						resultSetId = generateKeyService.getNextGeneratedKey("")
								.getNextKey();
						indexNo1 = indexNo1 + 1;
						x= 1;
					}
					if("unique".equals(obj.getColRuleType())){
						// 这是一对多的关系，得先处理选择的列，可能选择多个列(姓名，地址)，所以得处理。sourceDataSet（A-B,B-C,C-D）
						sourceDataSet = doInsertUnique(obj,flag , resultSetId,inteRule, flowRuleCustId,
								sourceDataSet,entity.getCreatePs(), indexNo1);
					}else{
						// 这是一对多的关系，得先处理选择的列，可能选择多个列(姓名，地址)，所以得处理。sourceDataSet（A-B,B-C,C-D）
						sourceDataSet = doInsert(obj, flowRuleCustId,
								sourceDataSet,entity.getCreatePs(), ++indexNo);
					}
					if(x==1){
						inteRule = new TableInteRule();
						x=0;
					}
					
				}

				// 手工定义的时候没有设置目标数据集，所以得动态虚拟生成一个目标数据集id修改到表里
				flowRuleCust.setResultDataSet(sourceDataSet);
				this.flowRuleCustService.update(flowRuleCust);

			} else {// 数据集抽取
				FlowRuleCust flowRuleCust = this.flowRuleCustService
						.save(entity);
				dataFlowExecutor.insertTableRule(flowRuleCust
						.getFlowRuleCustId());
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	
	private String doInsertUnique(ColumnInteRule rule, Boolean flag,String resultSetId, TableInteRule inteRule, String processId,
			String osetId, String createUser, Integer indexNo) throws BusinessException {

		// 获取规则列（姓名,年龄）
		String[] columns = rule.getResultColumnName().split(",");
		//设置脚本id的
		RuleScript ruleScript  = new RuleScript();
		ruleScript.setScriptId(Long.parseLong(rule.getScriptId()));
		inteRule.setCreateUser(createUser);
		//设置排重依据
		if(!"".equals(rule.getDataset1GroupColumn())){
			inteRule.setDataset1GroupColumn(rule.getDataset1GroupColumn());
		}
		// 设置一条规则的时候，如果选择的是处理多列，就要插入多列记录
		for (String column : columns) {
			ColumnInteRule crule = new ColumnInteRule();
			
			crule.setColRuleType(rule.getColRuleType());
			crule.setDataSet1Col(column);
			crule.setRuleDesc(rule.getRuleDesc());
			crule.setRuleScript(ruleScript);

			DataSet dataSet1 = new DataSet();
			dataSet1.setDataSetId(osetId);

			inteRule.setDataSet1(dataSet1);
			inteRule.setRuleType(rule.getColRuleType());
			//设置运用的规则
			inteRule.setRuleDesc(rule.getRuleDesc());
			inteRule.getColumnRules().add(crule);
		}
		if(flag){
			// 把设置好的规则插入到相应的表中，并返回结果数据集id
			inteRule.setResultDataSetName(resultSetId);
			this.dataFlowExecutor.insertTableRule(inteRule, processId, resultSetId,
					indexNo);
			inteRule = new TableInteRule();
		}
		return resultSetId;
	}
	
	/**
	 * 
	 * @param rule
	 *            表整合规则
	 * @param processId
	 *            流程规则定制id
	 * @param osetId
	 *            原数据集的ID
	 * @return
	 * @throws BusinessException
	 */
	private String doInsert(ColumnInteRule rule, String processId,
			String osetId, String createUser, Integer indexNo) throws BusinessException {

		// 获取规则列（姓名,年龄）
		String[] columns = rule.getResultColumnName().split(",");
		//设置脚本id的
		RuleScript ruleScript  = new RuleScript();
		ruleScript.setScriptId(Long.parseLong(rule.getScriptId()));
		TableInteRule inteRule = new TableInteRule();
		inteRule.setCreateUser(createUser);
		// 设置一条规则的时候，如果选择的是处理多列，就要插入多列记录
		for (String column : columns) {
			ColumnInteRule crule = new ColumnInteRule();
			
			SubScriptPara subScriptPara = new SubScriptPara();
			SubScriptPara subScriptPara1 = new SubScriptPara();
			SubRuleScript subRuleScript = new SubRuleScript();
			SubRuleScript subRuleScript1 = new SubRuleScript();
			String columnInteId = generateKeyService.getNextGeneratedKey(
					"").getNextKey();
			Boolean flag = true;
			if(!"".equals(rule.getSrc()) && !"".equals(rule.getTo())){//自定义规则
				for(int i=0;i<2;i++){
					if(flag){
						//往t06_sub_script_para表插入记录
						subScriptPara.setParaCd("src");
						subScriptPara.setParaVal(rule.getSrc());
						subScriptPara.setSeqNo(1L);
						subRuleScript.getSubScriptParams().add(subScriptPara);
						//往t06_sub_rule_script插入记录
						subRuleScript.setScriptId(Long.parseLong(rule.getOptType()));
						subRuleScript.setColName(column);
						subRuleScript.setSeqNo(0L);
						crule.getSubRuleScript().add(subRuleScript);
						flag = false;
					}else{
						//往t06_sub_script_para表插入记录
						subScriptPara1.setParaCd("to");
						subScriptPara1.setParaVal(rule.getTo());
						subScriptPara1.setSeqNo(2L);
						subRuleScript1.getSubScriptParams().add(subScriptPara1);
						//往t06_sub_rule_script插入记录
						subRuleScript1.setScriptId(Long.parseLong(rule.getOptType()));
						subRuleScript1.setColName(column);
						subRuleScript1.setSeqNo(1L);
						crule.getSubRuleScript().add(subRuleScript1);
					}
				}
			}else if(!"".equals(rule.getKeyword())){//剔除关键字以后字符
					subScriptPara.setColumnInteId(columnInteId);
					subScriptPara.setParaCd("keyword");
					subScriptPara.setParaVal(rule.getKeyword());
					subScriptPara.setSeqNo(1L);
					crule.getScriptParams().add(subScriptPara);
			}else if(!"".equals(rule.getStartIndex()) && !"".equals(rule.getStrLength())){//字符截取
				for(int i=0;i<2;i++){
					if(flag){
						subScriptPara.setColumnInteId(columnInteId);
						subScriptPara.setParaCd("startIndex");
						subScriptPara.setParaVal(rule.getStartIndex());
						subScriptPara.setSeqNo(1L);
						crule.getScriptParams().add(subScriptPara);
						flag = false;
					}else{
						subScriptPara1.setColumnInteId(columnInteId);
						subScriptPara1.setParaCd("strLength");
						subScriptPara1.setParaVal(rule.getStrLength());
						subScriptPara1.setSeqNo(2L);
						crule.getScriptParams().add(subScriptPara1);
					}
				}
				
			}
			
			crule.setColRuleType(rule.getColRuleType());
			crule.setDataSet1Col(column);
			crule.setRuleDesc(rule.getRuleDesc());
			crule.setRuleScript(ruleScript);

			DataSet dataSet1 = new DataSet();
			dataSet1.setDataSetId(osetId);

			inteRule.setDataSet1(dataSet1);
			inteRule.setRuleType(rule.getColRuleType());
			//设置运用的规则
			inteRule.setRuleDesc(rule.getRuleDesc());
			inteRule.getColumnRules().add(crule);
		}
		// 把设置好的规则插入到相应的表中，并返回结果数据集id
		String resultSetId = generateKeyService.getNextGeneratedKey("")
				.getNextKey();
		inteRule.setResultDataSetName(resultSetId);
		//设置排重依据
		if(!"".equals(rule.getDataset1GroupColumn())){
			inteRule.setDataset1GroupColumn(rule.getDataset1GroupColumn());
		}
		this.dataFlowExecutor.insertTableRule(inteRule, processId, resultSetId,
				indexNo);

		return resultSetId;
	}

}
