package com.idap.dataprocess.dataset.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.utils.DateUtil;

public class DataSetUtils {
	private static final Log logger = LogFactory.getLog(DataSetUtils.class);

	// 主键信息
	public static final String ID_COL_NAME = "ID";
	public static final String ID_COL_DESC = "主键";
	public final static String ID_COL_TYPE = "NUMBER";
	public final static int DATA_TYPE_VARCHAR2_LENGTH = 200;

	// 开关状态
	public final static String SWITCH_TRUE = "Y";// 是
	public final static String SWITCH_FALSE = "N";// 否

	// 上传文件后缀
	public static final String SUFFIX_TXT = ".txt";
	public static final String SUFFIX_XLS = ".xls";
	public static final String SUFFIX_XLSX = ".xlsx";
	public static final String SUFFIX_ET = ".et";
	public static final String SUFFIX_ZIP = ".zip";
	// 默认字段名前缀
	public static final String PRIFIX_COLUMN_NAME = "COLUMN_";

	// 数据定义 数据类型 常量
	public static final String DATA_TYPE_VARCHAR2 = "VARCHAR2";
	public static final String DATA_TYPE_NUMBER = "NUMBER";
	public static final String DATA_TYPE_DATE = "DATE";

	// 数据集上传类型标志位
	public static final String DS_LOG_TYPE_DOWNLOAD = "1";
	public static final String DS_LOG_TYPE_UPLOAD = "2";

	// 记录文件上传日志状态
	public static final String DS_LOG_OPT_STATUS_SUCCESS = "1";
	public static final String DS_LOG_OPT_STATUS_ERROR = "2";

	// 数据集内容 记录修改日志状态
	public static final String DS_MAINTAIN_LOG_SUCCESS = "1";
	public static final String DS_MAINTAIN_LOG_ERROR = "2";

	// 评估状态
	// public static final String DS_ASSESS_SUCCESS = "1";

	// 执行状态[数据集评估状态]
	public static final String EXECUTE_SATUS_INIT = "init";
	public static final String EXECUTE_SATUS_RUNNING = "running";
	public static final String EXECUTE_SATUS_FINISHED = "finished";
	public static final String EXECUTE_SATUS_FAILURE = "failure";

	//
	public static final String DATA_SET_PREFIX = "DATA_SET_";// 数据集前缀
	public static final String DATA_DEF_PREFIX = "DATA_DEF_";// 数据定义前缀
	// public static final String DATA_SET_FILE_UPLOAD_PATH = "upload/data_set";
	// public static final String DATA_SET_FILE_DOWNLOAD_PATH =
	// "download/data_set";
	// public static final String DATA_SET_FILE_UPLOAD_ERROR_PATH =
	// "download/data_set/error";

	// 地址匹配 脚本库 脚本代码
	public static final String ADDRESS_CLEAN_TYPE_ADDRESS = "address";// 地址列
	public static final String ADDRESS_CLEAN_TYPE_POST = "post";// 邮编列

	// 数据定义businessType 定义类型 1、手工创建 2.自动创建
	public static final String DEF_BIZ_TYPE_MANUAL = "1";// 手工创建
	public static final String DEF_BIZ_TYPE_AUTO = "2";// 自动创建

	// 数据集创建类型 HANDMADE：手工 SYS:系统（其它业务系统抽取）
	public static final String DATA_SET_CREATE_TYPE_HANDMADE = "HANDMADE";// 手工创建

	//
	public static final String DS_UPLOAD_ERROR_FILE_SUFFIX = "_ERR";// 数据上传时错误文件后缀
	public static final String DS_UPLOAD_ERROR_FILE_SUFFIX_CN = "_错误";// 数据上传时错误文件后缀
	
	//
	public static final String DS_SOURCE_TYPE_INTERFACE = "jksj";//接口数据
	public static final String DS_SOURCE_TYPE_TEST= "sysj";// 实验数据
	
	//
	public static final Long RULE_SCRIPT_ID_KEY = 12l;//剔除关键字及关键字以后字符
	public static final Long RULE_SCRIPT_ID_SUBSTR= 13l;// 按照起始位置和长度进行字符截取

	// 1.数据命名规则
	public static String buildDsTableName(final String tabName) {
		return DataSetUtils.DATA_SET_PREFIX + tabName;
	}

	// 生成新的文件名
	public static String buildNewFileName(final String orgFileName) {
		String extName = orgFileName.substring(orgFileName.lastIndexOf('.'));
		return UUID.randomUUID().toString().replace("-", "") + extName;
	}

	// 生成新的文件名
	public static String buildNewFileName(final String orgFileName, final String newFileName) {
		String extName = orgFileName.substring(orgFileName.lastIndexOf('.'));
		String fileName = fileDirClean(newFileName);
		return fileName + extName;
	}
	
	// 生成新的文件名
	public static String buildErrorFileName(final String orgFileName, final String newFileName) {
		String dateStr = DateUtil.format(new Date(), DateUtil.MMDDHHMMSS);
		return DataSetUtils.buildNewFileName(orgFileName, newFileName + "_" + dateStr
				+ DataSetUtils.DS_UPLOAD_ERROR_FILE_SUFFIX_CN);
	}


	public static String fileDirClean(final String orgFileName) {
		String fileName = orgFileName;
		// 文件名不允许出现的字符
		fileName = fileName.replace(" ", "");
		fileName = fileName.replace("\\", "");
		fileName = fileName.replace("/", "");
		fileName = fileName.replace("*", "");
		fileName = fileName.replace("?", "");
		fileName = fileName.replace("<", "");
		fileName = fileName.replace(">", "");
		fileName = fileName.replace("|", "");
		
		return fileName;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	// 将字符串转为相应的java对像
	public static Object buildColumn2JavaObject(String colStr, String type) throws ParseException {
		Object colObj = null;
		if (StringUtils.isNotBlank(colStr)) {
			if (type.equals(DataSetUtils.DATA_TYPE_VARCHAR2)) {
				colObj = colStr;
			} else if (type.equals(DataSetUtils.DATA_TYPE_NUMBER)) {
				if (StringUtils.isNotBlank(colStr)) {
					colObj = new BigDecimal(colStr.trim());
				}
			} else if (type.equals(DataSetUtils.DATA_TYPE_DATE)) {
				if (RegexUtils.testDate(colStr)) {
					colObj = DateUtil.strToDate(colStr, null);
				} else if (RegexUtils.testDateTime(colStr)) {
					colObj = DateUtil.strToDate(colStr, DateUtil.SDF_YYYY_MM_DD_2HH_MM);
				} else if (RegexUtils.testDate(colStr, "/")) {
					colObj = DateUtil.strToDate(colStr, DateUtil.SDF_YYYY_MM_DD1);
				} else if (RegexUtils.testDateTime(colStr, "/")) {
					colObj = DateUtil.strToDate(colStr, DateUtil.SDF_YYYY_MM_DD_2HH_MM1);
				} else {
					throw new BusinessException("日期格式转换错误[content:" + colStr + "]");
				}
			}
		}
		return colObj;

	}

	public static Date str2date(String dateStr) throws ParseException {
		Date date = null;
		if (RegexUtils.testDate(dateStr)) {
			date = DateUtil.strToDate(dateStr, null);
		} else if (RegexUtils.testDateTime(dateStr)) {
			date = DateUtil.strToDate(dateStr, DateUtil.SDF_YYYY_MM_DD_2HH_MM);
		} else if (RegexUtils.testDate(dateStr, "/")) {
			date = DateUtil.strToDate(dateStr, DateUtil.SDF_YYYY_MM_DD1);
		} else if (RegexUtils.testDateTime(dateStr, "/")) {
			date = DateUtil.strToDate(dateStr, DateUtil.SDF_YYYY_MM_DD_2HH_MM1);
		} else {
			throw new BusinessException("日期格式转换错误[content:" + dateStr + "]");
		}
		return date;
	}

	// 生成数据集主键信息的公共方法
	public static DataDefinitionAttr buildIdColumn(IGenerateKeyMangerService generateKeyService) {
		DataDefinitionAttr idBO = new DataDefinitionAttr();
		idBO.setId(generateKeyService.getNextGeneratedKey(null).getNextKey());
		idBO.setColumnId(generateKeyService.getNextGeneratedKey(null).getNextKey());
		idBO.setColumnName(DataSetUtils.ID_COL_NAME);
		idBO.setColumnDesc(DataSetUtils.ID_COL_DESC);
		idBO.setDataType(DataSetUtils.ID_COL_TYPE);
		idBO.setIsDisplay(DataSetUtils.SWITCH_FALSE);
		idBO.setColNum(0);
		idBO.setIsNull(DataSetUtils.SWITCH_FALSE);
		idBO.setIsPk(DataSetUtils.SWITCH_TRUE);
		idBO.setDisplayColNum(0);
		return idBO;
	}

	// 生成数据集主键信息的公共方法
	public static DataDefinitionAttr buildIdColumn(IGenerateKeyMangerService generateKeyService, String dataDefId) {
		DataDefinitionAttr idBO = buildIdColumn(generateKeyService);
		idBO.setDataDefId(dataDefId);
		return idBO;
	}

	// get方法
	public static String varName2getterName(final String varName) {
		return "get" + varName.substring(0, 1).toUpperCase() + varName.substring(1);
	}

	// 将List转储为Map
	public static Map<Object, Object> list2map(List<?> objs, String propertyName) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		Object key = null;
		for (Object obj : objs) {
			Method method = obj.getClass().getMethod(varName2getterName(propertyName), null);
			key = method.invoke(obj);
			resultMap.put(key, obj);
		}
		return resultMap;
	}

	/**
	 * 格式化输出 浮点数[存在四舍五入]
	 * 
	 * @param num 双精度浮点数
	 * @param max 小数点后-最大保留位数(默认为 2 多的去除 )
	 * @param min 小数点后-最小保留位数(默认为 2 ,不足补0)
	 * @return
	 */
	public static String decimalFormat(BigDecimal num, Integer max, Integer min) {
		BigDecimal numLong=new BigDecimal(num.toBigInteger());
		if (null == num) {
			return "";
		}
		Integer _min = (null == min || min < 0) ? 2 : min;
		String pattern = "0";
		DecimalFormat formatter = new DecimalFormat(pattern);
		if (null != _min) {
			formatter.setMinimumFractionDigits(_min);
		}
		if (null != max) {
			num=num.stripTrailingZeros();
			if(num.compareTo(numLong)==0){
				formatter.setMaximumFractionDigits(0);
			}else{
				formatter.setMaximumFractionDigits(max);
			}
		}
		return formatter.format(num);
	}
	/**
	 * 将字符型数据字转为整形
	 * @param obj
	 * @return
	 */
	public static Integer obj2integer(Object obj) {
		Integer typeLength=null;
		if(obj!=null){
			if(obj instanceof String){
				typeLength=Integer.valueOf((String)obj);
			}
			if(obj instanceof Integer){
				typeLength=(Integer) obj;
			}
		}
		return typeLength;
	}

	public static void main(String[] args) {
//		BigDecimal num=num.stripTrailingZeros();
		System.out.println(decimalFormat(new BigDecimal("11111111111111111.3393").divideToIntegralValue(new BigDecimal(1)),null,null));
		Object val = new String("q");
		System.out.println(val instanceof String);
		Double.valueOf((String) val);
	}

}
