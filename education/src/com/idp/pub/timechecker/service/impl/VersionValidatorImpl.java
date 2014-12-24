package com.idp.pub.timechecker.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idp.pub.entity.BaseEntity;
import com.idp.pub.entity.utils.SuperEntityUtils;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.timechecker.dao.VersionValidatorDao;
import com.idp.pub.timechecker.exception.TimeOutException;
import com.idp.pub.timechecker.service.IVersionValidator;

/**
 * 时间戳校验服务实现
 * 
 * @author panfei
 * 
 */
@Service("versionValidator")
public class VersionValidatorImpl implements IVersionValidator {

	@Resource(name = "VersionValidatorDao")
	public VersionValidatorDao dao;

	@Override
	public void validateBaseEntitys(BaseEntity... lockvos)
			throws TimeOutException {
		if (lockvos == null || lockvos.length == 0) {
			throw new TimeOutException("加锁vo不能为空!");
		}
		try {
			dao.validateBaseEntitys(SuperEntityUtils.convertBaseExtVO(lockvos));
		} catch (BusinessException e) {
			throw new TimeOutException(e);
		}
	}

}
