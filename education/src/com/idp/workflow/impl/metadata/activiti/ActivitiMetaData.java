package com.idp.workflow.impl.metadata.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.idp.workflow.event.action.ActionEvent;
import com.idp.workflow.event.action.ActionEventDispatcher;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.builder.activiti.DentityManageServiceBuilder;
import com.idp.workflow.impl.builder.activiti.DentityQueryServiceBuilder;
import com.idp.workflow.impl.builder.activiti.EngineServiceBuilder;
import com.idp.workflow.impl.builder.activiti.HistoricTaskManageServiceBuilder;
import com.idp.workflow.impl.builder.activiti.HistoricTaskQueryServiceBuilder;
import com.idp.workflow.impl.builder.activiti.ProDefineMangeServiceBuilder;
import com.idp.workflow.impl.builder.activiti.ProDefineQueryServiceBuilder;
import com.idp.workflow.impl.builder.activiti.ProInstanceManageServiceBuilder;
import com.idp.workflow.impl.builder.activiti.ProInstanceQueryServiceBuilder;
import com.idp.workflow.impl.builder.activiti.TaskManageServiceBuilder;
import com.idp.workflow.impl.builder.activiti.TaskQueryServiceBuilder;
import com.idp.workflow.impl.listener.activiti.TaskActionListener;
import com.idp.workflow.itf.metadata.AbstractWFMetaData;
import com.idp.workflow.util.common.ReflectUtil;
import com.idp.workflow.util.common.SimpleCache;
import com.idp.workflow.util.service.Context;

/**
 * Activiti工作流元数据实现
 * 
 * @author panfei
 * 
 */
public class ActivitiMetaData extends AbstractWFMetaData<ProcessEngine> {

	public ActivitiMetaData(String jdbcUrl) throws WfBusinessException {
		this(jdbcUrl, false, ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
	}

	public ActivitiMetaData(String jdbcUrl, boolean jobExecutorActivate,
			String databaseSchemaUpdate) throws WfBusinessException {
		super();
		if (jdbcUrl != null) {
			this.processEngConfig = ProcessEngineConfiguration
					.createStandaloneInMemProcessEngineConfiguration()
					.setDatabaseSchemaUpdate(databaseSchemaUpdate)
					.setJdbcUrl(jdbcUrl)
					.setJobExecutorActivate(jobExecutorActivate);
			this.dataSource = getProcessEngConfig().getDataSource();
			this.setWorkFlowSource(this.processEngConfig.buildProcessEngine());
			this.InItSerivceBuilder();
			this.InItEventListener();
			this.setLocater(SimpleCache.GetInstance());
			this.setServiceRegAuto(true);
			this.initSqlSessionFactory();
		}
	}

	/**
	 * 工作流activiti配置信息
	 */
	protected ProcessEngineConfiguration processEngConfig;

	/**
	 * sql数据源
	 */
	protected DataSource dataSource;

	public ProcessEngineConfiguration getProcessEngConfig() {
		return processEngConfig;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 数据库会话
	 */
	protected SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 数据库事务
	 */
	protected TransactionFactory transactionFactory;

	public TransactionFactory getTransactionFactory() {
		return transactionFactory;
	}

	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	protected void InItSerivceBuilder() {
		// 添加 流程定义管理服务
		this.addAdapterBuilder(new ProDefineMangeServiceBuilder());
		// 添加 流程定义查询服务
		this.addAdapterBuilder(new ProDefineQueryServiceBuilder());
		// 添加 流程实例管理服务
		this.addAdapterBuilder(new ProInstanceManageServiceBuilder());
		// 添加 流程实例查询服务
		this.addAdapterBuilder(new ProInstanceQueryServiceBuilder());
		// 添加 工作流引擎
		this.addAdapterBuilder(new EngineServiceBuilder());
		// 添加 任务管理服务
		this.addAdapterBuilder(new TaskManageServiceBuilder());
		// 添加 任务查询服务
		this.addAdapterBuilder(new TaskQueryServiceBuilder());
		// 添加 历史任务管理服务
		this.addAdapterBuilder(new HistoricTaskManageServiceBuilder());
		// 添加 历史任务查询服务
		this.addAdapterBuilder(new HistoricTaskQueryServiceBuilder());
		// 添加 身份认证查询服务
		this.addAdapterBuilder(new DentityQueryServiceBuilder());
		// 添加 身份认证管理服务
		this.addAdapterBuilder(new DentityManageServiceBuilder());
	}

	protected void InItEventListener() {
		// 注册动作事件
		ActionEventDispatcher.getInstance().clearActionListeners();
		ActionEventDispatcher.getInstance().registerActionListener(
				ActionEvent.class, new TaskActionListener());
	}

	protected void initSqlSessionFactory() throws WfBusinessException {
		this.initTransactionFactory();
		if (this.getSqlSessionFactory() == null) {
			InputStream inputStream = null;
			try {
				inputStream = ReflectUtil
						.getResourceAsStream("resources/com/idp/workflow/data/mapping/mappings.xml");
				Environment environment = new Environment("default_iworkflow",
						this.getTransactionFactory(), this.getDataSource());
				Reader reader = new InputStreamReader(inputStream);
				XMLConfigBuilder parser = new XMLConfigBuilder(reader);
				Configuration configuration = parser.getConfiguration();
				configuration.setEnvironment(environment);
				configuration = parser.parse();
				this.setSqlSessionFactory(new DefaultSqlSessionFactory(
						configuration));
				Context.buildMapperCreater(this.getSqlSessionFactory());
			} catch (Exception e) {
				throw new WfBusinessException(
						"Error while building ibatis SqlSessionFactory: "
								+ e.getMessage(), e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void initTransactionFactory() {
		if (this.getTransactionFactory() == null) {
			this.setTransactionFactory(new JdbcTransactionFactory());
		}
	}
}
