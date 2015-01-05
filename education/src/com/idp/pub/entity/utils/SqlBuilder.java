package com.idp.pub.entity.utils;

import java.util.Map;
import java.util.Set;

import com.idp.pub.entity.Pager;
import com.idp.pub.entity.constants.IDataType;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.utils.StringUtils;
 
/**
 * sql语句构造
 * 
 * @author panfei
 * 
 */
public class SqlBuilder {

	public static final int ORDERTYPE_ASC = 0;

	public static final int ORDERTYPE_DESC = 1;

	/**
	 * 拼装主键查询条件
	 * 
	 * @param datamap
	 * @return
	 * @throws BusinessException
	 */
	public static String createWherePksSql(Map<String, DataAttribute> datamap)
			throws BusinessException {
		StringBuilder appender = new StringBuilder();
		if (datamap != null) {
			appender.append(" where (");
			Set<String> keynamess = datamap.keySet();
			int index = 0;
			for (String keyname : keynamess) {
				DataAttribute Datavalue = datamap.get(keyname);

				if (StringUtils.isEmpty(Datavalue.getStringValue())) {
					throw new BusinessException("主键不能为空！");
				}

				if (index > 0) {
					appender.append(" and ");
				}
				appender.append(keyname);
				appender.append("=");
				if (IDataType.VACHAR == Datavalue.getDataType()) {
					appender.append("'");
				}
				appender.append(Datavalue.getStringValue());
				if (IDataType.VACHAR == Datavalue.getDataType()) {
					appender.append("'");
				}
				index++;
			}
			appender.append(")");
		}
		return appender.toString();
	}

	/**
	 * 构建删除sql语句
	 * 
	 * @param pkfilednames
	 * @param pkvalues
	 * @return
	 */
	public static String createDeleteSql(String tableName,
			Map<String, DataAttribute> datamap) throws BusinessException {
		if (datamap != null && datamap.size() > 0) {
			StringBuilder appender = new StringBuilder();
			appender.append("delete from ");
			appender.append(tableName);
			appender.append(createWherePksSql(datamap));
			return appender.toString();
		}
		return null;
	}

	/**
	 * 生成插入语句
	 * 
	 * @param tableName
	 * @param datamap
	 * @return
	 * @throws BusinessException
	 */
	public static String createInsertSql(String tableName,
			Map<String, DataAttribute> datamap, String... ignoreColNames) {
		if (datamap != null && datamap.size() > 0) {
			if (ignoreColNames != null) {
				for (String ignoreColName : ignoreColNames) {
					datamap.remove(ignoreColName);
				}
			}

			StringBuilder appender = new StringBuilder();
			appender.append("insert into ");
			appender.append(tableName);
			appender.append(" (");
			Set<String> keynames = datamap.keySet();
			int index = 0;
			for (String keyname : keynames) {
				if (index > 0) {
					appender.append(",");
				}
				appender.append(keyname);
				index++;
			}
			appender.append(")");

			StringBuilder colvaluessql = new StringBuilder();
			colvaluessql.append(" (");
			index = 0;
			for (String keyname : keynames) {
				DataAttribute Datavalue = datamap.get(keyname);

				if (index > 0) {
					colvaluessql.append(",");
				}

				if (IDataType.VACHAR == Datavalue.getDataType()
						&& Datavalue.getStringValue() != null) {
					colvaluessql.append("'");
				}

				colvaluessql.append(Datavalue.getStringValue());

				if (IDataType.VACHAR == Datavalue.getDataType()
						&& Datavalue.getStringValue() != null) {
					colvaluessql.append("'");
				}

				index++;
			}
			colvaluessql.append(")");
			appender.append(" values ");
			appender.append(colvaluessql);
			return appender.toString();
		}
		return null;
	}

	/**
	 * 修改语句
	 * 
	 * @param tableName
	 * @param colnames
	 * @param colvalues
	 * @return
	 * @throws BusinessException
	 */
	public static String createUpdateSql(String tableName,
			Map<String, DataAttribute> datamap, String wheresql,
			String... ignoreColNames) {
		if (datamap != null && datamap.size() > 0) {

			if (ignoreColNames != null) {
				for (String ignoreColName : ignoreColNames) {
					datamap.remove(ignoreColName);
				}
			}

			StringBuilder appender = new StringBuilder();
			appender.append("update ");
			appender.append(tableName);
			appender.append(" set ");
			int index = 0;
			Set<String> keynames = datamap.keySet();
			for (String kename : keynames) {

				DataAttribute Datavalue = datamap.get(kename);

				if (index > 0) {
					appender.append(",");
				}
				appender.append(kename);
				appender.append("=");

				if (IDataType.VACHAR == Datavalue.getDataType()
						&& Datavalue.getStringValue() != null) {
					appender.append("'");
				}

				appender.append(Datavalue.getStringValue());

				if (IDataType.VACHAR == Datavalue.getDataType()
						&& Datavalue.getStringValue() != null) {
					appender.append("'");
				}
				index++;
			}

			if (!StringUtils.isEmpty(wheresql)) {
				if (!wheresql.trim().startsWith("where")) {
					appender.append(" where ");
				}
				appender.append(wheresql);
			}
			return appender.toString();
		}
		return null;
	}

	/**
	 * 统计总页数sql语句
	 * 
	 * @param tableName
	 * @param wheresql
	 * @return
	 * @throws BusinessException
	 */
	public static String createSelectCountSql(String tableName, String wheresql) {
		return createSelectSql(tableName, new String[] { " count(*) as "
				+ Pager.TOTAL + " " }, wheresql);
	}

	/**
	 * 查询语句
	 * 
	 * @param tableName
	 * @param colnames
	 * @param colvalues
	 * @param wheresql
	 * @return
	 * @throws BusinessException
	 */
	public static String createSelectSql(String tableName, String[] colnames,
			String wheresql) {
		if (colnames != null && colnames.length > 0) {
			StringBuilder appender = new StringBuilder();
			appender.append("select ");
			appender.append(" ");
			for (int i = 0; i < colnames.length; i++) {
				if (i > 0) {
					appender.append(",");
				}
				appender.append(colnames[i]);
			}
			appender.append(" from ");

			if (!StringUtils.isEmpty(tableName)) {
				appender.append(tableName);
			}

			if (!StringUtils.isEmpty(wheresql)) {
				if (!wheresql.trim().startsWith("where")
						&& !wheresql.trim().startsWith("order by")
						&& !wheresql.trim().startsWith("group by")) {
					appender.append(" where ");
				}
				appender.append(wheresql);
			}
			return appender.toString();
		}
		return null;
	}

	/**
	 * order条件串拼接
	 * 
	 * @param colnames
	 * @param orderType
	 * @return
	 */
	public static String createOrderBySql(String[] colnames, int orderType) {

		if (orderType == -1) {
			return "";
		}

		StringBuilder appender = new StringBuilder();
		appender.append(" order by ");
		for (int i = 0; i < colnames.length; i++) {
			if (i > 0) {
				appender.append(",");
			}
			appender.append(colnames[i]);
		}
		if (ORDERTYPE_ASC == orderType) {
			appender.append(" asc ");
		} else {
			appender.append(" desc ");
		}
		return appender.toString();
	}

	/**
	 * 防止sql注入
	 * 
	 * @param sql
	 * @return
	 */
	public static String transactSQLInjection(String sql) {
		return sql.replaceAll(".*([';]+|(--)+).*", " ");
	}
}
