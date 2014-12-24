package com.idap.web.dataprocess.assess.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.idap.dataprocess.assess.entity.CompleteSituation;
import com.idap.dataprocess.assess.entity.DataSetContour;
import com.idap.dataprocess.assess.service.AssessService;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionAttrService;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
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
@RequestMapping("/assess")
public class AssessController extends BaseController<Map, String> {
	private static final Log logger = LogFactory.getLog(AssessController.class);

	/**
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：执行评估存储过程
	 */
	@RequestMapping(method = RequestMethod.POST, params = "execute=1")
	@ResponseBody
	@SuppressWarnings("unused")
	public Map<String, Object> execute(@RequestParam String dataSetId, String status) {
		Map<String, Object> results = Constants.MAP();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataSetId", dataSetId);
		DataSet ds = dsService.findList(params).get(0);
		try {
			// 1.更新数据集为执行中
			ds.setEvaluateState(DataSetUtils.EXECUTE_SATUS_RUNNING);
			dsService.update(ds);
			map = this.assessService.executeAssess(dataSetId);
			// * out executeState INTEGER --执行结果码 0:成功 非0 ：失败
			// * out executeStep VARCHAR2 --执行结果描述 'OK':成功 否则为错误描述
			BigDecimal executeState = (BigDecimal) map.get("executeState");
			if (executeState.intValue() == 0) {
				// 2.更新为执行成功
				ds.setEvaluateState(DataSetUtils.EXECUTE_SATUS_FINISHED);
				logger.info("executeState:" + map.toString());
			} else {
				// 3.更新为执行失败
				ds.setEvaluateState(DataSetUtils.EXECUTE_SATUS_FAILURE);
				logger.error("executeState:" + map.toString());
			}
			ds.setEvaluateDate(new Date());
			dsService.update(ds);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			// 3.更新为执行失败
			ds.setEvaluateState(DataSetUtils.EXECUTE_SATUS_FAILURE);
			dsService.update(ds);
			logger.error(e);
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	/**
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：获取所有的评估内容 前台报表使用
	 */
	@RequestMapping(params = "isChart=1", method = RequestMethod.POST)
	@ResponseBody
	public Map queryChartData(@RequestParam String dataSetId, @RequestParam String dataDefId,@RequestParam String dataSourceType) {
		Map<String, Object> results = Constants.MAP();
		int notBlankCount = 0;
//		Long filterCount = 0l;
		try {
			List<CompleteSituation> chart3List = this.assessService.assessStatistics(dataSetId);
			List<DataSetContour> chart1List = this.assessService.queryColAssessInfo(dataSetId);
			List<DataSetContour> chart1ListOrder = new ArrayList<DataSetContour>();
			List<DataDefinitionAttr> columnsList = getColumnsList(dataDefId,dataSourceType);
			Map<Object, Object> chart1Map = DataSetUtils.list2map(chart1List, "colName");
//			for (CompleteSituation cs : chart3List) {
//				filterCount += cs.getCount();
//			}
			//转换字段名 为 中文名
			for (CompleteSituation cs : chart3List) {
				cs.converColumns(columnsList);
			}
			for (DataDefinitionAttr col : columnsList) {
				DataSetContour dsc = (DataSetContour) chart1Map.get(col.getColumnName());
				if (dsc != null) {
					notBlankCount += (dsc.getRowCount() - dsc.getNoValueCount() - dsc.getNullCount());
					dsc.setColShowName(col.getColumnDesc());//更新前台要显示的中文名
					chart1ListOrder.add(dsc);
				}
			}
			if (chart1List.get(0) != null) {
				results.put("dataTotalCount", chart1List.get(0).getRowCount());
			}

			results.put("colomnSize", chart1List.size());// 字段个数
			results.put("chart1List", chart1ListOrder);
			results.put("chart3List", chart3List);
			results.put("notBlankCount", notBlankCount);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (java.lang.ArithmeticException e) {
			logger.error(e);
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, "数据集为空");
		} catch (Exception e) {
			logger.error(e);
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	private List<DataDefinitionAttr> getColumnsList(String dataDefId,String dataSourceType){
		List<DataDefinitionAttr> columnsList = null;
		// 接口数据查询 dataDefId 些内容为表名
				if (StringUtils.isNotBlank(dataSourceType) && dataSourceType.toLowerCase().equals(DataSetUtils.DS_SOURCE_TYPE_INTERFACE)) {
					columnsList = getColumnsForInterfaceData(dataDefId);
				} else {
					// 实验数据查询
					columnsList = getColumns(dataDefId);
				}
				for(DataDefinitionAttr attr:columnsList){
					attr.setColumnName(attr.getColumnName().toUpperCase());
				}
		
		return columnsList;
	}

	/**
	 * 对于接口数据来说 dataDefId 是表名
	 * 
	 * @return
	 */
	private List<DataDefinitionAttr> getColumnsForInterfaceData(String tableName) {
		List<DataDefinitionAttr> items=columnsService.findForDicColumn(tableName);
		for(DataDefinitionAttr attr:items){
			if(StringUtils.isBlank(attr.getColumnDesc())){
				attr.setColumnDesc(attr.getColumnName());
			}
		}
		return items;
	}
	
	/**
	 * 按顺序查找
	 * 
	 * @return
	 */
	private List<DataDefinitionAttr> getColumns(String dataDefId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderBy", "DISPLAY_COL_NUM");
//		params.put("isDisplay", DataSetUtils.SWITCH_TRUE);
		params.put("dataDefId", dataDefId);

		return columnsBaseService.findList(params);
	}

	// ========================= get/set ===========================
	@Resource(name = "assessService")
	public void setBaseService(IBaseService<Map, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "assessService")
	private AssessService assessService;

	@Resource(name = "dataDefinitionAttrService")
	private DataDefinitionAttrService<DataDefinitionAttr, String> columnsService;

	@Resource(name = "dataSetService")
	private IBaseService<DataSet, String> dsService;

	@Resource(name = "dataDefinitionAttrService")
	private IBaseService<DataDefinitionAttr, String> columnsBaseService;

}