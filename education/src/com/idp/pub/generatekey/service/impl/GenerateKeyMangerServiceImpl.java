package com.idp.pub.generatekey.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.AbstractDaoStrategyFactory;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.dao.GenerateKeyDaoStrategyFactory;
import com.idp.pub.generatekey.dao.IGenerateKeyDaoStrategy;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.entity.KeyCondition;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;

/**
 * uuid主键生成策略，可适用于集群
 * 
 * @author panfei
 * 
 */
@Service("generateKeyServcie")
@Transactional(propagation = Propagation.REQUIRED)
public class GenerateKeyMangerServiceImpl implements IGenerateKeyMangerService,
		InitializingBean {

	// @Resource(name = "generateKeyDaoFactory")
	@Autowired(required = false)
	@Qualifier("generateKeyDaoFactory")
	private AbstractDaoStrategyFactory<String, IGenerateKeyDaoStrategy> generateKeyDaoFactory;

	private IGenerateKeyDaoStrategy generateKeyDaoStrategy;

	@Override
	public GeneratedKeyHolder getNextGeneratedKey(String entityName)
			throws BusinessException {
		KeyCondition params = new KeyCondition();
		params.setEntityName(entityName);
		return generateKeyDaoStrategy.createNextGenerateKey(params);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.generateKeyDaoFactory == null) {
			this.generateKeyDaoFactory = GenerateKeyDaoStrategyFactory
					.getGenerateKeyDaoStrategyFactoryy();
		}
		this.generateKeyDaoStrategy = this.generateKeyDaoFactory
				.createDaoStrategy();
	}

}
