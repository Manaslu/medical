package com.idp.workflow.impl.metadata.activiti;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.primarykey.IPrimaryKeyCreater;
import com.idp.workflow.util.spring.ServiceBeanFactory;
 
/**
 * 
 * Activiti工作流元数据实现
 * 
 * @author panfei
 * 
 */
public class SpringActivitiMetaData extends ActivitiMetaData implements
		ApplicationContextAware, DisposableBean {

	private IPrimaryKeyCreater pkCreater;

	private ApplicationContext applicationContext;

	/**
	 * 获取spring应用的上下文
	 * 
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 事务管理
	 */
	private PlatformTransactionManager transaManager;

	public SpringActivitiMetaData(DataSource dataSource,
			PlatformTransactionManager transaManager)
			throws WfBusinessException {
		this(dataSource, transaManager, false,
				ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE, true);
	}

	public SpringActivitiMetaData(DataSource dataSource,
			PlatformTransactionManager transaManager, boolean autoBind)
			throws WfBusinessException {
		this(dataSource, transaManager, false,
				ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE, autoBind);
	}

	public SpringActivitiMetaData(DataSource dataSource,
			PlatformTransactionManager transaManager,
			boolean jobExecutorActivate, String databaseSchemaUpdate,
			boolean autoBind) throws WfBusinessException {
		super(null);
		BuildActivitiEngine(transaManager, dataSource, jobExecutorActivate,
				databaseSchemaUpdate);
		this.InItSerivceBuilder();
		this.InItEventListener();
		this.setLocater(ServiceBeanFactory.GetInstance());
		this.setServiceRegAuto(autoBind);
		this.initSqlSessionFactory();
	}

	/**
	 * 构建工作流引擎
	 */
	private void BuildActivitiEngine(PlatformTransactionManager transaManager,
			DataSource dataSource, boolean jobExecutorActivate,
			String databaseSchemaUpdate) {
		// 初始化配置
		SpringProcessEngineConfiguration springprocessEngConfig = new SpringProcessEngineConfiguration();
		this.dataSource = dataSource;
		springprocessEngConfig.setDataSource(dataSource);
		this.transaManager = transaManager;
		springprocessEngConfig.setTransactionManager(transaManager);
		this.processEngConfig = springprocessEngConfig;
		this.processEngConfig.setJobExecutorActivate(jobExecutorActivate);
		this.processEngConfig.setDatabaseSchemaUpdate(databaseSchemaUpdate);
	}

	@Override
	protected void initSqlSessionFactory() throws WfBusinessException {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			dataSource = ((TransactionAwareDataSourceProxy) dataSource)
					.getTargetDataSource();
		}
		super.initSqlSessionFactory();
	}

	@Override
	protected void initTransactionFactory() {
		if (this.getTransactionFactory() == null) {
			SpringManagedTransactionFactory springFactory = new SpringManagedTransactionFactory();
			this.setTransactionFactory(springFactory);
		}
	}

	public PlatformTransactionManager getTransaManager() {
		return transaManager;
	}

	private void initPropertys() {
		SpringProcessEngineConfiguration config = (SpringProcessEngineConfiguration) this
				.getProcessEngConfig();
		if (this.getPkCreater() != null) {
			config.setIdGenerator((IdGenerator) this.getPkCreater()
					.createDbIdGenerator());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.initPropertys();
		this.applicationContext = arg0;
		ServiceBeanFactory.GetInstance().setSpringContext(arg0);
		// 构建ProcessEngine
		ProcessEngineFactoryBean factorBean = new ProcessEngineFactoryBean();
		factorBean
				.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) this.processEngConfig);
		factorBean.setApplicationContext(arg0);
		try {
			this.setWorkFlowSource(factorBean.getObject());
		} catch (Exception e) {
			throw new BeanInitializationException(
					"ProcessEngineFactoryBean创建ProcessEngine失败！", e);
		}
	}

	@Override
	public void destroy() throws Exception {
		if (this.getWorkFlowObject() != null) {
			this.getWorkFlowObject().close();
		}
	}

	public Map<String, Object> getServicesExtend() {
		return servicesExtend;
	}

	public void setServicesExtend(Map<String, Object> servicesExtend) {
		this.servicesExtend = servicesExtend;
	}

	/**
	 * 外挂式服务注册,支持用户可以通过IOC注入的方式直接添加服务，而不需要在工作流元数据中实现硬编码注册builder然后再实现初始化服务
	 * 配置文件优先原则，如果存在优先取配置文件的
	 */
	private Map<String, Object> servicesExtend = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	@Override
	public <E> E getAdapterSerivce(Class<E> classname)
			throws WfBusinessException {
		if (servicesExtend.size() > 0
				&& servicesExtend.containsKey(classname.getName())) {
			return ((E) servicesExtend.get(classname.getName()));
		} else {
			return super.getAdapterSerivce(classname);
		}
	}

	public IPrimaryKeyCreater getPkCreater() {
		return pkCreater;
	}

	public void setPkCreater(IPrimaryKeyCreater pkCreater) {
		this.pkCreater = pkCreater;
	}

}
