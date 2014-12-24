package com.idap.intextservice.dataServiceProcess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.service.BatchExecutor;
import com.idap.dataprocess.dataset.service.batch.SqlSetter;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.entity.DataLogState;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.SubRuleScript;
import com.idap.dataprocess.rule.entity.SubScriptPara;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idap.dataprocess.rule.service.TableInteRuleService;
import com.idap.intextservice.dataServiceProcess.entity.DataServiceProcess;
import com.idap.intextservice.dataServiceProcess.entity.FlowRuleCust;
import com.idap.intextservice.dataServiceProcess.entity.RunProcess;
import com.idap.intextservice.dataServiceProcess.service.DataFlowExecutor;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;

@Service("dataFlowExecutor")
@Transactional
public class DataFlowExecutorService implements DataFlowExecutor {

	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "flowRuleCustDao")
	private IBaseDao<FlowRuleCust, String> flowRuleCustDao;

	@Resource(name = "tableInteRuleDao")
	private IBaseDao<TableInteRule, String> tableInteRuleDao;

	@Resource(name = "columnInteRuleDao")
	private IBaseDao<ColumnInteRule, String> columnInteRuleDao;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;


	@Resource(name = "dataInteLogService")
	private IBaseService<DataInteLog, String> dataInteLogService;

	@Resource(name = "tableInteRuleService")
	private TableInteRuleService tableInteRuleService;

	@Resource(name = "dataInteLogService")
	private IBaseService<DataInteLog, String> dataInteLogBaseService;

	@Resource(name = "runProcessService")
	private IBaseService<RunProcess, String> runProcessService;

	@Resource(name = "dataServiceProcessService")
	private IBaseService<DataServiceProcess, String> dataServiceProcessService;

	@Resource(name = "batchExecutor")
	private BatchExecutor batchExecutor;

	@Resource(name = "subRuleScriptDao")
	private IBaseDao<SubRuleScript, String> subRuleScriptDao;

	@Resource(name = "subScriptParaDao")
	private IBaseDao<SubScriptPara, String> subScriptParaDao;

	private void clearTableRule(String processId) {
		Map<String, Object> params = Constants.MAP();
		params.put("processType", DataInteLog.PROCESS_TYPE_COPY);
		params.put("processId", processId);
		params.put("orderBy", "INDEXNO");
		params.put("order", "ASC");
		List<DataInteLog> logs = this.dataInteLogBaseService.findList(params);
		for (DataInteLog log : logs) {
			this.tableInteRuleService.deleteTableRule(log.getRuleId());
		}
	}

	@Override
	public void execute(String processId) throws BusinessException {
		logger.info("数据服务流程开始执行...{processId:" + processId + "}");
		RunProcess process = runProcessService.getById(processId);
		FlowRuleCust flowRule = this.flowRuleCustDao.get(process
				.getProcRuleCustId());
		this.clearTableRule(processId);

		// 0代表数据抽取，1代表手工定义
		this.copyDataInteLog(flowRule, processId,
				DataInteLog.PROCESS_TYPE_AUTO, DataInteLog.PROCESS_TYPE_COPY);

		// 1.从数据集抽取数据清洗规则
		// 根据目标数据集的ID,回溯源数据集列表
		Map<String, Object> params = Constants.MAP();
		params.put("processType", DataInteLog.PROCESS_TYPE_COPY);
		params.put("processId", processId);
		params.put("orderBy", "INDEXNO");
		params.put("order", "ASC");
		List<DataInteLog> logs = this.dataInteLogBaseService.findList(params);
		// 2.执行该流程所有整合规则
		try {
			// 3.设置真实的数据集 ID，更新至第一个整合日志的源数据集ID
			DataInteLog fLog = logs.get(0);
			DataSet sourceSet = new DataSet();
			sourceSet.setDataSetId(process.getSourceDataSet());
			fLog.setSourceDataSet1(sourceSet);
			fLog.setOpStats(DataLogState.LOG_INIT.getState());
			this.dataInteLogBaseService.update(fLog);
			// 4. 修改表整合源数据的ID
			TableInteRule inteRule = this.tableInteRuleDao
					.get(fLog.getRuleId());
			inteRule.setDataSet1(sourceSet);
			this.tableInteRuleDao.update(inteRule);
			final List<Object[]> dataList = new ArrayList<Object[]>();
			DataInteLog _log = null;
			for (int i = 0; i < logs.size(); i++) {
				_log = logs.get(i);
				Object[] values = new Object[2];
				int index = 0;
				if (i == logs.size() - 1) {
					values[index++] = process.getResultDataSetName();
				} else {
					values[index++] = process.getDataSetName()
							+ "_"
							+ RuleType.transfer(_log.getRuleType())
									.getRuleName() + (i + 1);
				}

				values[index++] = _log.getRuleId();
				dataList.add(values);
			}

			batchExecutor.batchUpdate(dataList, new SqlSetter() {
				@Override
				public int batchSize() {
					return dataList.size();
				}

				@Override
				public String getSql() {
					return "UPDATE T06_TABLE_INTE_RULE SET RESULT_DATA_SET_NAME=? WHERE RULE_ID=?";
				}
			});

			DataInteLog endLog = logs.get(logs.size() - 1);
			logger.info("数据服务流程开始执行清洗规则...{processId:" + processId + "}");

			for (DataInteLog log : logs) {
				logger.info("开始清洗规则=["
						+ RuleType.transfer(log.getRuleType()).getRuleName()
						+ ",{整合规则ID:" + log.getRuleId() + ",规则类型:"
						+ log.getRuleType() + "}");
				this.tableInteRuleService.executeRule(log.getRuleId());
			}

			// 3. 修改执行流程相关信息，待添加相应代码
			process.setResultDataSet(endLog.getResultSetId());
			process.setRunState("2");// 执行成功,成功后就不能再次执行该模板
			process.setOverTime(new Date());// 执行时间
			logger.info("更新数据服务流程的结果数据集的 ID");
			this.runProcessService.update(process);
			logger.info("数据服务流程执行成功...{processId:" + processId + "}");

			// process.setResultDataSet(resultDataSet);
			// 4. 修改执行流程相关信息

			DataServiceProcess dataServicePro = new DataServiceProcess();
			// 显示源数据集名称的
			dataServicePro.setSourceDataSetId(process.getSourceDataSet());
			// 显示目标数据集名称的
			dataServicePro.setResultDataSetId(process.getResultDataSet());
			// 显示流程名称的
			dataServicePro.setRunProcId(process.getRunProcId());
			// 显示规则名称的
			dataServicePro.setFlowRuleCustId(process.getProcRuleCustId());
			// 执行完把数据服务状态改为已执行，EXEC-已执行；UNEXEC-未执行
			dataServicePro.setServiceState("EXEC");
			// 数据服务ID
			dataServicePro.setDataServiceId(process.getDataServiceId());

			dataServiceProcessService.update(dataServicePro);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			RunProcess runProcess = new RunProcess();
			runProcess.setRunState("3");// 执行失败
			runProcess.setRunProcId(process.getRunProcId());
			this.runProcessService.update(runProcess);
			throw new BusinessException("数据服务流程执行失败!流程ID：{processId:"
					+ processId);
		}
	}

	/**
	 * 新增匹配规则，自定义规则
	 */
	@Override
	public TableInteRule insertTableRule(TableInteRule tableRule,
			String flowId, String resultId, Integer indexNo)
			throws BusinessException {
		return this.insertTableRule(tableRule, flowId, resultId, indexNo,
				DataLogState.LOG_FINISHED, DataInteLog.PROCESS_TYPE_AUTO);
	}

	private TableInteRule insertTableRule(TableInteRule tableRule,
			String flowId, String resultId, Integer indexNo,
			DataLogState state, String processType) throws BusinessException {
		Assert.notNull(tableRule, "流程规则定制的ID不允许为空,flowId=" + flowId);
		// 1. 生成整合规则的主键Id
		String ruleId = this.generateKeyService.getNextGeneratedKey("")
				.getNextKey();
		// 2.新插入整合规则对应的字段
		for (ColumnInteRule rule : tableRule.getColumnRules()) {
			rule.setRuleId(ruleId);
			rule.setId(generateKeyService.getNextGeneratedKey("").getNextKey());
			this.columnInteRuleDao.save(rule);
			insertAllChildRules(rule);
		}
		// 3. 如果需要增加整合日志
		tableRule.setRuleId(ruleId);
		DataInteLog log = this.insertDataInteLog(tableRule, flowId, resultId,
				indexNo, state, processType);
		tableRule.setInteLog(log);
		tableRule.setResultDataSetName(generateKeyService.getNextGeneratedKey(
				"").getNextKey());
		// 4.新插入表整合规则功能
		tableInteRuleDao.save(tableRule);

		return tableRule;
	}

	/**
	 * 插入所有的子规则以及对应的参数数据
	 * 
	 * @param rule
	 * @throws BusinessException
	 */
	private void insertAllChildRules(ColumnInteRule rule)
			throws BusinessException {
		// 一级规则级参数
		for (SubScriptPara param : rule.getScriptParams()) {
			param.setId(buildId(""));
			param.setColumnInteId(rule.getId());
			subScriptParaDao.save(param);
		}
		// 二级规则级参数
		Boolean flag = true;
		String id = buildId("");
		for (SubRuleScript chdRule : rule.getSubRuleScript()) {
			if (flag) {
				chdRule.setId(id);
				chdRule.setRuleId(rule.getRuleId());
				chdRule.setColumnRuleId(rule.getId());
				subRuleScriptDao.save(chdRule);
				flag = false;
			}
			for (SubScriptPara ruleParm : chdRule.getSubScriptParams()) {
				ruleParm.setId(buildId(""));
				ruleParm.setSubRuleScriptId(id);
				subScriptParaDao.save(ruleParm);
			}
		}
	}

	/**
	 * 主键生成
	 * 
	 * @param params
	 * @return
	 */
	private String buildId(String params) {
		return generateKeyService.getNextGeneratedKey(params).getNextKey();
	}

	/**
	 * 抽取规则
	 */
	@Override
	public String insertTableRule(String flowId) throws BusinessException {
		FlowRuleCust flowRule = this.flowRuleCustDao.get(flowId);
		this.copyDataInteLog(flowRule, flowRule.getFlowRuleCustId(),
				DataInteLog.PROCESS_TYPE_MANUAL, DataInteLog.PROCESS_TYPE_AUTO);
		return flowId;
	}

	/**
	 * 
	 * @param flowRule
	 * @param processId
	 * @param state
	 * @param sourceProcessType
	 *            源整合日志的处理类型
	 * @param orgProcessType
	 *            目标整合日志的处理类型
	 */
	private void copyDataInteLog(FlowRuleCust flowRule, String processId,
			String sourceProcessType, String orgProcessType) {
		Map<String, Object> params = Constants.MAP();
		params.put("processId", flowRule.getFlowRuleCustId());
		params.put("orderBy", "INDEXNO");
		params.put("order", "ASC");
		List<DataInteLog> inteLogs = this.dataInteLogService.findList(params);
		// .findByResult(
		// flowRule.getResultDataSet(), sourceProcessType);
		// Collections.reverse(inteLogs);
		// 1.创建源数据集对象
		String osetId = generateKeyService.getNextGeneratedKey("").getNextKey();
		DataSet dataSet1 = new DataSet();
		dataSet1.setDataSetId(osetId);
		String resultId = generateKeyService.getNextGeneratedKey("")
				.getNextKey();
		Integer indexNo = 0;
		for (int i = 0; i < inteLogs.size(); i++) {

			// 2.获取数据表整合规则对象
			TableInteRule tRule = this.tableInteRuleDao.get(inteLogs.get(i)
					.getRuleId());
			TableInteRule tableRule = clone(tRule);
			tableRule.setDataSet1(dataSet1);

			resultId = generateKeyService.getNextGeneratedKey("").getNextKey();
			TableInteRule resultRule = this.insertTableRule(tableRule,
					processId, resultId, ++indexNo, DataLogState.LOG_INIT,
					orgProcessType);
			// 3.获取该次整合规则产生的结果数据集，作为下一个数据整合的源数据集
			dataSet1 = resultRule.getInteLog().getResultDataSet();
		}
	}

	private TableInteRule clone(TableInteRule tRule) {
		TableInteRule result = new TableInteRule();

		result.getColumnRules().addAll(tRule.getColumnRules());
		result.setCreateUser(tRule.getCreateUser());
		result.setLinkRule(tRule.getLinkRule());
		result.setRuleType(tRule.getRuleType());
		result.setResultDataSetName(tRule.getResultDataSetName());
		result.setDataset1GroupColumn(tRule.getDataset1GroupColumn());
		result.setDataset2GroupColumn(tRule.getDataset2GroupColumn());

		return result;
	}

	private DataInteLog insertDataInteLog(TableInteRule tableRule,
			String flowId, String resultId, Integer indexNo,
			DataLogState state, String processType) throws BusinessException {
		DataInteLog log = new DataInteLog();

		log.setId(this.generateKeyService.getNextGeneratedKey("").getNextKey());
		log.setRuleId(tableRule.getRuleId());
		log.setProcessId(flowId);
		log.setSourceDataSet1(tableRule.getDataSet1());
		log.setSourceDataSet2(tableRule.getDataSet2());
		// 2.1 创建一个虚拟结果数据集
		DataSet resultSet = new DataSet();

		resultSet.setDataSetId(resultId);

		log.setOpUser(tableRule.getCreateUser());
		log.setRuleType(tableRule.getRuleType());
		log.setResultDataSet(resultSet);
		log.setOpStats(state.getState());
		log.setProcessType(processType);
		log.setIndexNo(indexNo);

		dataInteLogBaseService.save(log);

		return log;// 返回结果整合日志
	}

}
