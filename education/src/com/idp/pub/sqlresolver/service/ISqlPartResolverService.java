package com.idp.pub.sqlresolver.service;

import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.sqlresolver.entity.PageParams;

/**
 * 不同数据库sql差异适配服务
 * 
 * @author panfei
 * 
 */
public interface ISqlPartResolverService {

	/**
	 * 分页查询语句
	 * 
	 * @param page
	 * @return
	 */
	String createPageQuerySql(Pager<?> page, PageParams params)throws BusinessException;

	/**
	 * 时间戳条件查询
	 * 
	 * @param tsValue
	 * @return
	 */
	String createTimeVersionSql(String tsValue)throws BusinessException;

}
