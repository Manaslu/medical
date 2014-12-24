package com.idap.web.dataprocess.integration.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.service.DataSetContentService;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idp.pub.constants.Constants;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping("/merge")
public class MergeController extends
		BaseController<Map<String, Object>, String> {
	private Log logger = LogFactory.getLog(MergeController.class);

	@Resource(name = "tableInteRuleService")
	public TableInteRuleService tableInteRuleService;

	@RequestMapping(params = "domerge=true", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> domerge(TableInteRule inteRule,
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
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	@RequestMapping(params = "mergeQuery=true", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> queryMergeData(
			@RequestParam("params") String values, HttpServletRequest request) {
		Map<String, Object> params = JsonUtils.toMap(values);
		DataSet dataSet = dataSetService.getById(params.get("dataSetId")
				.toString());
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		pager.setCurrent(0);
		pager.setLimit(10);
		params.put("columns", this.getAttrs(dataSet, request));
		params.put("tableName", dataSet.getTableName());
		params.put("dataDefId", dataSet.getDataDefId());
		return this.dataSetContentService.queryMergeContent(params, pager)
				.getData();
	}

	@SuppressWarnings("unchecked")
	private List<DataDefinitionAttr> getAttrs(DataSet dataSet,
			HttpServletRequest request) {
		String keyAttr = dataSet.getDataSetId() + "_attr";

		List<DataDefinitionAttr> attrs = (List<DataDefinitionAttr>) request
				.getSession().getAttribute(keyAttr);
		if (attrs == null) {
			Map<String, Object> attrParams = new HashMap<String, Object>();
			attrParams.put("dataDefId", dataSet.getDataDefId());
			attrParams.put("orderBy", "DISPLAY_COL_NUM");
			attrs = baseService.findList(attrParams);
			request.getSession().setAttribute(keyAttr, attrs);
		}
		return attrs;
	}

	@Resource(name = "dataDefinitionAttrService")
	private IBaseService<DataDefinitionAttr, String> baseService;

	@Resource(name = "dataSetContentService")
	private DataSetContentService dataSetContentService;

	@Resource(name = "dataSetService")
	private IBaseService<DataSet, String> dataSetService;
}
