package com.idap.web.dataprocess.dataset.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.danalysis.service.IDataMapService;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.entity.DataUploadDownloadLog;
import com.idap.dataprocess.dataset.service.DataSetContentService;
import com.idap.dataprocess.dataset.service.DataSetService;
import com.idap.dataprocess.dataset.utils.AntZipUtils;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.dataset.utils.FileReadContent;
import com.idap.dataprocess.dataset.utils.RegexUtils;
import com.idap.dataprocess.dataset.vo.UploadFileContent;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionService;
import com.idap.drad.entity.SystemCode;
import com.idap.web.common.controller.Commons;
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
@Controller
@RequestMapping("/dataSet")
public class DataSetController extends BaseController<DataSet, String> {
	private static final Log logger = LogFactory.getLog(DataSetController.class);
	private static final String QUERY_GROUP = "queryGroup";
	private static final String QUERY_ADDRESS = "queryAddress";

	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	protected Pager<DataSet> findByPager(Pager<DataSet> pager, @RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		String dataSourceType = (String) params.get("dataSourceType");
		// 接口数据查询
		try{
			if (StringUtils.isNotBlank(dataSourceType) && dataSourceType.toLowerCase().equals(DataSetUtils.DS_SOURCE_TYPE_INTERFACE)) {
				pager = findByPagerForInterfaceData(pager, params);
			} else {
				// 实验数据查询
				if (StringUtils.isBlank((CharSequence) params.get("validFlag"))) {
					params.put("validFlag", DataSetUtils.SWITCH_TRUE);
				}
				pager = this.getBaseService().findByPager(pager, params);
			}
		}catch(Exception e){
			logger.error(e);
		}
		return pager;
	}

	private Pager<DataSet> findByPagerForInterfaceData(Pager<DataSet> pager, Map<String, Object> params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<SystemCode> codes=systemCodeService.findList(new HashMap<String,Object>());
		Map codesMap=DataSetUtils.list2map(codes, "portcode");
		pager=this.dataSetService.findByPagerForDsContour(pager, params);
		String sysCode=null;
		SystemCode code=null;
		for(DataSet ds:pager.getData()){
			sysCode=ds.getTableName().substring(0, 3);
			code=(SystemCode) codesMap.get(sysCode);
			ds.setSystemName(code.getName());
		}
		
		return pager;
	}

	// ----------------文件上传 起-------------------
	/**
	 * @功能描述：文件上传 读取预览内容
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 * @param req httpServletRequest
	 * @param filePath 文件地址
	 * @param dataSetDesc 数据集说明
	 * @param dataSetName 数据集名称
	 * @param otherSeparate 选择其它时的用户输入的分隔符
	 * @param dataDefId 数据定义主键
	 * @param firstIsTitle 第一行是否为标题
	 * @param separate 预置分隔符
	 * @return
	 */
	@RequestMapping(params = "method=upload", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	protected Map fileUpload(HttpServletRequest req, @RequestParam(required = true) String filePath,
			String dataSetDesc, String dataSetName, String otherSeparate, String dataDefId, String firstIsTitle,
			String separate) throws BusinessException {
		Map<String, Object> resutlMap = new HashMap<String, Object>();
		// 文件操作
		String dsFileRealPath = filePath;
		File targetFile = new File(dsFileRealPath);
		int colSize = 0;// 列数
		List<DataDefinitionAttr> columns = new ArrayList<DataDefinitionAttr>();
		try {
			if (StringUtils.isNotBlank(dataDefId)) {
				Map<String, Object> attrParams = new HashMap<String, Object>();
				attrParams.put("dataDefId", dataDefId);
				List<DataDefinitionAttr> attrs = this.attrService.findList(attrParams);
				colSize = attrs.size() - 1;// 去ID
			}
			firstIsTitle = StringUtils.isBlank(firstIsTitle) ? "1" : firstIsTitle;
			// 内容读取
			UploadFileContent content = this.fileReader(targetFile, separate, otherSeparate, firstIsTitle, colSize);
			// 类型推测
			if (content.getContent().size() > 0) {
				columns = guestType((List<String>) content.getContent().get(0), content.getTitle());
			}
			resutlMap.put("content", content);
			resutlMap.put("columns", columns);
			resutlMap.put("filePath", filePath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

		return resutlMap;
	}

	/**
	 * 文件分类型读取 统计下阶段的内容格式
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @author 李广彬
	 * @param file 上传文件
	 * @param splitSeparate 未转换的标识
	 * @param firstIsTitle 0:否 1:是
	 * @param colSize 当为0时会根据第一行数据来确定
	 * @return
	 * @throws IOException
	 */
	private UploadFileContent fileReader(File file, String separate, String otherSeparate, String firstIsTitle,
			final int colSize) throws IOException {
		String splitSeparate;
		UploadFileContent content = null;
		String fileName = file.getName();
		String fileExtName = fileName.substring(fileName.lastIndexOf('.'));
		// 分隔附转换
		if (separate.equals("other")) {
			splitSeparate = otherSeparate;
		} else {
			splitSeparate = separate;
		}
		// 内容读取
		if (fileExtName.toLowerCase().equals(DataSetUtils.SUFFIX_TXT)) {
			content = FileReadContent.readTxtFile(file, splitSeparate, firstIsTitle, 5, colSize);
		} else if (fileExtName.toLowerCase().equals(DataSetUtils.SUFFIX_XLS)
				|| fileExtName.toLowerCase().equals(DataSetUtils.SUFFIX_ET)) {
			content = FileReadContent.readXlsFile(file, firstIsTitle, 5, colSize);
		} else if (fileExtName.toLowerCase().equals(DataSetUtils.SUFFIX_XLSX)) {
			content = FileReadContent.readXlsxFile(file, firstIsTitle, 5, colSize);
		}
		return content;
	}

	/**
	 * 推测数据类型
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @author 李广彬
	 * @param items 读取的列数据
	 * @param titles 标题信息
	 */
	private List<DataDefinitionAttr> guestType(List<String> items, List<String> titles) {
		List<DataDefinitionAttr> guestType = new ArrayList<DataDefinitionAttr>();
		int i = 0;
		String itemNew = null;
		for (String item : items) {
			itemNew = item;// TODO 匹配前需不需需要去空格
			i++;
			DataDefinitionAttr type = new DataDefinitionAttr();
			type.setColNum(i);
			type.setColumnName(DataSetUtils.PRIFIX_COLUMN_NAME + i);
			if (StringUtils.isBlank(itemNew)) {// 空字段 默认值
				type.setDataType(DataSetUtils.DATA_TYPE_VARCHAR2);
				type.setLength(DataSetUtils.DATA_TYPE_VARCHAR2_LENGTH);
				initColDesc(type, titles, i);
				guestType.add(type);
			} else {// 存在类型
				if (RegexUtils.testStartZero(itemNew) || RegexUtils.testIdCard(itemNew)
						|| RegexUtils.testMobile(itemNew) || RegexUtils.testPost(itemNew)
						|| RegexUtils.testPhone(itemNew)) {// 以0开头的整数认为是字符串
					type.setDataType(DataSetUtils.DATA_TYPE_VARCHAR2);
					type.setLength(DataSetUtils.DATA_TYPE_VARCHAR2_LENGTH);
					initColDesc(type, titles, i);
					guestType.add(type);
				} else if (RegexUtils.testNumber(itemNew) && itemNew.length() <= 38) {// 数字
					type.setDataType(DataSetUtils.DATA_TYPE_NUMBER);
					type.setLength(null);
					initColDesc(type, titles, i);
					guestType.add(type);
				} else if (RegexUtils.testDate(itemNew) || RegexUtils.testDate(itemNew, "/")
						|| RegexUtils.testDateTime(itemNew) || RegexUtils.testDateTime(itemNew, "/")) {// 日期
					type.setDataType(DataSetUtils.DATA_TYPE_DATE);
					type.setLength(null);
					initColDesc(type, titles, i);
					guestType.add(type);
				} else {// 默认字符串
					type.setDataType(DataSetUtils.DATA_TYPE_VARCHAR2);
					type.setLength(DataSetUtils.DATA_TYPE_VARCHAR2_LENGTH);
					initColDesc(type, titles, i);
					guestType.add(type);
				}
			}
		}
		return guestType;
	}

	/**
	 * 列名初始化设置
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @author 李广彬
	 * @param type 字段信息
	 * @param titles 标题信息
	 * @param index 字段序号
	 */
	private void initColDesc(DataDefinitionAttr type, List<String> titles, int index) {
		try {
			if (titles.size() > 0) {
				if (StringUtils.isBlank(titles.get(index - 1))) {// 标题为空时自己生成
					type.setColumnDesc(DataSetUtils.PRIFIX_COLUMN_NAME + index);
				} else {
					type.setColumnDesc(StringUtils.substring(titles.get(index - 1), 0, 20));
				}
			} else {// 标题设置时生成规则
				type.setColumnDesc(DataSetUtils.PRIFIX_COLUMN_NAME + index);
			}
		} catch (java.lang.IndexOutOfBoundsException e) {//
			type.setColumnDesc(DataSetUtils.PRIFIX_COLUMN_NAME + index);
			// titles.add(type.getColumnDesc());
		}
	}

	// ----------------文件上传 止-------------------
	// ----------------文件上传保存 起-------------------
	/**
	 * 文件上传保存
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @author 李广彬
	 * @param values
	 */
	@RequestMapping(params = "method=saveup", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> save(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		try {
			DataSetInsertThread dataSetInsertThread = new DataSetInsertThread(params, dataSetService, logService,
					defService, generateKeyService);
			Thread thread = new Thread(dataSetInsertThread);
			thread.start();
			params.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			params.put(Constants.SUCCESS, Constants.FALSE);
			params.put(Constants.MESSAGE, e.getMessage());
			logger.error(e);
		}
		return params;
	}

	// ----------------文件上传保存 止-------------------
	// ----------------文件下载 起-------------------
	/**
	 * 文件下载
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @author 李广彬
	 */
	@RequestMapping(params = "method=download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> download(@RequestParam("params") String values) {
		// dataSetId columns userId
		Map<String, Object> params = JsonUtils.toMap(values);
		List<String> colIds = (List<String>) params.get("columns");// 1
		String dataSetId = (String) params.get("dataSetId");// 2
		String userId = (String) params.get("userId");
		String isToEmpty = (String) params.get("isToEmpty");
		String basePath = buildUserDownloadPath(commons.getFileDownPath(), userId);// 服务器文件生成目录
		DataUploadDownloadLog logBO = null;
		File zipFile = null;
		String filePath = null;
		try {
			if (colIds.size() == 0) {
				throw new BusinessException("参数异常");
			}
			// 查询数据集
			Map<String, Object> dsparams = new HashMap<String, Object>();
			dsparams.put("dataSetId", dataSetId);
			params.put("dataSetId", dataSetId);
			DataSet ds = this.getBaseService().findList(dsparams).get(0);// 查询当前数据集
			String tableName = ds.getTableName();

			// 查询所有的列
			Map<String, Object> attrparams = new HashMap<String, Object>();
			attrparams.put("ids", colIds);
			attrparams.put("order", "asc");
			attrparams.put("orderby", "DISPLAY_COL_NUM");
			List<DataDefinitionAttr> cols = attrService.findList(attrparams);

			// 写日志
			logBO = new DataUploadDownloadLog();
			logBO.setDataSetId(dataSetId);
			logBO.setDataSetName(ds.getDataSetName());
			logBO.setOpType(DataSetUtils.DS_LOG_TYPE_DOWNLOAD);
			logBO.setOpUser(userId);
			logBO.setOpStartDate(new Date());

			// 文件生成
			zipFile = this.dataSetDownload(tableName, cols, basePath, ds.getDataSetName(), logBO, isToEmpty);
			String fileName = zipFile.getName();
			fileName = DataSetUtils.buildNewFileName(fileName, ds.getDataSetName());
			filePath = zipFile.getPath();

			logBO.setOpEndDate(new Date());
			// logBO.setDataCount(ds.getDataAmount());
			logBO.setOpStats(DataSetUtils.DS_LOG_OPT_STATUS_SUCCESS);
			logBO.setSourFileDir(filePath);
			logBO.setSourFileName(fileName);

			logBO = logService.save(logBO);

			// 文件下载
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String(logBO.getSourFileName().getBytes("GBK"),
					"iso8859-1"));
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zipFile), headers, HttpStatus.OK);
		} catch (Exception e) {
			params.put(Constants.SUCCESS, Constants.FALSE);
			params.put(Constants.MESSAGE, e.getMessage());
			logger.error(e);
		}

		return null;
	}

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：实验数据下载 对应存储过程生成下载文件 回写文件路径 （zip 压缩文件） 直支持txt
	 * @param tableName 表名称
	 * @param cols 字段信息
	 * @param basePath 下载的基准路径
	 * @param isToEmpty 是否去除空记录
	 * @return 下载文件地址
	 */
	@SuppressWarnings("rawtypes")
	public File dataSetDownload(String tableName, List<DataDefinitionAttr> cols, String basePath, String dataSetName,
			DataUploadDownloadLog logBO, String isToEmpty) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		params.put("columns", cols);
		params.put("isToEmpty", isToEmpty);
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		String fileName = DataSetUtils.buildNewFileName("aa.txt", dataSetName);
		String impFileName = basePath + File.separator + DataSetUtils.getUUID();// 文本文件路径
		String zipFilePath = basePath + File.separator + DataSetUtils.getUUID() + ".zip";// 压缩文件路径
		logger.error("当前JVM字符集：" + System.getProperty("file.encoding"));
		logger.error("当前JVM字符集：" + fileName + System.getProperty("file.encoding"));
		File file = new File(impFileName, fileName);
		OutputStreamWriter fw = null;
		try {
			File basePathDir = new File(impFileName);
			if (!basePathDir.exists()) {
				basePathDir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new OutputStreamWriter(new FileOutputStream(file), "GBK");
			pager.setLimit(2000);
			pager.setCurrent(0);
			pager.setReload(true);
			pager = contentBaseService.findByPager(pager, params);
			// if(pager.getTotal()==0){
			// throw new BusinessException("所选数据集下载列为空，请重新选择！");
			// }
			dataSetTitle2txt(fw, cols);
			logBO.setDataCount(new Long(pager.getTotal()));
			for (int i = 0; i < pager.getTotalPage(); i++) {
				pager.setCurrent(i);
				pager = contentBaseService.findByPager(pager, params);
				dataSet2txt(fw, pager.getData(), cols);
			}
		} finally {
			fw.close();
		}

		File zipFile = AntZipUtils.compress(file, zipFilePath);// ZipUtils
		fileName = zipFile.getName();
		// file.delete();
		// file.getParentFile().delete();
		return zipFile;
	}

	// 数据集表头转储到文本文件
	private OutputStreamWriter dataSetTitle2txt(OutputStreamWriter fw, List<DataDefinitionAttr> attrs)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		for (DataDefinitionAttr attr : attrs) {
			buffer.append(attr.getColumnDesc());
			if (attrs.size() != (attrs.indexOf(attr) + 1)) {
				buffer.append("\t");
			}
		}
		buffer.append("\r\n");
		fw.append(buffer.toString());
		return fw;
	}

	// 数据集内容转储到文本文件
	private OutputStreamWriter dataSet2txt(OutputStreamWriter fw, List<Map<String, Object>> list,
			List<DataDefinitionAttr> attrs) throws IOException {
		StringBuffer buffer = new StringBuffer();

		for (Map<String, Object> item : list) {
			if (item == null) {// 空列
				for (DataDefinitionAttr attr : attrs) {
					buffer.append("");
					if (attrs.size() != (attrs.indexOf(attr) + 1)) {
						buffer.append("\t");
					}
				}
			} else {
				for (DataDefinitionAttr attr : attrs) {
					Object obj = item.get(attr.getColumnName().toUpperCase());
					if (obj != null) {// 空对像
						if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_VARCHAR2)) {
							buffer.append(obj);
						} else if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_NUMBER)) {
							buffer.append(obj.toString());
						} else if (attr.getDataType().equals(DataSetUtils.DATA_TYPE_DATE)) {
							buffer.append(DateUtil.dateTimeToStrDefault((java.util.Date) obj));
						}
					} else {
						buffer.append("");
					}

					if (attrs.size() != (attrs.indexOf(attr) + 1)) {
						buffer.append("\t");
					}
				}
			}

			buffer.append("\r\n");
		}
		fw.write(buffer.toString());
		return fw;
	}

	/**
	 * 生成用户目录
	 * 
	 * @param basePath
	 * @param userId
	 * @return
	 */
	private String buildUserDownloadPath(String basePath, String userId) {
		Date currDate = new Date();
		StringBuffer buf = new StringBuffer(basePath + File.separator + userId);
		buf.append(currDate.getYear());
		buf.append(currDate.getMonth());
		buf.append(currDate.getDay());
		return buf.toString();

	}

	/**
	 * @功能描述：保存清理设置 并 执行
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 */
	@RequestMapping(method = RequestMethod.POST, params = "optType=count")
	@ResponseBody
	public Map<String, Object> count(@RequestParam String originalDstId, @RequestParam String targetDsType) {
		Map<String, Object> results = Constants.MAP();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("sourceDataSet", originalDstId);
			params.put("type", targetDsType);
			Integer count = dataSetService.count(params);
			results.put("count", count);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
			logger.error(e);
		}
		return results;
	}

	@RequestMapping(method = RequestMethod.GET, params = "address=true")
	@ResponseBody
	public Map<String, Object> address(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		Map<String, Object> params = JsonUtils.toMap(values);
		String resultSetId = (String) params.get("resultSetId");
		String type = (String) params.get("type");
		String ruleId = (String) params.get("ruleId");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resultSetId", resultSetId);
		param.put("type", type);
		param.put("ruleId", ruleId);
		List<Map<String, Object>> list = dataMapService.findColumns(QUERY_ADDRESS, param);
		if (list.size() > 0) {
			Map<String, Object> map = list.get(0);
			String columnName = (String) map.get("COLUMNNAME");
			results.put("data", columnName);
		}
		return results;
	}

	@RequestMapping(params = "getData=true", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String, List<List<Object>>> findData(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		String dataSetId = (String) params.get("resultSetId");
		String groupColumn = (String) params.get("groupColumn");
		DataSet dataSet = super.getBaseService().getById(dataSetId);
		String tableName = dataSet.getTableName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("groupColumn", groupColumn);
		List<Map<String, Object>> list = dataMapService.findColumns(QUERY_GROUP, param);
		// BigDecimal count = BigDecimal.valueOf(0); // 地址总数
		List<List<Object>> targetList = new ArrayList<List<Object>>();
		Map<String, List<List<Object>>> returnMap = new HashMap<String, List<List<Object>>>();

		// for(Map<String,Object> map : list){
		// count = count.add((BigDecimal)map.get("COUNT"));
		// }

		List<Object> list1 = new ArrayList<Object>();
		List<Object> list2 = new ArrayList<Object>();
		List<Object> list3 = new ArrayList<Object>();
		List<Object> list4 = new ArrayList<Object>();
		List<Object> list5 = new ArrayList<Object>();

		list1.add("无地址匹配");
		list1.add(BigDecimal.ZERO);
		list2.add("行政区划匹配");
		list2.add(BigDecimal.ZERO);
		list3.add("街道匹配");
		list3.add(BigDecimal.ZERO);
		list4.add("门牌匹配");
		list4.add(BigDecimal.ZERO);
		list5.add("单元室匹配");
		list5.add(BigDecimal.ZERO);

		targetList.add(list1);
		targetList.add(list2);
		targetList.add(list3);
		targetList.add(list4);
		targetList.add(list5);

		for (Map<String, Object> map : list) {

			// List<Object> temp = new ArrayList<Object>();
			String key = (String) map.get(groupColumn);
			BigDecimal num = (BigDecimal) map.get("COUNT");
			if (key.equals("-1")) {
				targetList.get(0).remove(1);
				targetList.get(0).add(num);
			} else if (key.equals("0")) {
				targetList.get(1).remove(1);
				targetList.get(1).add(num);
			} else if (key.equals("4")) {
				targetList.get(2).remove(1);
				targetList.get(2).add(num);
			} else if (key.equals("5")) {
				targetList.get(3).remove(1);
				targetList.get(3).add(num);
			} else if (key.equals("6")) {
				targetList.get(4).remove(1);
				targetList.get(4).add(num);
			}

			// temp.add(num.divide(count));
			// temp.add(num);
			// targetList.add(temp);
		}

		returnMap.put("data", targetList);
		return returnMap;
	}

	// ========================= get/set ===========================
	@Resource(name = "dataSetContentService")
	private DataSetContentService contentService;

	@Resource(name = "dataSetContentService")
	private IBaseService<Map<String, Object>, String> contentBaseService;

	@Resource(name = "dataSetService")
	public DataSetService dataSetService;

	@Resource(name = "dataUploadDownloadLogService")
	public IBaseService<DataUploadDownloadLog, String> logService;

	@Resource(name = "dataSetService")
	public void setBaseService(IBaseService<DataSet, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "dataDefinitionAttrService")
	private IBaseService<DataDefinitionAttr, String> attrService;

	@Resource(name = "dataDefinitionService")
	private DataDefinitionService defService;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	@Resource(name = "dataMapService")
	private IDataMapService dataMapService;

	protected String buildId() throws BusinessException {
		return generateKeyService.getNextGeneratedKey(null).getNextKey();
	}
	
	@Resource(name = "commons")
	private Commons commons;
	
	
	@Resource(name = "systemCodeService")
	IBaseService<SystemCode, String> systemCodeService;

}