package com.idp.pub.sqlresolver.dao;

import com.idp.pub.entity.Pager;
import com.idp.pub.entity.utils.SqlBuilder;
import com.idp.pub.sqlresolver.entity.PageParams;
import com.idp.pub.sqlresolver.service.ISqlPartResolverService;
import com.idp.pub.utils.StringUtils;

/**
 * oracle策略
 * 
 * @author panfei
 * 
 */
public class OraclePartResolverDaoStrategy implements ISqlPartResolverService {

	private static final String TableAlias = "pg";

	private static final String RowNumAlias = "rn";

	@Override
	public String createPageQuerySql(Pager<?> page, PageParams params) {

		StringBuilder rownumder = new StringBuilder();
		if (page == null) {
			String selectsql = SqlBuilder.createSelectSql(
					params.getTableName(), params.getColNames(),
					params.getWherePartStr());

			if (!StringUtils.isEmpty(params.getOrderPartStr())) {
				selectsql = selectsql + " " + params.getOrderPartStr();
			}
			rownumder.append(selectsql);
		} else {

			if (page.getCurrent() > page.getTotalPage() && page.getTotal() > 0) {
				page.setCurrent(page.getCurrent() - 1);
			}

			String firstLoopSql = SqlBuilder.createSelectSql(null,
					params.getColNames(), null);
			rownumder.append(firstLoopSql);
			rownumder.append(" (");
			String[] aliasCols = new String[params.getColNames().length];
			for (int i = 0; i < aliasCols.length; i++) {
				aliasCols[i] = TableAlias + "." + params.getColNames()[i];
			}
			String secondLoopSql = SqlBuilder.createSelectSql(null, aliasCols,
					null);
			secondLoopSql.replaceFirst("from", ",rownum" + " " + RowNumAlias
					+ " from");
			rownumder.append(secondLoopSql);
			rownumder.append(" (");
			String thridLoopSql = SqlBuilder.createSelectSql(
					params.getTableName(), params.getColNames(),
					params.getWherePartStr());
			if (!StringUtils.isEmpty(params.getOrderPartStr())) {
				thridLoopSql = thridLoopSql + " " + params.getOrderPartStr();
			}
			rownumder.append("thridLoopSql");
			rownumder.append(") ");
			rownumder.append(TableAlias);
			rownumder.append("where rownum<=");
			rownumder.append(page.getCurrent() * page.getLimit() + 1);
			rownumder.append(") ");
			rownumder.append(RowNumAlias);
			rownumder.append(">=");
			rownumder.append((page.getCurrent() + 1) * page.getLimit());
		}
		return rownumder.toString();
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
