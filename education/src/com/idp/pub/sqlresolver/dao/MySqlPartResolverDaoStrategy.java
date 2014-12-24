package com.idp.pub.sqlresolver.dao;

import com.idp.pub.entity.Pager;
import com.idp.pub.entity.utils.SqlBuilder;
import com.idp.pub.sqlresolver.entity.PageParams;
import com.idp.pub.sqlresolver.service.ISqlPartResolverService;
import com.idp.pub.utils.StringUtils;

/**
 * mysql 策略
 * 
 * @author panfei
 * 
 */
public class MySqlPartResolverDaoStrategy implements ISqlPartResolverService {

	@Override
	public String createPageQuerySql(Pager<?> page, PageParams params) {

		String selectsql = SqlBuilder.createSelectSql(params.getTableName(),
				params.getColNames(), params.getWherePartStr());

		if (page.getCurrent() > page.getTotalPage() && page.getTotal() > 0) {
			page.setCurrent(page.getCurrent() - 1);
		}

		if (!StringUtils.isEmpty(params.getOrderPartStr())) {
			selectsql = selectsql + " " + params.getOrderPartStr();
		}

		StringBuilder limitstr = new StringBuilder();
		limitstr.append(selectsql);
		if (page != null) {
			limitstr.append(" limit ");
			long beginrows = 0;

			if (page.getCurrent() > 1) {
				beginrows = (page.getCurrent() - 1l) * page.getLimit();
			}

			limitstr.append(beginrows);
			limitstr.append(",");
			limitstr.append(page.getLimit());
		}
		return limitstr.toString();
	}

	@Override
	public String createTimeVersionSql(String tsValue) {
		StringBuilder sqlwhere = new StringBuilder();
		sqlwhere.append("'");
		sqlwhere.append(tsValue);
		sqlwhere.append("'");
		return sqlwhere.toString();
	}

}
