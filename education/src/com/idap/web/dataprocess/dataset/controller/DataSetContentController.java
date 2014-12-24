package com.idap.web.dataprocess.dataset.controller;

import java.math.BigDecimal;
import java.text.ParseException;
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

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.entity.DataSetMaintainLog;
import com.idap.dataprocess.dataset.service.DataSetContentService;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.dataset.utils.RegexUtils;
import com.idap.web.dataprocess.definition.controller.DataDefinitionController;
import com.idp.pub.constants.Constants;
import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.DateUtil;
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
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/dataSetContent")
public class DataSetContentController extends BaseController<Map<String, Object>, String> {
	private static final Log logger = LogFactory.getLog(DataDefinitionController.class);

	/**
	 * @功能描述：数据集内容分页查询
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	protected Pager findByPager(Pager<Map<String, Object>> pager, @RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		params.put("orderBy", "id");
		params.put("order", "desc");
		String tableName = (String) params.get("tableName");
		Pager<List> pagerNew = service.queryContent(tableName, params, pager);
		// 转换格式
		// 数据Map转List
		List data = new ArrayList<List<Object>>();
		List<List> orgData = pagerNew.getData();
		for (List record : orgData) {
			List<Object> items = new ArrayList<Object>();
			for (Object obj : record) {
				if (obj instanceof java.sql.Timestamp) {
					items.add(DateUtil.dateTimeToStrDefault((Date) obj));
				} else if(obj instanceof java.math.BigDecimal){
					items.add(DataSetUtils.decimalFormat((BigDecimal)obj, 12, null));
				}else{
					items.add(obj);
				}
			}
			data.add(items);
		}
		pagerNew.setData(data);
		return pagerNew;
	}

	/**
	 * @功能描述：数据集记录删除
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		String dataSetId = null;
		String tableName = null;
		try {
			Map<String, Object> params = JsonUtils.toMap(values);
			dataSetId = (String) params.get("dataSetId");
			tableName = (String) params.get("tableName");
			List<String> w_columns = new ArrayList<String>();
			List<Object> w_values = new ArrayList<Object>();
			w_columns.add("ID");
			w_values.add(params.get("id"));
			params.put("w_columns", w_columns);
			params.put("w_values", w_values);
			this.getBaseService().delete(params);
			updateDScount(dataSetId, tableName);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	/**
	 * @功能描述：数据集记录删除
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = "update=true")
	@ResponseBody
	protected Map<String, Object> update(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		Map<String, Object> params = JsonUtils.toMap(values);
		Map<String, Object> updateParams = null;
		Map<String, Object> saveParams = null;
		List<DataSetMaintainLog> editList = new ArrayList<DataSetMaintainLog>();
		String isNew = (String) params.get("isNew");// 0 1
		String tableName = (String) params.get("tableName");//

		try {
			// 1.组织后台更新数据格式
			if ("0".equals(isNew)) {//修改
				updateParams = getEditRecord(params);
				// 2.比对修改记录
				editList = getEditRecordLog(params);
				for (DataSetMaintainLog dsml : editList) {
					dsmlService.save(dsml);
				}
				// 存修改表
				if (editList.size() > 0) {
					this.getBaseService().update(updateParams);// 数据保存
				}
			} else {//新增
				saveParams = getSaveRecord(params);
				this.getBaseService().save(saveParams);

				Map<String, Object> dsParams = new HashMap<String, Object>();
				dsParams.put("tableName", tableName);
				DataSet ds = this.dataSetService.findList(dsParams).get(0);
				ds.setDataAmount(new Long(this.service.count(tableName)));
				this.dataSetService.update(ds);
			}

			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (BusinessException e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
			logger.error(e);
		}catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
			results.put(Constants.MESSAGE, "系统后台错误");
			logger.error(e);
		}
		return results;
	}

	// 整理数据集数据更新参数
	@SuppressWarnings("unchecked")
	private Map<String, Object> getEditRecord(Map<String, Object> params) throws ParseException {
		Map<String, Object> updateParams = new HashMap<String, Object>();
		updateParams.put("values", new ArrayList());
		updateParams.put("wheres", new ArrayList());
		updateParams.put("tableName", params.get("tableName"));
		String name =null;
		String key =null;
		String dataType =null;
		Integer dataLength =null;
		Object val = null;
		List<Map> itemMap = (List<Map>) params.get("columns");
		for (Map itemContent : itemMap) {
			 name = (String) itemContent.get("columnDesc");
			 key = (String) itemContent.get("columnName");
			 dataType = (String) itemContent.get("dataType");
			 dataLength = (Integer) itemContent.get("length");
			 val = itemContent.get("editVal");
			if (DataSetUtils.ID_COL_NAME.equals(key)) {
				Map<String, Object> kv = new HashMap<String, Object>();
				kv.put("key", key);
				kv.put("val", val);
				((List<Map<String, Object>>) updateParams.get("wheres")).add(kv);
				continue;
			}
			val=getElementVal(val,name,dataType,dataLength);
			Map<String, Object> kv = new HashMap<String, Object>();
			kv.put("key", key);
			kv.put("val", val);
			((List<Map<String, Object>>) updateParams.get("values")).add(kv);
		}

		return updateParams;
	}

	// 整理数据集数据保存时的参数
	private Map<String, Object> getSaveRecord(Map<String, Object> params) throws ParseException {
		Map<String, Object> saveParams = new HashMap<String, Object>();
		List<Map> itemMap = (List<Map>) params.get("columns");
		saveParams.put("columns", itemMap);
		saveParams.put("values", new ArrayList());
		saveParams.put("tableName", params.get("tableName"));
		String name =null;
		String key =null;
		String dataType =null;
		Integer dataLength =null;
		Object val = null;
		for (Map itemContent : itemMap) {
			 key = (String) itemContent.get("columnName");
			 val = itemContent.get("editVal");
			 name = (String) itemContent.get("columnDesc");
			 dataType = (String) itemContent.get("dataType");
			 dataLength = (Integer) itemContent.get("length");
			if (DataSetUtils.ID_COL_NAME.equals(key)) {
				continue;
			}
			val=getElementVal(val,name,dataType,dataLength);
			((List<Object>) saveParams.get("values")).add(val);
		}
		return saveParams;
	}
	
	private Object getElementVal(Object val,String name,String dataType,Integer dataLength) throws ParseException{
		String valStr=null;
		if(val!=null){
			if (DataSetUtils.DATA_TYPE_VARCHAR2.equals(dataType)) {
				valStr=(String) val;
				if(RegexUtils.calcDBStringLen(valStr)>dataLength){
					throw new BusinessException(name+":输入字符串过长");
				}
				 val=val.toString();
			}
			if (DataSetUtils.DATA_TYPE_NUMBER.equals(dataType)&&val instanceof String) {
				try{
					if(StringUtils.isNotBlank((String)val)){
//						BigDecimal kk=new BigDecimal("1111111111111111111111111111");
						val=new BigDecimal((String)val);
					}else{
						val=null;
					}
				}catch(NumberFormatException e){
					throw new BusinessException(name+":请输入正确数值");
				}
			}
			if (DataSetUtils.DATA_TYPE_DATE.equals(dataType)) {
				 valStr=(String) val;
				if(StringUtils.isBlank(valStr)){
					val = null;
				}else{
					try{
						val = DataSetUtils.str2date(valStr);
					}catch(NumberFormatException e){
						throw new BusinessException(name+":请输入正确的日期");
					}
				}
			}
		}
		return val;
	}

	// 记录用户修改日志
	private List<DataSetMaintainLog> getEditRecordLog(Map<String, Object> params) throws ParseException {
		List<DataSetMaintainLog> editList = new ArrayList<DataSetMaintainLog>();
		List<Map> itemMap = (List<Map>) params.get("columns");
		Object oldVal = null;
		Object newVal = null;
		String columnName = null;
		params.put("id", itemMap.get(0).get("editVal"));// 主键内容
		List<Map<String, Object>> oldItemList = this.getBaseService().findList(params);
		Map<String, Object> oldItem = oldItemList.get(0);// 行记录
		String rowNumber = itemMap.get(0).get("editVal").toString();// 主键
		String dataSetId = (String) params.get("dataSetId");
		Map user = (Map) params.get("user");

		// 取内容进行比较
			for (Map itemContent : itemMap) {
				columnName = (String) itemContent.get("columnName");
				newVal = itemContent.get("editVal");
				oldVal=oldItem.get(columnName);
				if(oldVal!=null&&newVal!=null){
					if (oldVal instanceof java.sql.Timestamp) {
						oldVal = DateUtil.dateTimeToStrDefault((Date) oldVal);// 统一转为字符串处理
					}
					if (oldVal instanceof BigDecimal) {
						oldVal = ((BigDecimal) oldVal).toString();
					}
//					System.out.println(newVal +"   "+oldVal);
					if (!newVal.equals(oldVal)) {
						DataSetMaintainLog log = new DataSetMaintainLog();
						log.setDataSetId(dataSetId);
						log.setColumnName(columnName);
						log.setRowNumber(rowNumber.toString());
						log.setOldVal(oldVal.toString());
						log.setNewVal(newVal.toString());
						log.setOpDate(new Date());
						log.setOpUser((String) user.get("id"));
						log.setOpStats(DataSetUtils.DS_MAINTAIN_LOG_SUCCESS);
						editList.add(log);
					}
				}else if(!(oldVal==null&&newVal==null)){
					DataSetMaintainLog log = new DataSetMaintainLog();
					log.setDataSetId(dataSetId);
					log.setColumnName(columnName);
					log.setRowNumber(rowNumber.toString());
					log.setOpDate(new Date());
					log.setOpUser((String) user.get("id"));
					log.setOpStats(DataSetUtils.DS_MAINTAIN_LOG_SUCCESS);
					if(oldVal==null){
						log.setOldVal(null);
						log.setNewVal(newVal.toString());
					}else{
						log.setOldVal(oldVal.toString());
						log.setNewVal(null);
					}
					
					editList.add(log);
				}
			}
//		}

		return editList;
	}

	private void updateDScount(String dsId, String tabName) {
		Map dsParams = new HashMap();
		dsParams.put("dataSetId", dsId);
		DataSet ds = dataSetService.findList(dsParams).get(0);
		int count = service.count(tabName);
		ds.setDataAmount(new Long(count));
		dataSetService.update(ds);
	}

	// ========================= get/set ===========================
	@Resource(name = "dataSetContentService")
	public void setBaseService(IBaseService<Map<String, Object>, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "dataSetService")
	IBaseService<DataSet, Long> dataSetService;

	@Resource(name = "dataSetMaintainLogService")
	IBaseService<DataSetMaintainLog, Long> dsmlService;

	@Resource(name = "dataSetContentService")
	private DataSetContentService service;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	protected String buildId() throws BusinessException {
		return generateKeyService.getNextGeneratedKey(null).getNextKey();
	}

}