package com.idap.dataprocess.addrmatch.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.idap.dataprocess.addrmatch.MatchException;
import com.idap.dataprocess.addrmatch.entity.AddresMatchConstants;
import com.idap.dataprocess.addrmatch.entity.EMatch;
import com.idap.dataprocess.addrmatch.service.MatchFileService;
import com.idap.dataprocess.addrmatch.utils.AddresMatchUtils;
import com.idap.dataprocess.dataset.dao.DataSetContentDao;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.entity.EDataType;
import com.idap.dataprocess.dataset.service.BatchExecutor;
import com.idap.dataprocess.dataset.service.batch.ParameterSetter;
import com.idap.dataprocess.dataset.service.batch.SqlSetter;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.dataset.utils.FileReadContent;
import com.idap.dataprocess.definition.dao.DataMatchAttrDao;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.rule.dao.DataInteLogDao;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.entity.DataLogState;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.utils.StringUtils;

@Service("matchFileService")
@Transactional
public class MatchFileServiceImpl implements MatchFileService {

	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "matchConstants")
	private MatchConstants matchConstants;

	@Override
	public String createInputMatchFile() throws MatchException {
		BufferedWriter bw = null;
		String fileName = null;
		try {
			this.updateState(this.inteLog.getId(), DataLogState.LOG_RUNNING);
			// 1.生成地址匹配的输入文件
			String filePath = matchConstants.getFileMatchInPath();
			fileName = filePath + AddresMatchUtils.getInputFileName(sourceDataSet.getId() + "");
			File path = new File(filePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			File input = new File(fileName);
			if (!input.exists()) {
				input.createNewFile();
			}
			logger.info(ruleType.getRuleName() + "文件创建完成,filePath:[" + fileName + "");
			FileOutputStream outputStream = new FileOutputStream(input);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, "GBK");
			bw = new BufferedWriter(writer);
			Map<String, Object> param = Constants.MAP();

			// 2.获取源数据集的属性定义,并将属性定义设置到变量中attrs
			logger.info("以下开始为" + ruleType.getRuleName() + "文件准备数据:");
			logger.info("第一步:查询数据集的属性描述信息");
			param.put("dataDefId", this.sourceDataSet.getDataDefId());
			List<DataDefinitionAttr> attrList = dataDefinitionAttrDao.find(param);
			this.sourceAttrs = new ArrayList<DataDefinitionAttr>();
			this.matchAttrs = new ArrayList<DataDefinitionAttr>();
			// this.resultMatchedAttrs = new ArrayList<DataDefinitionAttr>();
			this.sourceAttrs.addAll(attrList);
			logger.info("第二步:过滤" + ruleType.getRuleName() + "相关15个字段");
			this.matchAttrs.addAll(this.dataMatchAttrDao.findMatchAttr());
			// resultMatchedAttrs.addAll(this.sourceAttrs);
			// resultMatchedAttrs.addAll(this.matchAttrs);
			// 3.查询该 数据集的所有数据
			param.clear();
			Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
			pager.setCurrent(0);
			pager.setLimit(2000);
			pager.setReload(true);
			pager.setTotal(1);
			param.put("tableName", this.sourceDataSet.getTableName());// 设置要查询的数据集的表名
			param.put("columns", attrList);// 设置该数据集的所有字段
			Map<String, EDataType> types = getAttrType();

			for (int current = 0; current < pager.getTotalPage(); current++) {
				pager.setCurrent(current);
				pager = this.dataSetContentDao.findByPager(pager, param);
				for (Map<String, Object> m : pager.getData()) {
					bw.write(this.getMapString(filterMap(m), types));
				}
				if (current == 0) {
					pager.setReload(false);
				}
			}
			logger.info(ruleType.getRuleName() + "文件数据生成成功,filePath:[" + fileName + "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ruleType.getRuleName() + "文件数据生成失败,filePath:[" + fileName + "");
			throw new MatchException(ruleType.getRuleName() + "文件数据生成失败,filePath:[" + fileName + "");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				throw new MatchException(e.getMessage());
			}
		}
		return fileName;
	}

	private Map<String, Object> filterMap(Map<String, Object> values) {
		Map<String, Object> results = new LinkedHashMap<String, Object>();
		for (DataDefinitionAttr ar : this.sourceAttrs) {
			results.put(ar.getColumnName(), values.get(ar.getColumnName()));
		}
		return results;
	}

	private Map<String, EDataType> getAttrType() {
		Map<String, EDataType> types = new LinkedHashMap<String, EDataType>();
		for (DataDefinitionAttr attr : sourceAttrs) {
			types.put(attr.getColumnName(), EDataType.transfer(attr.getDataType()));
		}
		return types;
	}

	@Override
	public void importMatchedFile() throws MatchException {
		// 1. 地址匹配输出文件路径
		String outputFileName = this.matchConstants.getFileMatchOutPath()
				+ AddresMatchUtils.getOutputFileName(this.sourceDataSet.getId().toString());
		File path = new File(this.matchConstants.getFileMatchOutPath());
		if (!path.exists()) {
			path.mkdirs();
		}
		final File outputFile = new File(outputFileName);
		// 2.创建结果数据集的属性及表结构定义
		this.createResultDataSet();
		TransactionTemplate transaction = new TransactionTemplate(transactionManager);
		// 3.将每次插入的数据放在同一个事务中，若有一条数据出现失败，则需要回滚所有数据,
		// 解析并插入地址匹配后的数据
		transaction.execute(new TransactionCallback<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				try {
					// 循环读取地址匹配后的文件
					do {
						// 读取文件
						List<List<String>> results = FileReadContent.readTxtFile(outputFile, "tab", "",
								AddresMatchConstants.BATCH_SIZE).getContent();
						batchInsert(trimList(results));
						if (results.size() < AddresMatchConstants.BATCH_SIZE) {// 读取到的数据量小于
																				// AddresMatchConstants.BATCH_SIZE,则说明已经读到文件尾
							break;
						}
					} while (true);
				} catch (IOException e) {
					logger.error(ruleType.getRuleName() + "批量插入数据失败,meesage:" + e.getMessage(), e);
				}
				return null;
			}
		});
	}

	// 1. 创建数据集的描述
	// 2. 创建数据集的属性定义
	// 3. 创建数据集
	// 4. 创建数据集对应的表结构
	private void createResultDataSet() throws MatchException {

		List<DataDefinitionAttr> fields = new LinkedList<DataDefinitionAttr>();
		// 查询地址匹配相关属性表
		fields.addAll(this.sourceAttrs);
		fields.addAll(this.filterMatchAttr());
		int index = 1;
		String defId = this.buildId();
		final List<Object[]> dataList = new ArrayList<Object[]>();
		for (DataDefinitionAttr entity : fields) {
			int cols = 0;
			Object[] data = new Object[13];

			data[cols++] = this.buildId();
			data[cols++] = defId;
			data[cols++] = this.buildId();
			data[cols++] = entity.getColumnName();
			data[cols++] = entity.getColumnDesc();
			data[cols++] = entity.getDataType();
			data[cols++] = entity.getLength();
			data[cols++] = entity.getIsNull();
			data[cols++] = entity.getIsPk();
			data[cols++] = entity.getConceptModelId();
			data[cols++] = index;
			data[cols++] = entity.getIsDisplay();
			data[cols++] = index++;

			// entity.setDisplayColNum(index++);
			// entity.setDataDefId(defId);
			// entity.setId(this.buildId());
			// entity.setColumnId(this.buildId());
			// entity.setIsDisplay(DataSetUtils.SWITCH_TRUE);
			// this.dataDefinitionAttrDao.save(entity);
			dataList.add(data);
		}

		this.batchExecutor.batchUpdate(dataList, new SqlSetter() {
			@Override
			public int batchSize() {
				return dataList.size();
			}

			@Override
			public String getSql() {
				return "INSERT INTO T06_DATA_DEFINITION_ATTR"
						+ "(ID,DATA_DEF_ID,COLUMN_ID,COLUMN_NAME,COLUMN_DESC,DATA_TYPE,LENGTH,IS_NULL,IS_PK,CONCEPT_MODEL_ID,COL_NUM,IS_DISPLAY,DISPLAY_COL_NUM)"
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			}
		});

		// 查询旧的数据结构定义
		DataDefinition oldef = this.dataDefinitionDao.get(sourceDataSet.getDataDefId());
		DataDefinition dataDef = new DataDefinition();
		dataDef.setDataDefId(defId);
		dataDef.setOldDataDefId(oldef.getDataDefId());
		dataDef.setCreateUser(oldef.getCreateUser());
		dataDef.setDataDefName(oldef.getDataDefName());
		dataDef.setDataDefDesc(oldef.getDataDefDesc());
		dataDef.setGroupId(oldef.getGroupId());
		dataDef.setCreateDate(new Date());
		dataDef.setBusinessType(inteRule.getRuleType());
		// 保存新的数据结构定义
		this.dataDefinitionDao.save(dataDef);
		dataDef.setTabName(DataSetUtils.DATA_DEF_PREFIX + dataDef.getId());
		this.dataDefinitionDao.update(dataDef);
		if (this.contentDao.tableExist(dataDef.getTabName())) {
			this.contentDao.dropTable(dataDef.getTabName());
		}
		this.contentDao.createTable(dataDef.getTabName(), fields);

		this.resultDataSet = new DataSet();
		resultDataSet.setDataSetId(this.inteLog.getResultSetId());
		resultDataSet.setDataDefId(defId);
		resultDataSet.setDataDesc(inteRule.getResultDataSetName());
		resultDataSet.setSourceDataSet(sourceDataSet.getDataSetId());
		resultDataSet.setCreateUser(sourceDataSet.getCreateUser());
		resultDataSet.setCreateDate(new Date());
		resultDataSet.setType(inteRule.getRuleType());
		resultDataSet.setAutoManu("HANDMADE");
		resultDataSet.setDataAmount(this.sourceDataSet.getDataAmount());
		resultDataSet.setDataSetName(inteRule.getResultDataSetName());
		resultDataSet.setEvaluateState(DataSetUtils.EXECUTE_SATUS_INIT);
		this.dataSetDao.save(resultDataSet);
		String tableName = DataSetUtils.DATA_SET_PREFIX + resultDataSet.getId();
		resultDataSet.setTableName(tableName);
		this.dataSetDao.update(resultDataSet);// 更新表名

		// this.contentDao.dropSequence(tableName);
		this.contentDao.createSequence(tableName);
		// 删除表结构
		if (this.contentDao.tableExist(tableName)) {
			this.contentDao.dropTable(tableName);
		}
		// 新建表结构
		this.contentDao.createTable(tableName, fields);

	}

	public void rollback() {
		String resultSetId = this.inteLog.getResultSetId();
		DataSet resultSet = this.dataSetDao.get(resultSetId);
		if (!StringUtils.isEmpty(resultSet)) {
			DataDefinition dataDef = this.dataDefinitionDao.get(resultSet.getDataDefId());
			Map<String, Object> params = Constants.MAP();
			params.put("dataDefId", resultSet.getDataDefId());
			this.dataDefinitionAttrDao.delete(params);
			this.dataDefinitionDao.delete(params);
			if (this.contentDao.tableExist(dataDef.getTabName())) {
				this.contentDao.dropTable(dataDef.getTabName());
			}
			params.clear();
			params.put("dataSetId", resultSetId);
			this.dataSetDao.delete(params);
			if (this.contentDao.tableExist(resultSet.getTableName())) {
				this.contentDao.dropTable(resultSet.getTableName());
			}
		}
	}

	private void batchInsert(List<Object[]> dataList) {
		batchExecutor.batchUpdate(dataList, new ParameterSetter() {
			@Override
			public int batchSize() {
				return AddresMatchConstants.BATCH_SIZE;
			}

			@Override
			public List<DataDefinitionAttr> getAttrs() {
				List<DataDefinitionAttr> attrs = new ArrayList<DataDefinitionAttr>();
				attrs.addAll(sourceAttrs);
				attrs.addAll(filterMatchAttr());
				return attrs;
			}

			@Override
			public String getTableName() {
				return resultDataSet.getTableName();
			}
		});
	}

	protected String buildId() {
		try {
			return generateKeyService.getNextGeneratedKey(null).getNextKey();
		} catch (Exception e) {
		}
		return "";
	}

	private List<Object[]> trimList(List<List<String>> dataList) {
		List<Object[]> lists = new ArrayList<Object[]>();
		int osetlen = this.sourceAttrs.size();
		int colen = osetlen + this.filterMatchAttr().size();
		for (List<String> vl : dataList) {
			Object[] column = new Object[colen];
			Object[] rst = vl.toArray();
			int index = 0;// 非地址匹配的字段总数
			System.arraycopy(rst, AddresMatchConstants.MATCH_ADDR_COLUMNS, column, index, osetlen);
			index = osetlen;
			for (int k = 0; k < AddresMatchConstants.MATCH_ADDR_COLUMNS; k++) {
				EMatch match = EMatch.getEMatch(k);
				if (match.isPersistence()) {
					column[index++] = rst[k];// 设置地址匹配的相关字段
				}
			}

			lists.add(column);
		}
		return lists;
	}

	// /**
	// * 过滤掉重复的字段
	// *
	// * @return
	// */
	// private List<DataDefinitionAttr> filterSourceAttr() {
	// List<DataDefinitionAttr> attrs = new ArrayList<DataDefinitionAttr>();
	// List<DataDefinitionAttr> mattrs = this.filterMatchAttr();
	// for (DataDefinitionAttr sattr : this.sourceAttrs) {
	// boolean exists = false;
	// for (DataDefinitionAttr mattr : mattrs) {
	// if (sattr.getColumnName().equalsIgnoreCase(
	// mattr.getColumnName())) {
	// exists = true;
	// break;
	// }
	// }
	// if (!exists) {
	// attrs.add(sattr);
	// }
	// }
	// return attrs;
	// }

	private List<DataDefinitionAttr> filterMatchAttr() {
		List<DataDefinitionAttr> attrs = new ArrayList<DataDefinitionAttr>();
		for (DataDefinitionAttr attr : this.matchAttrs) {
			EMatch match = EMatch.transfer(attr.getColumnName());
			if (match.isPersistence()) {
				attrs.add(attr);
			}
		}
		return attrs;
	}

	private String getMapString(Map<String, Object> map, Map<String, EDataType> types) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (Object value : map.values()) {
			if (index > 0) {
				sb.append("\t");
			}
			if (!StringUtils.isEmpty(value)) {
				sb.append(value.toString());
			} else {
				sb.append("");
			}
			index++;
		}
		return sb.append("\n").toString();
	}

	@Override
	public synchronized void updateState(String logId, DataLogState state) {
		this.inteLog.setOpStats(state.getState());
		this.dataInteLogDao.update(this.inteLog);
	}

	@Override
	public int columnIndex(String attrName) {
		int columnIndex = -1;
		boolean exists = false;
		for (DataDefinitionAttr attr : sourceAttrs) {
			columnIndex++;
			if (attr.getColumnName().equals(attrName)) {
				exists = true;
				break;
			}
		}

		return (exists ? columnIndex : -1);
	}

	@Override
	public void setTableInteRule(TableInteRule inteRule) {
		this.inteRule = inteRule;
		this.inteLog = inteRule.getInteLog();
		this.sourceDataSet = this.inteRule.getDataSet1();
		this.ruleType = RuleType.transfer(inteRule.getRuleType());
	}

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	@Resource(name = "dataDefinitionAttrDao")
	private DataMatchAttrDao dataMatchAttrDao;

	@Resource(name = "dataSetContentDao")
	private DataSetContentDao contentDao;

	@Resource(name = "dataInteLogDao")
	private DataInteLogDao dataInteLogDao;

	@Resource(name = "dataSetContentDao")
	private IPagerDao<Map<String, Object>> dataSetContentDao;

	private DataSet sourceDataSet;// 即将要匹配的源数据集

	private DataSet resultDataSet;// 匹配后新生成的结果数据集

	private TableInteRule inteRule;

	private RuleType ruleType;

	private DataInteLog inteLog;

	@Resource(name = "batchExecutor")
	private BatchExecutor batchExecutor;

	@Resource(name = "txManager")
	private PlatformTransactionManager transactionManager;

	private List<DataDefinitionAttr> sourceAttrs = null;// 源数据集的属性定义列表

	private List<DataDefinitionAttr> matchAttrs = null;// 地址匹配的属性定义列表

	// private List<DataDefinitionAttr> resultMatchedAttrs = null;//
	// 地址匹配后的属性定义列表

	@Resource(name = "dataDefinitionAttrDao")
	private IBaseDao<DataDefinitionAttr, String> dataDefinitionAttrDao;

	@Resource(name = "dataDefinitionDao")
	private IBaseDao<DataDefinition, String> dataDefinitionDao;

	@Resource(name = "dataSetDao")
	private IBaseDao<DataSet, String> dataSetDao;

}
