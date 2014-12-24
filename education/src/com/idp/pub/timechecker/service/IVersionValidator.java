package com.idp.pub.timechecker.service;

import com.idp.pub.entity.BaseEntity;
import com.idp.pub.timechecker.exception.TimeOutException;

/**
 * 业务vo时间戳校验
 * 
 * @author panfei
 * 
 */
public interface IVersionValidator {

	/**
	 * 批量业务vo校验服务<br>
	 * 必须继承BaseVO和BaseExtVO，VO主键必须有值,getTableName和getTs必须有值<br>
	 * 
	 * @param lockvos
	 *            批量vo
	 * @throws TimeOutException
	 *             时间戳不一致，已失效异常
	 */
	void validateBaseEntitys(BaseEntity... lockvos) throws TimeOutException;
}
