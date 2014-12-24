package com.idp.workflow.vo.pub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * where语句条件封装
 * 
 * @author panfei
 * 
 */
public class WhereCondition implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = -5442651668804252893L;

	/**
	 * sql元素集合
	 */
	private List<SqlPart> where_sqlparts = new ArrayList<SqlPart>();

	/**
	 * 关系枚举
	 * 
	 * @author panfei
	 * 
	 */
	public enum RelationType {
		AND, OR, NONE
	}

	public WhereCondition(String sqlpart) {
		where_sqlparts.add(new SqlPart(sqlpart, RelationType.NONE));
	}

	/**
	 * 添加一个and关系查询条件
	 * 
	 * @param sqlpart
	 * @return
	 */
	public WhereCondition addAndRelation(String sqlpart) {
		where_sqlparts.add(new SqlPart(sqlpart, RelationType.AND));
		return this;
	}

	/**
	 * 添加一个or关系查询条件
	 * 
	 * @param sqlpart
	 * @return
	 */
	public WhereCondition addOrRelation(String sqlpart) {
		where_sqlparts.add(new SqlPart(sqlpart, RelationType.OR));
		return this;
	}

	/**
	 * 封装where查询条件
	 */
	@Override
	public String toString() {
		StringBuilder appener = new StringBuilder();
		for (SqlPart temp : where_sqlparts) {
			appener.append(temp.toString());
		}
		return appener.toString();
	}

	/**
	 * 判断是否包含条件相等
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 */
	public boolean equal(String colName, Object colValue) {
		// String checkvalue = createSqlStr(colName, colValue);
		SqlPart checker = new SqlPart(colName, colValue, RelationType.AND);
		if (where_sqlparts.contains(checker)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建组装sql语句
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 */
	private static String createSqlStr(String colName, Object colValue) {
		StringBuilder appener = new StringBuilder();
		appener.append(colName);
		appener.append("=");
		appener.append(String.valueOf(colValue));
		return appener.toString();
	}

	/**
	 * 条件元素
	 * 
	 * @author panfei
	 * 
	 */
	class SqlPart implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7231989579807866359L;
		private String sqlPartStr;
		private RelationType relationType;

		public RelationType getRelationType() {
			return relationType;
		}

		public SqlPart(String sqlPartStr, RelationType relationType) {
			this.sqlPartStr = sqlPartStr;
			this.relationType = relationType;
		}

		public SqlPart(String colName, Object colValue,
				RelationType relationType) {
			this(createSqlStr(colName, colValue), relationType);
		}

		@Override
		public String toString() {
			StringBuilder appener = new StringBuilder();
			if (RelationType.AND.equals(relationType)) {
				appener.append(" and");
			} else if (RelationType.OR.equals(relationType)) {
				appener.append(" or");
			}
			appener.append(" ");
			appener.append(sqlPartStr);
			return appener.toString();
		}

		@Override
		public boolean equals(Object anObject) {
			if (anObject == null) {
				return false;
			}
			return this.toString().equals(anObject.toString());
		}
	}
}
