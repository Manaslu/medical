package com.idap.dataprocess.rule.service.handler.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.exception.RuleException;
import com.idap.dataprocess.rule.service.handler.RuleHandler;

public abstract class DefaultRuleHanlder implements RuleHandler {

	private Log logger = LogFactory.getLog(getClass());

	protected String ruleId;

	public abstract RuleType getRuleType();

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTeamplate;

	@Override
	public void execute() throws RuleException {
		String psql = "{CALL P06_DATA_INTEGRATION(?,?,?)}";

		logger.debug("数据整合规则类型：[" + this.getRuleType().getType()
				+ "]，starting....");
		logger.debug("存储过程:" + psql + ",参数列表:{ruleId:" + ruleId + "}");
		try {
			RunMsg result = this.jdbcTeamplate.execute(psql,
					new CallableStatementCallback<RunMsg>() {
						@Override
						public RunMsg doInCallableStatement(
								CallableStatement proc) throws SQLException,
								DataAccessException {
							proc.setString(1, ruleId);
							proc.registerOutParameter(2, Types.INTEGER);
							proc.registerOutParameter(3, Types.VARCHAR);
							proc.execute();
							RunMsg result = new RunMsg(proc.getInt(2), proc
									.getString(3));
							return result;
						}
					});

			logger.info("执行结果：RunMsg=" + result);
			if (result != null && result.getSqlCode() != 0) {
				throw new RuleException(result.toString());
			}
			logger.info("数据整合规则类型：[" + this.getRuleType().getType()
					+ "]，ended....");
		} catch (Exception e) {
			logger.error("数据整合规则类型：[" + this.getRuleType().getType()
					+ "]，error...." + e.getMessage());
			throw new RuleException(e.getMessage());
		}
	}

}

class RunMsg {

	public RunMsg() {
	}

	public RunMsg(Integer sqlCode, String errMsg) {
		this.sqlCode = sqlCode;
		this.errMsg = errMsg;
	}

	public Integer getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(Integer sqlCode) {
		this.sqlCode = sqlCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	private String errMsg;

	private Integer sqlCode;

	public String toString() {
		return "SQLCODE: " + this.sqlCode + ",SQLERR:" + this.errMsg;
	}
}
