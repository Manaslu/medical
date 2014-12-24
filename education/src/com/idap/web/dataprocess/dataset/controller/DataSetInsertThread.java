package com.idap.web.dataprocess.dataset.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.entity.DataUploadDownloadLog;
import com.idap.dataprocess.dataset.service.DataSetService;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionService;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;

public class DataSetInsertThread implements Runnable {
	private static final Log logger = LogFactory.getLog(DataSetInsertThread.class);
	private DataSetService dataSetService;
	private IBaseService<DataUploadDownloadLog, String> logService;
	private Map<String, Object> params;
	private DataDefinitionService defService;
	private IGenerateKeyMangerService generateKeyService;

	public DataSetInsertThread(Map<String, Object> params, DataSetService dataSetService,
			IBaseService<DataUploadDownloadLog, String> logService, DataDefinitionService defService,
			IGenerateKeyMangerService generateKeyService) {
		this.params = params;
		this.dataSetService = dataSetService;
		this.logService = logService;
		this.defService = defService;
		this.generateKeyService = generateKeyService;

	}

	public void run() {
		String dataDefId = (String) params.get("dataDefId");
		String filePath = (String) params.get("filePath");
		String orgFileName = (String) params.get("orgFileName");
		String dsFileRealPath = filePath;
		File uploadFile = new File(dsFileRealPath);
		String dataSetName = (String) params.get("dataSetName");
		String dataSetId = (String) params.get("dataSetId");
		String dataDesc = (String) params.get("dataDesc");
		String separate = (String) params.get("separate");
		String otherSeparate = (String) params.get("otherSeparate");
		String firstIsTitle = (String) params.get("firstIsTitle");
		String userId = (String) params.get("userId");
		firstIsTitle = StringUtils.isNotBlank(firstIsTitle) ? firstIsTitle : "0";
		List<Map> columns = (List) params.get("columns");
		DataDefinition def = null;
		String splitSeparate = null;
		String errorFileName = null;
		DataSet dataSet = new DataSet();
		DataUploadDownloadLog logBO = new DataUploadDownloadLog();
		logBO.setOpStartDate(new Date());
		if (separate.equals("other")) {
			splitSeparate = otherSeparate;
		} else {
			splitSeparate = separate;
		}
		dataSet.setDataSetId(dataSetId);
		dataSet.setDataDesc(dataDesc);
		dataSet.setDataSetName(dataSetName);
		dataSet.setCreateDate(new Date());
		dataSet.setCreateUser(userId);
		logBO.setOpType(DataSetUtils.DS_LOG_TYPE_UPLOAD);
		logBO.setOpUser(userId);
		logBO.setDataSetId(dataSetId);
		logBO.setDataSetName(dataSetName);
		logBO.setSourFileDir(dsFileRealPath);
		logBO.setSourFileName(orgFileName);
		try {
			// 选择数据
			if (StringUtils.isBlank(dataDefId) && columns.size() > 0) {
				def = ConvertDataDef(columns, dataSetName, userId);
			} else {// 已定义
				def = defService.queryDefinitionCascade(dataDefId);
			}

			dataSet = dataSetService.saveUploadCascade(def, uploadFile, dataSet, splitSeparate, firstIsTitle);
			// 记录上传日志
			logBO.setDataSetId(dataSet.getDataSetId());
			logBO.setDataCount(dataSet.getDataAmount());// 数据集内的记录数
			logBO.setSuccDataCnt(dataSet.getImpSuccessCount());
			logBO.setFailDataCnt(dataSet.getImpFailCount());
			logBO.setOpEndDate(new Date());
			logBO.setOpStats(DataSetUtils.DS_LOG_OPT_STATUS_SUCCESS);
			
			if (dataSet.getErrorFile() != null) {// txt 路径 非打包后数据
				String orgFileNameExtFileType=orgFileName.substring(0, orgFileName.lastIndexOf("."));
				errorFileName = DataSetUtils.buildErrorFileName(dataSet.getErrorFile().getName(),
						orgFileNameExtFileType);
				logBO.setErrFileName(errorFileName);
				logBO.setErrFileDir(dataSet.getErrorFile().getPath());
			}
			logBO = logService.save(logBO);
		} catch (Exception e) {
			e.printStackTrace();
			logBO.setOpEndDate(new Date());
			logBO.setOpStats(DataSetUtils.DS_LOG_OPT_STATUS_ERROR);
			logBO = logService.save(logBO);
			logger.error(e);
		}
	}

	/**
	 * 将map 转为对像
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @author 李广彬
	 * @param columns
	 * @param dataDefName
	 */
	private DataDefinition ConvertDataDef(List<Map> columns, String dataDefName, String userId)
			throws BusinessException {
		DataDefinition def = new DataDefinition();
		List<DataDefinitionAttr> columnList = new ArrayList();
		def.setBusinessType(DataSetUtils.DEF_BIZ_TYPE_MANUAL);
		def.setDataDefId(buildId());
		def.setDataDefName(dataDefName);
		def.setDataDefDesc(dataDefName);
		def.setCreateUser(userId);
		def.setCreateDate(new Date());
		def.setGroupId(this.buildId());
		int i = 0;

		for (Map col : columns) {
			i++;
			DataDefinitionAttr attr = new DataDefinitionAttr();
			attr.setId(buildId());
			attr.setColumnId(buildId());
			attr.setDataDefId(def.getDataDefId());
			attr.setColumnName((String) col.get("columnName"));
			attr.setColumnDesc((String) col.get("columnDesc"));
			attr.setDataType((String) col.get("dataType"));
			attr.setLength(DataSetUtils.obj2integer(col.get("length")));
			attr.setConceptModelId((String) col.get("conceptModelId"));
			attr.setColNum(i);
			columnList.add(attr);
		}
		def.setColumns(columnList);
		return def;
	}

	protected String buildId() throws BusinessException {
		return generateKeyService.getNextGeneratedKey(null).getNextKey();
	}

}