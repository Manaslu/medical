package com.idap.dataprocess.dataset.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.dao.DataSetContentDao;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.entity.DataUploadDownloadLog;
import com.idap.dataprocess.dataset.service.BatchExecutor;
import com.idap.dataprocess.dataset.service.DataSetService;
import com.idap.dataprocess.dataset.service.batch.ParameterSetter;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.dataset.utils.FileReadContent;
import com.idap.dataprocess.dataset.utils.FileReadUtils;
import com.idap.dataprocess.dataset.utils.RegexUtils;
import com.idap.dataprocess.dataset.vo.UploadFileContent;
import com.idap.dataprocess.definition.dao.CountDao;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionService;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-18 11:47:20
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@SuppressWarnings("unchecked")
@Service("dataSetService")
@Transactional
public class DataSetServiceImpl extends DefaultBaseService<DataSet, String> implements DataSetService {
	private static final Log logger = LogFactory.getLog(DataSetService.class);

	/**
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：删除数据集记录、对应的数据集表
	 * @param params 只需要 dataSetId 用Map 做入参 主要是兼容delete接口
	 * @return 操作记录条数
	 */
	@Override
	public Integer delete(Map<String, Object> params) {
		DataSet entity = new DataSet();
		entity.setDataSetId((String) params.get("dataSetId"));
		return this.deleteCascade(entity);
	}

	/**
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：删除数据集记录、对应的数据集表
	 * @param ds 实体对像
	 * @return 操作记录条数
	 */
	public Integer deleteCascade(DataSet ds) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			ds = this.getBaseDao().get(ds.getDataSetId());
			ds.setValidFlag(DataSetUtils.SWITCH_FALSE);
			dropDataSet(ds.getTableName());
			super.getBaseDao().update(ds);
			params = new HashMap<String, Object>();
			params.put("resultDataSet", ds);
			List<DataInteLog> dils = dataInteLogDao.find(params);
			if (dils.size() > 0) {
				this.tableInteRuleService.deleteTableRule(dils.get(0).getRuleId());
			}
			// TODO 李广彬　功能补全
			// 0.查询子过程产生的表
			// 1.删除 字段整合规则
			// 2.删除 表整合规则
			// 3.删除 整合日志
			// 4.删除 数据清洗报告
			// 5.删除 数据集轮廓表
			// 5.数据服务流程 的清理规则
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：级联保存：（实验数据上传） 1.保存数据定义 -》写上传日志 1.信息组织给存储过程（上传）
	 * @param bo 数据定义和相应字段信息
	 * @param uploadFile 上传文件
	 * @param dataSet 数据集信息
	 * @param splitSeparate 分隔符
	 * @param firstIsTitle 第一行是否是表头
	 */
	public DataSet saveUploadCascade(DataDefinition bo, File uploadFile, DataSet dataSet, String splitSeparate,
			String firstIsTitle) throws Exception {
		List<List<String>> errorLine = new ArrayList<List<String>>();
		File errorFile = buildErrorFile(uploadFile);
		// 1.保存关系统 [新建、已存在]
		if (bo.getId() == null) {
			dataDefinitionService.saveDefinitionCascade(bo);// 级联保存数据定义和字段
		}
		// 2.创建表
		bo = createDataDefTbl(bo);
		dataSet = createDataSetTbl(bo, dataSet);
		// 3.导数据
		errorLine = dsImport(bo, dataSet, uploadFile, splitSeparate, firstIsTitle);
		// 4.错误数据输出
		if (errorLine != null && errorLine.size() > 0) {
			writeErrorFile(bo.getColumns(), errorLine, errorFile);
			dataSet.setErrorFile(errorFile);
		}
		// 4.更新数据集记录数
		updateDSCount(dataSet);

		return dataSet;
	}

	// 错误文件路径生成 (错误文件名：原文件名_ERR.txt)
	private File buildErrorFile(File uploadFile) {
		String filePath = uploadFile.getPath();
		String errorFilePath = filePath.substring(0, filePath.lastIndexOf('.'))
				+ DataSetUtils.DS_UPLOAD_ERROR_FILE_SUFFIX + DataSetUtils.SUFFIX_TXT;
		return new File(errorFilePath);
	}

	// 更新最终的数据集数量
	private DataSet updateDSCount(DataSet dataSet) {
		if (dataSet.getDataAmount() == null) {
			dataSet.setDataAmount(Long.valueOf(dataSet.getImpSuccessCount()));
		} else {
			dataSet.setDataAmount(dataSet.getDataAmount() + Long.valueOf(dataSet.getImpSuccessCount()));
		}
		this.update(dataSet);
		return dataSet;
	}

	// 创建数据集表
	private DataDefinition createDataDefTbl(DataDefinition bo) {
		// 不存在再建
		List<DataDefinitionAttr> attrs = bo.getColumns();
		String tableName = bo.getTabName();
		if (!this.commonDao.tableExist(tableName)) {
			this.commonDao.createTable(tableName, attrs);
		}
		return bo;
	}

	// 创建数据集表
	private DataSet createDataSetTbl(DataDefinition bo, DataSet ds) throws BusinessException {
		if (StringUtils.isBlank(ds.getDataSetId())) {// 追加和新增
			ds.setDataDefId(bo.getDataDefId());
			ds.setDataDefName(bo.getDataDefName());
			ds.setDataSetId(this.buildId());
			ds.setDataAmount(0L);
			ds.setEvaluateState(DataSetUtils.EXECUTE_SATUS_INIT);
			ds.setAutoManu(DataSetUtils.DATA_SET_CREATE_TYPE_HANDMADE);
			this.getBaseDao().save(ds);
			ds.setTableName(DataSetUtils.DATA_SET_PREFIX + ds.getId());
			this.getBaseDao().update(ds);
			List<DataDefinitionAttr> attrs = bo.getColumns();
			String tableName = ds.getTableName();
			this.commonDao.createTable(tableName, attrs);
			// this.commonDao.createSequence(tableName);
		} else {
			ds = this.getBaseDao().get(ds.getDataSetId());
		}
		return ds;
	}

	// 文件内容读取
	private UploadFileContent readFileCountent(final HSSFSheet xls, HSSFFormulaEvaluator Hevaluator,
			final XSSFSheet xlsx, XSSFFormulaEvaluator Xevaluator, final BufferedReader lnReader,
			final String splitSeparate, final String firstIsTitle, final int startLine, final int lineCount,
			final int colSize) throws IOException {
		if (xls != null) {
			return FileReadContent.readXlsFileSegment(xls, firstIsTitle, startLine, lineCount, colSize, Hevaluator);
		} else if (xlsx != null) {
			return FileReadContent.readXlsxFileSegment(xlsx, firstIsTitle, startLine, lineCount, colSize, Xevaluator);
		} else if (lnReader != null) {
			return FileReadContent.readTxtFileSegment(lnReader, splitSeparate, firstIsTitle, lineCount, colSize);
		}
		return null;
	}

	// 数据导入 成功的导入 失败的导入的文本文件中
	@SuppressWarnings("rawtypes")
	private List<List<String>> dsImport(DataDefinition bo, DataSet dataSet, File uploadFile, String splitSeparate,
			String firstIsTitle) throws IOException {
		UploadFileContent resultContent = new UploadFileContent();// 读取的文件内容对像
		String dataSetTblName = dataSet.getTableName();// 数据库表名
		List<List<String>> errorLine = new ArrayList<List<String>>();// 错误数据列表
		String fileName = uploadFile.getName();//
		String fileType = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();// 文件扩展名
		HSSFSheet xls = null;
		XSSFSheet xlsx = null;
		HSSFFormulaEvaluator Hevaluator = null;
		XSSFFormulaEvaluator Xevaluator = null;
		List<Object> values = null;// 转换为实际对像类型的数据
		List<String> itemNew = null;//
		BufferedReader lnReader = null;// 读取流
		int pageSize = 2000;// 分页记录
		int pageNo = 0;// 页码
		int impRecoreCount = 0;// 导入记录数
		int impSuccessCount = 0;// 导入成功记录数
		int impFailCount = 0;// 导入失败记录数
		int colSize = bo.getColumns().size() - 1;// 去除ID之后的数量

		int redIndex = commonDao.maxId(dataSetTblName);
		try {
			if (DataSetUtils.SUFFIX_TXT.equals(fileType)) {
				lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(uploadFile),
						FileReadUtils.getFileEncoding(uploadFile)));
			} else if (DataSetUtils.SUFFIX_XLS.equals(fileType) || DataSetUtils.SUFFIX_ET.equals(fileType)) {
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(uploadFile));
				xls = workbook.getSheetAt(0);
				Hevaluator = workbook.getCreationHelper().createFormulaEvaluator();
			} else if (DataSetUtils.SUFFIX_XLSX.equals(fileType)) {
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(uploadFile));
				xlsx = workbook.getSheetAt(0);
				Xevaluator = workbook.getCreationHelper().createFormulaEvaluator();
			}
			resultContent = readFileCountent(xls, Hevaluator, xlsx, Xevaluator, lnReader, splitSeparate, firstIsTitle,
					pageNo++, pageSize, colSize);
			while (resultContent.getContent().size() > 0) {
				List<List> allItem = resultContent.getContent();
				List<Object[]> allItemNew = new ArrayList<Object[]>();
				for (int i = 0; i < allItem.size(); i++) {
					try {
						values = convertType(bo.getColumns(), allItem.get(i));
						if (values != null) {
							redIndex++;
							itemNew = new ArrayList<String>();
							itemNew.add(redIndex + "");
							itemNew.addAll(allItem.get(i));
							allItemNew.add(itemNew.toArray());
							impSuccessCount++;
						}
					} catch (Exception e) {
						logger.info(e);
						errorLine.add(allItem.get(i));
						impFailCount++;
					}
					impRecoreCount++;
				}
				if (allItemNew.size() > 0) {
					batchInsert(bo.getColumns(), allItemNew, dataSetTblName, pageSize);
				}
				if (resultContent.getContent().size() < pageSize) {
					break;
				}
				resultContent = readFileCountent(xls, Hevaluator, xlsx, Xevaluator, lnReader, splitSeparate,
						firstIsTitle, (pageNo++ * pageSize), pageSize, colSize);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (lnReader != null)
				lnReader.close();
		}
		dataSet.setImpRecoreCount(impRecoreCount);
		dataSet.setImpFailCount(impFailCount);
		dataSet.setImpSuccessCount(impSuccessCount);
		return errorLine;
	}

	/**
	 * 数据批量插入
	 * 
	 * @param columns 所有要插入的列信息
	 * @param dataList 所有要插入的数据列表
	 * @param tabName 表名称 t_base_user
	 * @param batchSize 分批数据量
	 */
	private void batchInsert(final List<DataDefinitionAttr> columns, final List<Object[]> dataList,
			final String tabName, final int batchSize) {
		batchExecutor.batchUpdate(dataList, new ParameterSetter() {
			@Override
			public int batchSize() {
				return batchSize;
			}

			@Override
			public List<DataDefinitionAttr> getAttrs() {
				return columns;
			}

			@Override
			public String getTableName() {
				return tabName;
			}
		});
	}

	// 将错误的数据集信息回写到文本文件内
	private File writeErrorFile(List<DataDefinitionAttr> columns, List<List<String>> list, File errorFile)
			throws IOException {
		// FileOutputStream outputStream = new FileOutputStream(errorFile);
		// OutputStreamWriter fw = new OutputStreamWriter(outputStream,"GBK");
		OutputStreamWriter fw = new FileWriter(errorFile);
		try {
			if (!errorFile.exists()) {
				errorFile.createNewFile();
			}
			// fw = new FileWriter(errorFile);
			StringBuffer buffer = new StringBuffer();
			for (DataDefinitionAttr col : columns) {
				if (col.getColumnName().equals(DataSetUtils.ID_COL_NAME)) {
					continue;
				}
				buffer.append(col.getColumnDesc());
				if (columns.indexOf(col) != (columns.size() - 1)) {
					buffer.append("\t");
				}
			}
			buffer.append("\r\n");
			fw.write(buffer.toString());
			for (List<String> items : list) {
				buffer = new StringBuffer("");
				for (String str : items) {
					buffer.append(str);
					if (items.indexOf(str) != (items.size() - 1)) {
						buffer.append("\t");
					}
				}
				buffer.append("\r\n");
				fw.write(buffer.toString());
				// System.out.println(buffer.toString());
			}

		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (fw != null)
				fw.close();
		}
		return errorFile;
	}

	// 将一条记录转为对应数据类型的数据
	private List<Object> convertType(List<DataDefinitionAttr> attrs, List<String> list) throws ParseException {
		List<Object> newList = new ArrayList<Object>();
		boolean isNullList = true;// 当前行内的数据是否全为空 相当于 excel内的空列
		int i = 0;
		for (DataDefinitionAttr attr : attrs) {
			String colStr = list.get(i);
			if (attr.getColumnName().equals(DataSetUtils.ID_COL_NAME)) {
				continue;
			}
			if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_VARCHAR2)) {
				if (RegexUtils.calcDBStringLen(colStr) > attr.getLength()) {
					throw new BusinessException("实验数据上传数据超长[column:" + attr.getColumnDesc() + "\tlength:"
							+ attr.getLength() + "\tcontent:" + colStr + "]");
				}
			}
			if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_NUMBER)) {
				if (colStr.length() > 38) {
					throw new BusinessException("实验数据上传数据超长[column:" + attr.getColumnDesc() + "\tlength:38\tcontent:"
							+ colStr + "]");
				}
			}
			newList.add(DataSetUtils.buildColumn2JavaObject(colStr, attr.getDataType()));
			i++;
		}

		for (Object obj : newList) {
			if (isNullList && obj instanceof String && StringUtils.isNotBlank((String) obj)) {
				isNullList = false;
			}
			if (isNullList && obj != null) {
				isNullList = false;
			}
		}
		if (isNullList) {
			newList = null;
		}
		return newList;
	}

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：实验数据下载 对应存储过程生成下载文件 回写文件路径 （zip 压缩文件） 直支持txt
	 * @param tableName 表名称
	 * @param cols 字段信息
	 * @param basePath 下载的基准路径
	 * @return 下载文件地址
	 */
	// @SuppressWarnings("rawtypes")
	// public String dataSetDownload(String tableName, List<DataDefinitionAttr>
	// cols, String basePath) throws IOException {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("tableName", tableName);
	// params.put("columns", cols);
	// Pager<Map> pager = new Pager<Map>();
	// String fileName = DataSetUtils.buildNewFileName("aa.txt");
	// File file = new File(basePath, fileName);
	// FileWriter fw = null;
	// try {
	// File basePathDir = new File(basePath);
	// if (!basePathDir.exists()) {
	// basePathDir.mkdirs();
	// }
	// if (!file.exists()) {
	// file.createNewFile();
	// }
	// fw = new FileWriter(file);
	// pager.setLimit(2000);
	// pager.setCurrent(0);
	// pager.setReload(true);
	// pager = contentPageDao.findByPager(pager, params);
	// dataSetTitle2txt(fw, cols);
	// for (int i = 0; i < pager.getTotalPage(); i++) {
	// pager.setCurrent(i);
	// pager = contentPageDao.findByPager(pager, params);
	// dataSet2txt(fw, pager.getData(), cols);
	// }
	// } finally {
	// fw.close();
	// }
	//
	// File zipFile = ZipUtils.compress(file);
	// fileName = zipFile.getName();
	// file.delete();
	// return fileName;
	// }
	//
	// // 数据集表头转储到文本文件
	// private FileWriter dataSetTitle2txt(FileWriter fw,
	// List<DataDefinitionAttr> attrs) throws IOException {
	// StringBuffer buffer = new StringBuffer();
	// for (DataDefinitionAttr attr : attrs) {
	// buffer.append(attr.getColumnDesc());
	// buffer.append("\t");
	// }
	// buffer.append("\n");
	// fw.write(buffer.toString());
	// return fw;
	// }
	//
	// // 数据集内容转储到文本文件
	// private FileWriter dataSet2txt(FileWriter fw, List<Map> orgData,
	// List<DataDefinitionAttr> attrs) throws IOException {
	// StringBuffer buffer = new StringBuffer();
	// for (Map<String, Object> item : orgData) {
	// for (DataDefinitionAttr attr : attrs) {
	// Object obj = item.get(attr.getColumnName().toUpperCase());
	// if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_VARCHAR2)) {
	// buffer.append(obj);
	// } else if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_NUMBER)) {
	// buffer.append(obj.toString());
	// } else if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_DATE)) {
	// buffer.append(DateUtil.dateTimeToStrDefault((java.util.Date) obj));
	// }
	// buffer.append("\t");
	// }
	// buffer.append("\n");
	// }
	// fw.write(buffer.toString());
	// return fw;
	// }

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：物理删除数据集
	 * @传入参数：
	 */
	private int dropDataSet(String tableName) {
		this.commonDao.dropTable(tableName);
		// this.commonDao.dropSequence(tableName);
		return 0;
	}

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：统计数据集个数
	 * @传入参数：
	 */
	@Override
	public Integer count(Map<String, Object> params) {
		return this.countDao.count(params);
	};

	/**
	 * 根据评估结果查询数据定义
	 */
	@Override
	public Pager<DataSet> findByPagerForDsContour(Pager<DataSet> pager, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getPagerDao().findByPager(KEY_FIND_BY_PAGER_FOR_DS_CONTOUR, pager, params);
	}

	// ---------------------------------------------------------
	public static final String KEY_FIND_BY_PAGER_FOR_DS_CONTOUR = "findByPagerForDsContour";
	@Resource(name = "dataUploadDownloadLogDao")
	private IBaseDao<DataUploadDownloadLog, Long> logDao;
	@Resource(name = "dataSetContentDao")
	private DataSetContentDao commonDao;

	@Resource(name = "dataSetContentDao")
	private IBaseDao<Map, String> contentDao;

	@Resource(name = "dataSetContentDao")
	private IPagerDao<Map> contentPageDao;

	@Resource(name = "dataDefinitionService")
	private DataDefinitionService dataDefinitionService;

	@Resource(name = "dataSetDao")
	private CountDao countDao;

	@Resource(name = "dataSetDao")
	public void setBaseDao(IBaseDao<DataSet, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "dataSetDao")
	public void setPagerDao(IPagerDao<DataSet> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	@Resource(name = "dataDefinitionAttrDao")
	private IBaseDao<DataDefinitionAttr, String> attrDao;
	@Resource(name = "dataDefinitionDao")
	private IBaseDao<DataDefinition, String> defDao;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	protected String buildId() throws BusinessException {
		return generateKeyService.getNextGeneratedKey(null).getNextKey();
	}

	@Resource(name = "batchExecutor")
	private BatchExecutor batchExecutor;

	@Resource(name = "dataInteLogDao")
	private IBaseDao<DataInteLog, String> dataInteLogDao;

	@Resource(name = "tableInteRuleService")
	public TableInteRuleService tableInteRuleService;

	@Override
	public String dataSetDownload(String tableName, List<DataDefinitionAttr> cols, String basePath) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
