package com.idap.web.dataprocess.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.service.DataSetContentService;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;

@Controller
@RequestMapping("/dataSetView")
public class DataSetViewController {
	@Resource(name = "dataSetContentService")
	private DataSetContentService service;

	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	public Pager<Map<String, Object>> findByPager(Pager<Map<String, Object>> pager,
			@RequestParam("params") String values, HttpServletRequest request) {
		Map<String, Object> params = JsonUtils.toMap(values);
		DataSet dataSet = this.getDataSet(request, params.get("dataSetId").toString());
		params.put("tableName", dataSet.getTableName());
		params.put("dataDefId", dataSet.getDataDefId());
		params.put("columns", this.getAttrs(dataSet, request));
		pager = service.queryMergeContent(params, pager);
		// 转换格式
		// 数据Map转List
		return pager;
	}

	@SuppressWarnings("unchecked")
	private List<DataDefinitionAttr> getAttrs(DataSet dataSet, HttpServletRequest request) {
		String keyAttr = dataSet.getDataSetId() + "_attr";

		List<DataDefinitionAttr> attrs = (List<DataDefinitionAttr>) request.getSession().getAttribute(keyAttr);
		if (attrs == null) {
			Map<String, Object> attrParams = new HashMap<String, Object>();
			attrParams.put("dataDefId", dataSet.getDataDefId());
			attrParams.put("orderBy", "DISPLAY_COL_NUM");
			attrParams.put("isPk", DataSetUtils.SWITCH_FALSE);
			attrs = baseService.findList(attrParams);
			request.getSession().setAttribute(keyAttr, attrs);
		}
		return attrs;
	}

	private DataSet getDataSet(HttpServletRequest request, String dataSetId) {

		DataSet dataSet = (DataSet) request.getSession().getAttribute(dataSetId);
		if (dataSet == null) {
			dataSet = dataSetService.getById(dataSetId);
			request.getSession().setAttribute(dataSet.getDataSetId(), dataSet);
		}
		return dataSet;
	}

	@RequestMapping(params = "isArray=true", method = RequestMethod.GET)
	@ResponseBody
	public List<DataDefinitionAttr> findByList(@RequestParam("params") String values, HttpServletRequest request) {
		Map<String, Object> params = JsonUtils.toMap(values);
		DataSet dataSet = this.getDataSet(request, params.get("dataSetId").toString());
		Assert.notNull(dataSet, "数据集[" + params.get("dataSetId") + "]为空,无法查看数据集的内容");
		List<DataDefinitionAttr> result = this.getAttrs(dataSet, request);
		return result;
	}

	@Resource(name = "dataDefinitionAttrService")
	private IBaseService<DataDefinitionAttr, String> baseService;

	@Resource(name = "dataSetService")
	private IBaseService<DataSet, String> dataSetService;
}
