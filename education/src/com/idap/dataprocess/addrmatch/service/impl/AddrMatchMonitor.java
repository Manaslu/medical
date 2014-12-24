package com.idap.dataprocess.addrmatch.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.idap.dataprocess.addrmatch.MatchException;
import com.idap.dataprocess.addrmatch.entity.AddresMatchConstants;
import com.idap.dataprocess.addrmatch.entity.MatchParameters;
import com.idap.dataprocess.addrmatch.service.MatchFileService;
import com.idap.dataprocess.addrmatch.service.MatchHandler;
import com.idap.dataprocess.addrmatch.utils.AddresMatchUtils;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.entity.DataLogState;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.constants.Constants;

/**
 * 
 * @author Raoxy
 * 
 */
public class AddrMatchMonitor {
	private Log logger = LogFactory.getLog(AddrMatchMonitor.class);

	private MatchFileService matchFileService;

	private AddresMatchServer addresMatchServer;

	private MatchConstants matchConstants;

	private String taskID;

	private TableInteRule inteRule;

	private DataInteLog inteLog;

	private RuleType ruleType;

	public void execute() {
		Map<String, String> map = null;//
		// 获取地址匹配的源数据集
		matchFileService.setTableInteRule(inteRule);
		this.matchConstants = this.addresMatchServer.getMatchConstants();
		this.inteLog = this.inteRule.getInteLog();// 设置数据整合日志
		this.ruleType = RuleType.transfer(inteRule.getRuleType());
		try {
			// 开始匹配
			startMatch();
			Boolean flag = true;
			int times = 1;
			while (flag) {
				logger.info("第 " + (times) + "次，" + ruleType.getRuleName()
						+ "开始监控匹配状态");
				map = matchedState();// 匹配状态
				if (map.containsKey("type") && map.containsKey("scrp")) {
					logger.error("监控" + ruleType.getRuleName() + "进度异常..."
							+ map.containsKey("scrp"));
					this.matchFileService.updateState(inteLog.getId(),
							DataLogState.LOG_FAILURE);
					flag = false;
				} else {
					if (AddresMatchConstants.MATCH_END.equals(map.get("isend"))) {// 匹配文件是否已经完成
						// 4.地址匹配后的文件插入到数据库中
						this.matchFileService.importMatchedFile();
						// 插入数据实例关系表
						this.matchFileService.updateState(inteLog.getId(),
								DataLogState.LOG_FINISHED);
						flag = false;
						logger.debug(ruleType.getRuleName() + "执行成功,(" + times
								+ ")");
					}
				}

				logger.info("第" + times + "次" + ruleType.getRuleName() + "监控结束");
				if (flag) {
					// this.matchFileService.updateState(inteLog.getId(),
					// DataLogState.LOG_RUNNING);
					MESSAGES.put("message",
							"第 " + (times) + " 次," + ruleType.getRuleName()
									+ "进行匹配");
					logger.info("第 " + (times++) + " 次,"
							+ ruleType.getRuleName() + "休眠"
							+ AddresMatchConstants.MATCH_SLEEP_TIMES
							+ "秒后，再次监控匹配状态");
					for (int i = AddresMatchConstants.MATCH_SLEEP_TIMES; i > 0; i--) {
						this.sleep(i);
					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 修改数据整合日志的状态为failure
			this.matchFileService.updateState(inteLog.getId(),
					DataLogState.LOG_FAILURE);
			// this.matchFileService.rollback();
			logger.debug(ruleType.getRuleName() + "执行失败!");
		}
	}

	private void sleep(int times) throws InterruptedException {
		logger.info(times + " 秒");
		Thread.sleep(1000l);
	}

	/**
	 * 拼接协议并生成输入输出文件
	 * 
	 * @param matchType
	 * @return
	 * @throws MatchException
	 */
	private boolean startMatch() throws MatchException {
		try {
			// 1.创建地址匹配的输入 文件
			matchFileService.createInputMatchFile();
			MatchHandler matchHandler = new FileMatchHandler(this.getParams());
			matchHandler.setServer(addresMatchServer);
			// 匹配并返回taskId
			this.taskID = (String) matchHandler.match();
			logger.info(ruleType.getRuleName() + "任务号,TASKID=" + taskID);
			// 更新整合日志的状态
			this.matchFileService.updateState(inteLog.getId(),
					DataLogState.LOG_RUNNING);
			MESSAGES.put("message", ruleType.getRuleName() + "进行匹配");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MatchException(e.getMessage());
		}
		return true;
	}

	private MatchParameters getParams() {
		String dataSetId = this.inteRule.getDataSet1().getId().toString();

		MatchParameters params = new MatchParameters();
		// 1.获取地址匹配相关属性信息(地址列，邮编列，行政区划列)
		params.setInputFile(this.matchConstants.getFileMatchInPath() + ""
				+ AddresMatchUtils.getInputFileName(dataSetId));
		params.setOutputFile(matchConstants.getFileMatchOutPath() + ""
				+ AddresMatchUtils.getOutputFileName(dataSetId));
		String addColumnName = "";
		String postColumnName = "";
		for (ColumnInteRule crule : this.inteRule.getColumnRules()) {
			if (DataSetUtils.ADDRESS_CLEAN_TYPE_ADDRESS.equalsIgnoreCase(crule
					.getRuleScript().getScriptCode())) {//
				addColumnName = crule.getDataSet1Col();
				continue;
			}
			if (DataSetUtils.ADDRESS_CLEAN_TYPE_POST.equalsIgnoreCase(crule
					.getRuleScript().getScriptCode())) {//
				postColumnName = crule.getDataSet1Col();
				continue;
			}

		}
		params.setAddrColumn(matchFileService.columnIndex(addColumnName) + "");// 地址列从0行开始
		params.setPostCodeColumn(matchFileService.columnIndex(postColumnName)
				+ "");
		params.setDivisionColumn("-1");
		params.setMatchType(RuleType.transfer(inteRule.getRuleType())
				.getValue());
		params.setOutputLevel("-1");
		params.setPriority("-1");
		return params;
	}

	/**
	 * 获取地址匹配监控状态
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> matchedState() throws MatchException {
		try {
			MatchHandler matchState = new MatchStateHandler(this.taskID);
			matchState.setServer(addresMatchServer);
			return (Map<String, String>) matchState.match();
		} catch (Exception e) {
			logger.error("监控" + ruleType.getRuleName() + "进度错误", e);
			throw new MatchException(e.getMessage());
		}
	}

	public TableInteRule getInteRule() {
		return inteRule;
	}

	public void setInteRule(TableInteRule inteRule) {
		this.inteRule = inteRule;
	}

	public void setMatchFileService(MatchFileService matchFileService) {
		this.matchFileService = matchFileService;
	}

	public void setAddresMatchServer(AddresMatchServer addresMatchServer) {
		this.addresMatchServer = addresMatchServer;
	}

	static Map<String, Object> MESSAGES = Constants.MAP();

	static class MatchPushlet extends EventPullSource {
		private Log logger = LogFactory.getLog(getClass());

		@Override
		protected long getSleepTime() {
			return 1000;
		}

		@Override
		protected Event pullEvent() {
			Event event = Event.createDataEvent("/event/match");
			for (Entry<String, Object> entry : MESSAGES.entrySet()) {
				logger.info("MESSAGE: " + entry.getValue());
				event.setField(entry.getKey(), entry.getValue().toString());
			}
			return event;
		}
	}
}
